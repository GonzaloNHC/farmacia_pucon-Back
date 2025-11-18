package Farmacia_Pucon.demo.inventario.service;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;

import java.util.List;

public interface MedicamentoService {

    MedicamentoResponseDTO crear(MedicamentoRequestDTO request);

    List<MedicamentoResponseDTO> listar();

    MedicamentoResponseDTO obtener(Long id);

    MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO request);

    void eliminar(Long id); // soft delete (activo = false)

    List<MedicamentoResponseDTO> buscarPorTexto(String texto);
}
