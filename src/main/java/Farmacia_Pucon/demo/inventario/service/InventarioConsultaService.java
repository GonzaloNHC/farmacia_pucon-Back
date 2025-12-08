package Farmacia_Pucon.demo.inventario.service;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import java.util.List;

public interface InventarioConsultaService {

    int obtenerStockTotal(Long medicamentoId);

    List<Lote> obtenerLotesDeMedicamento(Long medicamentoId);

    Lote obtenerPrimerLoteDisponible(Long medicamentoId); // FEFO
}
