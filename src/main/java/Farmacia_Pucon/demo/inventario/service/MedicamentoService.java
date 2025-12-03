package Farmacia_Pucon.demo.inventario.service;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;

import java.util.List;

public interface MedicamentoService {

    MedicamentoResponseDTO crear(MedicamentoRequestDTO request);

    MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO request);

    MedicamentoResponseDTO obtenerPorId(Long id);

    List<MedicamentoResponseDTO> listar();

    void eliminar(Long id);

    // Búsqueda general (nombre comercial o genérico)
    List<MedicamentoResponseDTO> buscarPorTexto(String texto);

    // Buscar por código de barras
    MedicamentoResponseDTO buscarPorCodigoBarras(String codigoBarras);

    // Decodificar desde lector
    String decodificarCodigoBarras(String codigoBarras);

    // Búsquedas específicas
    List<MedicamentoResponseDTO> buscarPorCategoria(String categoria);

    List<MedicamentoResponseDTO> buscarPorTipoVenta(String tipoVenta);

    List<MedicamentoResponseDTO> buscarPorLaboratorio(String laboratorio);

    List<MedicamentoResponseDTO> buscarPorFormaFarmaceutica(String formaFarmaceutica);
}