package Farmacia_Pucon.demo.inventario.stock.service;

import Farmacia_Pucon.demo.inventario.stock.dto.*;
import Farmacia_Pucon.demo.inventario.dto.DevolucionStockRequest;

import java.util.List;

public interface LoteService {

    LoteResponseDTO crearLote(CrearLoteRequest request);

    List<LoteResponseDTO> listarTodos();

    List<LoteResponseDTO> listarPorMedicamento(Long medicamentoId);

    LoteResponseDTO obtenerPorId(Long id);

    LoteResponseDTO actualizarStock(Long id, ActualizarStockRequest request);

    LoteResponseDTO devolverStock(Long loteId, DevolucionStockRequest request);

    LoteResponseDTO registrarMovimiento(RegistrarMovimientoRequest request);

    void desactivar(Long id);

    LoteResponseDTO devolverStockPorVenta(DevolucionVentaRequest request);

}
