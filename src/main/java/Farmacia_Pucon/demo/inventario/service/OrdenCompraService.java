package Farmacia_Pucon.demo.inventario.service;

import java.util.List;

import Farmacia_Pucon.demo.inventario.dto.OrdenCompraCreateDTO;
import Farmacia_Pucon.demo.inventario.dto.OrdenCompraDTO;
import Farmacia_Pucon.demo.inventario.dto.RecepcionOrdenDTO;

public interface OrdenCompraService {
    OrdenCompraDTO crear(OrdenCompraCreateDTO dto);
    OrdenCompraDTO recepcionar(Long id); 
    OrdenCompraDTO recepcionar(Long id, RecepcionOrdenDTO dto); 
    List<OrdenCompraDTO> listarPendientes();
    List<OrdenCompraDTO> listarTodas();
}
