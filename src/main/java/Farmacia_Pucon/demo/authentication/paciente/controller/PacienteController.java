package Farmacia_Pucon.demo.authentication.paciente.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;
import Farmacia_Pucon.demo.authentication.paciente.service.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PACIENTE_CREAR')")
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO creado = pacienteService.crearPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE_VER')")
    public ResponseEntity<PacienteResponseDTO> obtenerPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorId(id));
    }

    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasAuthority('PACIENTE_VER')")
    public ResponseEntity<PacienteResponseDTO> obtenerPacientePorRut(@PathVariable String rut) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorRut(rut));
    }
}
