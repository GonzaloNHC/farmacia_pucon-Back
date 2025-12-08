package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.service.InventarioConsultaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioConsultaServiceImpl implements InventarioConsultaService {

    private final LoteRepository loteRepository;

    public InventarioConsultaServiceImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public int obtenerStockTotal(Long medicamentoId) {
        return loteRepository.obtenerStockTotal(medicamentoId);
    }

    @Override
    public List<Lote> obtenerLotesDeMedicamento(Long medicamentoId) {
        return loteRepository.findByMedicamentoIdOrderByFechaVencimientoAsc(medicamentoId);
    }

    @Override
    public Lote obtenerPrimerLoteDisponible(Long medicamentoId) {
        return loteRepository.findByMedicamentoIdOrderByFechaVencimientoAsc(medicamentoId)
                .stream()
                .filter(l -> l.getCantidadDisponible() > 0 && l.getActivo())
                .findFirst()
                .orElse(null);
    }
}
