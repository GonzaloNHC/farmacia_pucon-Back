package Farmacia_Pucon.demo.authentication.paciente.service;

import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO crearPaciente(PacienteRequestDTO request);

    PacienteResponseDTO obtenerPacientePorId(Long id);

    PacienteResponseDTO obtenerPacientePorRut(String rut);

    // NUEVOS
    List<PacienteResponseDTO> listarPacientes();

    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request);

    void eliminarPaciente(Long id);
}