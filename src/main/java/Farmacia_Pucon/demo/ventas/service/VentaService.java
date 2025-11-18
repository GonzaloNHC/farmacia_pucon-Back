package Farmacia_Pucon.demo.ventas.service;

import java.util.List;

import Farmacia_Pucon.demo.ventas.dto.RegistrarVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.VentaResponseDTO;

public interface VentaService {

    VentaResponseDTO registrarVenta(RegistrarVentaRequest request, String usernameVendedor);

    VentaResponseDTO obtenerVenta(Long id);

    List<VentaResponseDTO> listarVentas();
}
