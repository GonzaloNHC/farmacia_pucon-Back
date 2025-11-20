package Farmacia_Pucon.demo.inventario.stock.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.stock.domain.Lote;
import Farmacia_Pucon.demo.inventario.stock.dto.ActualizarStockRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.CrearLoteRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.LoteResponseDTO;
import Farmacia_Pucon.demo.inventario.stock.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.stock.service.LoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final MedicamentoRepository medicamentoRepository;

    public LoteServiceImpl(LoteRepository loteRepository,
                           MedicamentoRepository medicamentoRepository) {
        this.loteRepository = loteRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public LoteResponseDTO crearLote(CrearLoteRequest request) {

        if (request.getMedicamentoId() == null) {
            throw new RuntimeException("El medicamentoId es obligatorio para crear un lote");
        }
        if (request.getStockInicial() == null || request.getStockInicial() < 0) {
            throw new RuntimeException("El stockInicial es obligatorio y no puede ser negativo");
        }

        Medicamento medicamento = medicamentoRepository.findById(request.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        Lote lote = new Lote();
        lote.setMedicamento(medicamento);
        lote.setCodigoLote(request.getCodigoLote());
        lote.setFechaVencimiento(request.getFechaVencimiento());
        lote.setStockInicial(request.getStockInicial());
        lote.setStockMinimo(request.getStockMinimo());
        lote.setActivo(true);

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
        }
        if (request.getStockMinimo() != null) {
            lote.setStockMinimo(request.getStockMinimo());
        }

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

    private LoteResponseDTO toResponse(Lote lote) {
        return new LoteResponseDTO(
                lote.getId(),
                lote.getMedicamento().getId(),
                lote.getMedicamento().getNombreComercial(),
                lote.getCodigoLote(),
                lote.getFechaVencimiento(),
                lote.getStockInicial(),
                lote.getStockMinimo(),
                lote.getActivo()
        );
    }
}
