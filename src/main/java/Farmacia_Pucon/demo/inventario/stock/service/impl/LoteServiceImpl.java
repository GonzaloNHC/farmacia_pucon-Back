package Farmacia_Pucon.demo.inventario.stock.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.domain.MovimientoInventario;
import Farmacia_Pucon.demo.inventario.repository.MovimientoInventarioRepository;
import Farmacia_Pucon.demo.inventario.stock.dto.*;
import Farmacia_Pucon.demo.inventario.stock.dto.DevolucionStockRequest;
import Farmacia_Pucon.demo.inventario.stock.service.LoteService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public LoteServiceImpl(LoteRepository loteRepository,
                           MedicamentoRepository medicamentoRepository,
                           MovimientoInventarioRepository movimientoInventarioRepository) {
        this.loteRepository = loteRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    @Override
    public LoteResponseDTO crearLote(CrearLoteRequest request) {

        if (request.getMedicamentoId() == null) {
            throw new RuntimeException("El medicamentoId es obligatorio para crear un lote");
        }
        if (request.getStockInicial() == null || request.getStockInicial() < 0) {
            throw new RuntimeException("El stockInicial es obligatorio y no puede ser negativo");
        }
        if (request.getPrecioUnitario() == null ||
                request.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precioUnitario es obligatorio y debe ser mayor a 0");
        }
        Medicamento medicamento = medicamentoRepository.findById(request.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        Lote lote = new Lote();
        lote.setMedicamento(medicamento);
        lote.setCodigoLote(request.getCodigoLote());
        lote.setFechaVencimiento(request.getFechaVencimiento());
        lote.setPrecioUnitario(request.getPrecioUnitario());
        lote.setStockInicial(request.getStockInicial());
        lote.setStockMinimo(request.getStockMinimo());
        lote.setCantidadDisponible(request.getStockInicial());
        lote.setActivo(true);

        BigDecimal precioTotal = request.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(request.getStockInicial()));
        lote.setPrecioTotalLote(precioTotal);

        Lote guardado = loteRepository.save(lote);
        return toResponse(guardado);
    }

    @Override
    public List<LoteResponseDTO> listarTodos() {
        return loteRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoteResponseDTO> listarPorMedicamento(Long medicamentoId) {
        return loteRepository.findByMedicamentoIdAndActivoTrue(medicamentoId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoteResponseDTO obtenerPorId(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        return toResponse(lote);
    }

    @Override
    public LoteResponseDTO actualizarStock(Long id, ActualizarStockRequest request) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        if (request.getStockInicial() != null) {
            if (request.getStockInicial() < 0) {
                throw new RuntimeException("El stockInicial no puede ser negativo");
            }
            lote.setStockInicial(request.getStockInicial());
            lote.setCantidadDisponible(request.getStockInicial());
        }
        if (request.getStockMinimo() != null) {
            lote.setStockMinimo(request.getStockMinimo());
        }

        BigDecimal precioTotal = request.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(request.getStockInicial()));
        lote.setPrecioTotalLote(precioTotal);

        Lote actualizado = loteRepository.save(lote);
        return toResponse(actualizado);
    }


    @Override
    public void desactivar(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        lote.setActivo(false);
        loteRepository.save(lote);
    }

    @Override
    public LoteResponseDTO devolverStock(Long loteId, DevolucionStockRequest request) {

        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad a devolver debe ser mayor a cero");
        }

        Lote lote = loteRepository.findById(loteId)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        // 1) Aumentar stock del lote
        int stockActual = lote.getCantidadDisponible();
        int nuevoStock = stockActual + request.getCantidad();
        lote.setCantidadDisponible(nuevoStock);

        loteRepository.save(lote);

        // 2) Registrar movimiento de inventario tipo DEVOLUCION
        MovimientoInventario mov = new MovimientoInventario();
        mov.setLote(lote);
        mov.setFechaHora(java.time.LocalDateTime.now());
        mov.setCantidad(request.getCantidad());   // positivo porque entra stock
        mov.setTipo("DEVOLUCION");
        mov.setReferencia(request.getMotivo());   // Ej: "Devolución venta 15"

        movimientoInventarioRepository.save(mov);

        // 3) Devolver el lote actualizado al front
        return toResponse(lote);
    }

    @Override
    public LoteResponseDTO registrarMovimiento(RegistrarMovimientoRequest request) {

        if (request.getLoteId() == null) {
            throw new RuntimeException("Debe indicar un loteId");
        }
        if (request.getCantidad() == null || request.getCantidad() == 0) {
            throw new RuntimeException("La cantidad debe ser distinta de cero");
        }
        if (request.getTipo() == null || request.getTipo().isBlank()) {
            throw new RuntimeException("Debe indicar el tipo de movimiento");
        }

        Lote lote = loteRepository.findById(request.getLoteId())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        int stockActual = lote.getCantidadDisponible();
        int nuevoStock = stockActual + request.getCantidad();

        if (nuevoStock < 0) {
            throw new RuntimeException("El movimiento dejaría el stock en negativo");
        }

        lote.setCantidadDisponible(nuevoStock);
        loteRepository.save(lote);

        // Registrar el movimiento
        MovimientoInventario mov = new MovimientoInventario();
        mov.setLote(lote);
        mov.setFechaHora(java.time.LocalDateTime.now());
        mov.setCantidad(request.getCantidad());
        mov.setTipo(request.getTipo());
        mov.setReferencia(request.getReferencia());

        movimientoInventarioRepository.save(mov);

        return toResponse(lote);
    }


    private LoteResponseDTO toResponse(Lote lote) {
        BigDecimal precioTotal = lote.getPrecioTotalLote();
        if (precioTotal == null && lote.getPrecioUnitario() != null && lote.getStockInicial() != null) {
            precioTotal = lote.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(lote.getStockInicial()));
        }
        return new LoteResponseDTO(
                lote.getId(),
                lote.getMedicamento().getId(),
                lote.getMedicamento().getNombreComercial(),
                lote.getCodigoLote(),
                lote.getFechaVencimiento(),
                lote.getPrecioUnitario(),
                lote.getPrecioTotalLote(),
                lote.getStockInicial(),
                lote.getStockMinimo(),
                lote.getCantidadDisponible(),
                lote.getActivo()
        );
    }
}
