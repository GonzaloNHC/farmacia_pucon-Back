package Farmacia_Pucon.demo.inventario.mapper;

import java.util.stream.Collectors;

import Farmacia_Pucon.demo.inventario.domain.*;
import Farmacia_Pucon.demo.inventario.dto.*;

public class OrdenCompraMapper {

    public static OrdenCompraDTO toDto(OrdenCompra oc) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setId(oc.getId());
        dto.setUsuario(oc.getUsuario());
        dto.setFechaCreacion(oc.getFechaCreacion());
        dto.setEstado(oc.getEstado());
        dto.setProveedor(ProveedorMapper.toDto(oc.getProveedor()));
        dto.setDetalles(
            oc.getDetalles().stream().map(DetalleOrdenMapper::toDto).collect(Collectors.toList())
        );
        return dto;
    }
}
