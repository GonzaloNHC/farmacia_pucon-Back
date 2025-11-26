package Farmacia_Pucon.demo.authentication.paciente.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;
import Farmacia_Pucon.demo.authentication.paciente.repository.PacienteRepository;
import Farmacia_Pucon.demo.authentication.paciente.service.PacienteService;

import java.util.List;
import java.util.stream.Collectors;

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

    // ===== NUEVOS MÉTODOS =====

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        // Actualizar campos
        paciente.setRut(request.getRut());
        paciente.setNombreCompleto(request.getNombreCompleto());
        paciente.setTelefono(request.getTelefono());
        paciente.setDireccion(request.getDireccion());
        paciente.setEmail(request.getEmail());

        Paciente actualizado = pacienteRepository.save(paciente);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    // ===== MAPPER =====

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