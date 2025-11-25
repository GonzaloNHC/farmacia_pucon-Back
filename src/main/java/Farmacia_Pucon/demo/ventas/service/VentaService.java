package Farmacia_Pucon.demo.ventas.service;

import Farmacia_Pucon.demo.ventas.dto.RegistrarVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.VentaResponseDTO;
import Farmacia_Pucon.demo.ventas.dto.ComprobantePacienteDTO;

import java.util.List;

public interface VentaService {

    VentaResponseDTO registrarVenta(RegistrarVentaRequest request);

    VentaResponseDTO obtenerVenta(Long id);

    List<VentaResponseDTO> listarVentas();

    ComprobantePacienteDTO generarComprobantePaciente(Long ventaId);
}
