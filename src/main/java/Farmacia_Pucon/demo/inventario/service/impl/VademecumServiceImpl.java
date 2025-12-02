package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.dto.LoteVademecumDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoVademecumDTO;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.service.VademecumService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VademecumServiceImpl implements VademecumService {

    private final MedicamentoRepository medicamentoRepository;
    private final LoteRepository loteRepository;

    public VademecumServiceImpl(MedicamentoRepository medicamentoRepository,
                                LoteRepository loteRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.loteRepository = loteRepository;
    }

    @Override
    public MedicamentoVademecumDTO obtenerPorId(Long medicamentoId) {
        Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        return mapMedicamentoToVademecum(medicamento);
    }

    @Override
    public List<MedicamentoVademecumDTO> buscarPorTexto(String texto) {

        // Usa tu query custom si existe
        List<Medicamento> medicamentos =
                medicamentoRepository.buscarActivosPorNombre(texto);

        return medicamentos.stream()
                .map(this::mapMedicamentoToVademecum)
                .collect(Collectors.toList());
    }

    public List<MedicamentoVademecumDTO> obtenerEquivalentes(Long medicamentoId) {
        Medicamento base = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        // Equivalentes: mismo nombre genérico, activos y distinto ID
        List<Medicamento> equivalentes = medicamentoRepository
                .findByNombreGenericoContainingIgnoreCase(base.getNombreGenerico())
                .stream()
                .filter(Medicamento::getActivo)
                .filter(m -> !m.getId().equals(base.getId()))
                .collect(Collectors.toList());

        return equivalentes.stream()
                .map(this::mapMedicamentoToVademecum)
                .collect(Collectors.toList());
    }

    // ================== Mapeos ==================

    private MedicamentoVademecumDTO mapMedicamentoToVademecum(Medicamento medicamento) {

        MedicamentoVademecumDTO dto = new MedicamentoVademecumDTO();
        dto.setId(medicamento.getId());
        dto.setNombreComercial(medicamento.getNombreComercial());
        dto.setNombreGenerico(medicamento.getNombreGenerico());
        dto.setPresentacion(medicamento.getPresentacion());
        dto.setDosificacion(medicamento.getDosificacion());
        dto.setActivo(medicamento.getActivo());

        dto.setIndicaciones(medicamento.getIndicaciones());
        dto.setContraindicaciones(medicamento.getContraindicaciones());
        dto.setAdvertencias(medicamento.getAdvertencias());
        dto.setInteracciones(medicamento.getInteracciones());

        //Normativa son las reglas que afectan la venta del medicamento
        dto.setRequiereReceta(medicamento.getRequiereReceta());
        dto.setTipoReceta(medicamento.getTipoReceta());
        dto.setControlado(medicamento.getControlado());

        // Lotes activos de este medicamento
        List<Lote> lotes = loteRepository.findByMedicamentoIdAndActivoTrue(medicamento.getId());

        List<LoteVademecumDTO> lotesDTO = lotes.stream()
                .map(this::mapLoteToVademecum)
                .collect(Collectors.toList());

        dto.setLotes(lotesDTO);

        // stockTotal = suma de cantidadDisponible de lotes
        int stockTotal = lotes.stream()
                .map(Lote::getCantidadDisponible)
                .filter(c -> c != null)
                .reduce(0, Integer::sum);

        dto.setStockTotal(stockTotal);

        return dto;
    }

    private LoteVademecumDTO mapLoteToVademecum(Lote lote) {
        LoteVademecumDTO dto = new LoteVademecumDTO();
        dto.setLoteId(lote.getId());
        dto.setCodigoLote(lote.getCodigoLote());
        dto.setFechaVencimiento(lote.getFechaVencimiento());
        dto.setCantidadDisponible(lote.getCantidadDisponible());
        dto.setPrecioUnitario(lote.getPrecioUnitario());

        BigDecimal precioTotal = null;
        if (lote.getPrecioUnitario() != null && lote.getStockInicial() != null) {
            precioTotal = lote.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(lote.getStockInicial()));
        }
        dto.setPrecioTotalLote(precioTotal);

        return dto;
    }
}
