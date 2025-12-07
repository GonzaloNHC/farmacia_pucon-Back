package Farmacia_Pucon.demo.inventario.stock.service;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.stock.dto.*;

import java.util.List;

public interface LoteService {

    LoteResponseDTO crearLote(CrearLoteRequest request);

    List<LoteResponseDTO> listarTodos();

    List<LoteResponseDTO> listarPorMedicamento(Long medicamentoId);

    LoteResponseDTO obtenerPorId(Long id);

    LoteResponseDTO actualizarStock(Long id, ActualizarStockRequest request);

    LoteResponseDTO devolverStock(Long loteId, ActualizarStockRequest.DevolucionStockRequest request);

    //Salidas manuales o especiales (correcciones, devoluciones, fraccionamientos)
    LoteResponseDTO registrarMovimiento(RegistrarMovimientoRequest request);

    //Venta de productos, descargas automáticas por venta
    void registrarSalidaPorVenta(LoteVentaDTO dto, Long ventaId, Long usuarioId);

    void desactivar(Long id);

    LoteResponseDTO devolverStockPorVenta(DevolucionVentaRequest request);

    //Ingreso inicial, recepciones de orden, creación de lote
    void registrarIngresoDeLote(Lote lote, String s);
}
