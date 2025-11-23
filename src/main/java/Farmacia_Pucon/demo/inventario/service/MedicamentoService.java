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

    List<MedicamentoResponseDTO> buscarPorTexto(String texto);
}
