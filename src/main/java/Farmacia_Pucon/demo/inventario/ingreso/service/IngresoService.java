package Farmacia_Pucon.demo.inventario.ingreso.service;

import Farmacia_Pucon.demo.inventario.ingreso.dto.CrearIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.IngresoResponseDTO;

import java.util.List;

public interface IngresoService {

    IngresoResponseDTO registrarIngreso(CrearIngresoRequest request);

    IngresoResponseDTO obtenerIngreso(Long id);

    List<IngresoResponseDTO> listarIngresos();
}
