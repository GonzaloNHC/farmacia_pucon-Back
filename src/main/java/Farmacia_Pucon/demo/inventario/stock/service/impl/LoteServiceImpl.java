package Farmacia_Pucon.demo.inventario.stock.service.impl;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.domain.MovimientoInventario;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.repository.MovimientoInventarioRepository;
import Farmacia_Pucon.demo.inventario.stock.dto.*;
import Farmacia_Pucon.demo.inventario.stock.service.LoteService;
import Farmacia_Pucon.demo.ventas.domain.DetalleVenta;
import Farmacia_Pucon.demo.ventas.domain.Venta;
import Farmacia_Pucon.demo.ventas.repository.DetalleVentaRepository;
import Farmacia_Pucon.demo.ventas.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final UserRepository userRepository;

    public LoteServiceImpl(
            LoteRepository loteRepository,
            MedicamentoRepository medicamentoRepository,
            MovimientoInventarioRepository movimientoInventarioRepository,
            VentaRepository ventaRepository,
            DetalleVentaRepository detalleVentaRepository,
            UserRepository userRepository
    ) {
        this.loteRepository = loteRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.userRepository = userRepository;
    }

    // ================== Crear lote ==================

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
        lote.setCosto(request.getCosto());
        lote.setFechaIngreso(request.getFechaIngreso());

        BigDecimal precioTotal = request.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(request.getStockInicial()));
        lote.setPrecioTotalLote(precioTotal);

        Lote guardado = loteRepository.save(lote);
        return toResponse(guardado);
    }

    // ================== Listar / obtener ==================

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

    // ================== Actualizar stock / precio ==================

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
        if (request.getPrecioUnitario() != null &&
                request.getPrecioUnitario().compareTo(BigDecimal.ZERO) > 0) {
            lote.setPrecioUnitario(request.getPrecioUnitario());
        }

        // Recalcular precio total si tengo precio y stockInicial
        if (lote.getPrecioUnitario() != null && lote.getStockInicial() != null) {
            BigDecimal precioTotal = lote.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(lote.getStockInicial()));
            lote.setPrecioTotalLote(precioTotal);
        }

        Lote actualizado = loteRepository.save(lote);
        return toResponse(actualizado);
    }

    // ================== Desactivar lote ==================

    @Override
    public void desactivar(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        lote.setActivo(false);
        loteRepository.save(lote);
    }

    // ================== Devolución genérica de stock ==================

    @Override
    public LoteResponseDTO devolverStock(Long loteId, ActualizarStockRequest.DevolucionStockRequest request) {

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
        mov.setMedicamento(lote.getMedicamento());   // 👈 importante para HU27
        mov.setFechaHora(LocalDateTime.now());
        mov.setCantidad(request.getCantidad());      // positivo porque entra stock
        mov.setTipo("DEVOLUCION");
        mov.setReferencia(request.getMotivo());      // Ej: "Devolución manual"

        movimientoInventarioRepository.save(mov);

        // 3) Devolver el lote actualizado al front
        return toResponse(lote);
    }

    // ================== Movimiento manual (ajuste) ==================

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
        int nuevoStock = stockActual + request.getCantidad(); // si cantidad < 0, será salida

        if (nuevoStock < 0) {
            throw new RuntimeException("El movimiento dejaría el stock en negativo");
        }

        lote.setCantidadDisponible(nuevoStock);
        loteRepository.save(lote);

        // Registrar el movimiento en kardex
        MovimientoInventario mov = new MovimientoInventario();
        mov.setLote(lote);
        mov.setMedicamento(lote.getMedicamento());
        mov.setFechaHora(LocalDateTime.now());
        mov.setCantidad(request.getCantidad());
        mov.setTipo(request.getTipo());             // Ej: "AJUSTE", "SALIDA", etc.
        mov.setReferencia(request.getReferencia()); // Texto libre

        movimientoInventarioRepository.save(mov);

        return toResponse(lote);
    }

    // ================== Registrar INGRESO de lote (usado en IngresoServiceImpl) ==================

    /**
     * Se usa al registrar un ingreso para dejar constancia en el kardex (tipo INGRESO).
     */
    public void registrarIngresoDeLote(Lote lote, String referencia) {
        MovimientoInventario m = new MovimientoInventario();
        m.setLote(lote);
        m.setMedicamento(lote.getMedicamento());            // 👈 obligatorio porque es not-null
        m.setFechaHora(LocalDateTime.now());
        m.setCantidad(lote.getCantidadDisponible());        // o lote.getStockInicial()
        m.setTipo("INGRESO");
        m.setReferencia(referencia);                        // Ej: "Ingreso N°X"
        movimientoInventarioRepository.save(m);
    }

    // ================== Devolución por venta ==================

    @Override
    public LoteResponseDTO devolverStockPorVenta(DevolucionVentaRequest request) {

        // Validaciones básicas
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad a devolver debe ser mayor a cero");
        }
        if (request.getVentaId() == null) {
            throw new RuntimeException("Debe indicar ventaId");
        }
        if (request.getDetalleId() == null) {
            throw new RuntimeException("Debe indicar detalleId");
        }
        if (request.getLoteId() == null) {
            throw new RuntimeException("Debe indicar loteId");
        }
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("Debe indicar usuarioId");
        }

        // Venta
        Venta venta = ventaRepository.findById(request.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Detalle
        DetalleVenta detalle = detalleVentaRepository.findById(request.getDetalleId())
                .orElseThrow(() -> new RuntimeException("DetalleVenta no encontrado"));

        // Validar que el detalle pertenece a la venta
        if (!detalle.getVenta().getId().equals(venta.getId())) {
            throw new RuntimeException("El detalle no pertenece a la venta indicada");
        }

        // Validar lote correcto
        if (!detalle.getLote().getId().equals(request.getLoteId())) {
            throw new RuntimeException("El lote indicado no coincide con el lote vendido");
        }

        // Validar que no se exceda la cantidad vendida
        if (request.getCantidad() > detalle.getCantidad()) {
            throw new RuntimeException(
                    "No se puede devolver más cantidad que la vendida (" + detalle.getCantidad() + ")"
            );
        }

        // Lote
        Lote lote = loteRepository.findById(request.getLoteId())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        // Reintegrar stock
        lote.setCantidadDisponible(lote.getCantidadDisponible() + request.getCantidad());
        loteRepository.save(lote);

        // Usuario que realiza la devolución
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Registrar movimiento en kardex
        MovimientoInventario mov = new MovimientoInventario();
        mov.setLote(lote);
        mov.setMedicamento(lote.getMedicamento());
        mov.setFechaHora(LocalDateTime.now());
        mov.setCantidad(request.getCantidad());
        mov.setTipo("DEVOLUCION");
        mov.setUsuario(usuario);
        mov.setReferencia(
                "DEVOLUCION POR VENTA #" + venta.getId() +
                        " | Detalle #" + detalle.getId() +
                        " | Motivo: " + request.getMotivo() +
                        " | Usuario: " + usuario.getNombreCompleto()
        );

        movimientoInventarioRepository.save(mov);

        return toResponse(lote);
    }

    // ================== Helper para mapear Lote -> DTO ==================

    private LoteResponseDTO toResponse(Lote lote) {
        BigDecimal precioTotal = lote.getPrecioTotalLote();
        if (precioTotal == null &&
                lote.getPrecioUnitario() != null &&
                lote.getStockInicial() != null) {
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
                precioTotal,
                lote.getStockInicial(),
                lote.getStockMinimo(),
                lote.getCantidadDisponible(),
                lote.getActivo(),
                lote.getCosto(),
                lote.getFechaIngreso()
        );
    }
}