package Farmacia_Pucon.demo.inventario.mapper;

import Farmacia_Pucon.demo.inventario.domain.DetalleOrden;
import Farmacia_Pucon.demo.inventario.dto.DetalleOrdenDTO;

public class DetalleOrdenMapper {

    public static DetalleOrdenDTO toDto(DetalleOrden det) {
        DetalleOrdenDTO dto = new DetalleOrdenDTO();
        dto.setId(det.getId());
        dto.setProductoId(det.getProductoId());
        dto.setCantidad(det.getCantidad());
        dto.setCostoUnitario(det.getCostoUnitario());
        return dto;
    }
}
