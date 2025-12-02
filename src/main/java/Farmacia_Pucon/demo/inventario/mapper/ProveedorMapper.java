package Farmacia_Pucon.demo.inventario.mapper;

import Farmacia_Pucon.demo.inventario.domain.Proveedor;
import Farmacia_Pucon.demo.inventario.dto.ProveedorCreateDTO;
import Farmacia_Pucon.demo.inventario.dto.ProveedorDTO;

public class ProveedorMapper {
   public static ProveedorDTO toDto(Proveedor p) {
        if (p == null) return null;
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(p.getId());
        dto.setRut(p.getRut());
        dto.setNombre(p.getNombre());
        dto.setContacto(p.getContacto());
        dto.setCorreo(p.getCorreo());
        dto.setActivo(p.getActivo());
        return dto;
    }

    public static Proveedor toEntity(ProveedorCreateDTO dto) {
        if (dto == null) return null;
        Proveedor p = new Proveedor();
        p.setRut(dto.getRut());
        p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());
        p.setCorreo(dto.getCorreo());
        p.setActivo(true);
        return p;
    }

    public static void updateEntityFromDto(ProveedorCreateDTO dto, Proveedor p) {
        if (dto.getNombre() != null) p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());
        p.setCorreo(dto.getCorreo());
        if (dto.getRut() != null) p.setRut(dto.getRut());
    }
}