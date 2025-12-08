package Farmacia_Pucon.demo.inventario.service;

import Farmacia_Pucon.demo.inventario.dto.AlertaDTO;
import java.util.List;

public interface AlertaInventarioService {

    List<AlertaDTO> obtenerAlertas();

    List<AlertaDTO> obtenerAlertasPorMedicamento(Long medicamentoId);
}
