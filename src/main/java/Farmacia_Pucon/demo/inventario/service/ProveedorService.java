package Farmacia_Pucon.demo.inventario.service;

import java.util.List;
import Farmacia_Pucon.demo.inventario.dto.ProveedorCreateDTO;
import Farmacia_Pucon.demo.inventario.dto.ProveedorDTO;

public interface ProveedorService {

    ProveedorDTO crear(ProveedorCreateDTO dto);

    ProveedorDTO actualizar(Long id, ProveedorCreateDTO dto);

    ProveedorDTO obtenerPorId(Long id);

    List<ProveedorDTO> listar(Boolean activo);

    void softDelete(Long id);

    ProveedorDTO activar(Long id);

    ProveedorDTO desactivar(Long id);
}
