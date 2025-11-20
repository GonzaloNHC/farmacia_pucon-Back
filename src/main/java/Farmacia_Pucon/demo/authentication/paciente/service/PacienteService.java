package Farmacia_Pucon.demo.authentication.paciente.service;

import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;

public interface PacienteService {

    PacienteResponseDTO crearPaciente(PacienteRequestDTO request);

    PacienteResponseDTO obtenerPacientePorId(Long id);

    PacienteResponseDTO obtenerPacientePorRut(String rut);
}
