package Farmacia_Pucon.demo.inventario.stock.service;

import Farmacia_Pucon.demo.inventario.stock.dto.ActualizarStockRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.CrearLoteRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.LoteResponseDTO;

import java.util.List;

public interface LoteService {

    LoteResponseDTO crearLote(CrearLoteRequest request);

    List<LoteResponseDTO> listarTodos();

    List<LoteResponseDTO> listarPorMedicamento(Long medicamentoId);

    LoteResponseDTO obtenerPorId(Long id);

    LoteResponseDTO actualizarStock(Long id, ActualizarStockRequest request);

    void desactivar(Long id);
}
