package Farmacia_Pucon.demo.authentication.paciente.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;
import Farmacia_Pucon.demo.authentication.paciente.repository.PacienteRepository;
import Farmacia_Pucon.demo.authentication.paciente.service.PacienteService;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponseDTO crearPaciente(PacienteRequestDTO request) {

        if (pacienteRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("Ya existe un paciente con ese RUT");
        }

        Paciente paciente = new Paciente(
                request.getRut(),
                request.getNombreCompleto(),
                request.getTelefono(),
                request.getDireccion(),
                request.getEmail()
        );

        Paciente guardado = pacienteRepository.save(paciente);

        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        return mapToResponse(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPacientePorRut(String rut) {
        Paciente paciente = pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        return mapToResponse(paciente);
    }

    private PacienteResponseDTO mapToResponse(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getRut(),
                paciente.getNombreCompleto(),
                paciente.getTelefono(),
                paciente.getDireccion(),
                paciente.getEmail()
        );
    }
}
