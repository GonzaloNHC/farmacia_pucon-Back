package Farmacia_Pucon.demo.inventario.ingreso.service;

import Farmacia_Pucon.demo.inventario.ingreso.dto.CrearIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.IngresoResponseDTO;

import java.util.List;

public interface IngresoService {

    // CREATE
    IngresoResponseDTO registrarIngreso(CrearIngresoRequest request);

    // READ
    IngresoResponseDTO obtenerIngreso(Long id);

    List<IngresoResponseDTO> listarIngresos();

    // UPDATE (usado por PUT y PATCH)
    IngresoResponseDTO actualizarIngreso(Long id, CrearIngresoRequest request);

    // DELETE
    void eliminarIngreso(Long id);
}