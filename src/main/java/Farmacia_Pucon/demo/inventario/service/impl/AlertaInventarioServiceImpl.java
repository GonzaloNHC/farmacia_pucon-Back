package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.dto.AlertaDTO;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.service.AlertaInventarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaInventarioServiceImpl implements AlertaInventarioService {

    private final LoteRepository loteRepository;

    public AlertaInventarioServiceImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public List<AlertaDTO> obtenerAlertas() {
        List<Lote> lotes = loteRepository.findAll();
        return evaluarAlertas(lotes);
    }

    @Override
    public List<AlertaDTO> obtenerAlertasPorMedicamento(Long medicamentoId) {
        List<Lote> lotes = loteRepository.findByMedicamentoIdOrderByFechaVencimientoAsc(medicamentoId);
        return evaluarAlertas(lotes);
    }

    private List<AlertaDTO> evaluarAlertas(List<Lote> lotes) {
        List<AlertaDTO> alertas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        LocalDate umbralVencimiento = hoy.plusDays(30);

        for (Lote lote : lotes) {

            // Alerta por stock bajo
            if (lote.getCantidadDisponible() <= lote.getStockMinimo()) {
                alertas.add(new AlertaDTO(
                        "STOCK_BAJO",
                        "Lote con stock bajo",
                        lote
                ));
            }

            // Alerta vencimiento próximo
            if (lote.getFechaVencimiento().isAfter(hoy) &&
                lote.getFechaVencimiento().isBefore(umbralVencimiento)) {
                alertas.add(new AlertaDTO(
                        "VENCIMIENTO_PROXIMO",
                        "Lote vencerá pronto",
                        lote
                ));
            }

            // Alerta lote vencido
            if (lote.getFechaVencimiento().isBefore(hoy)) {
                alertas.add(new AlertaDTO(
                        "VENCIDO",
                        "Lote ya está vencido",
                        lote
                ));
            }
        }

        return alertas;
    }
}
