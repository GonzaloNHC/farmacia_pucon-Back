package Farmacia_Pucon.demo.authentication.paciente.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteRequestDTO;
import Farmacia_Pucon.demo.authentication.paciente.dto.PacienteResponseDTO;
import Farmacia_Pucon.demo.authentication.paciente.service.PacienteService;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin("*")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /** CREAR */
    @PostMapping
    @PreAuthorize("hasAuthority('PACIENTE_CREAR')")
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO creado = pacienteService.crearPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** OBTENER POR ID */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE_VER')")
    public ResponseEntity<PacienteResponseDTO> obtenerPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorId(id));
    }

    /** OBTENER POR RUT */
    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasAuthority('PACIENTE_VER')")
    public ResponseEntity<PacienteResponseDTO> obtenerPacientePorRut(@PathVariable String rut) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorRut(rut));
    }

    /** LISTAR TODOS */
    @GetMapping
    @PreAuthorize("hasAuthority('PACIENTE_VER')")
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    /** ACTUALIZAR (PUT COMPLETO) */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE_CREAR')") // o 'PACIENTE_EDITAR' si lo tienes
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request
    ) {
        PacienteResponseDTO actualizado = pacienteService.actualizarPaciente(id, request);
        return ResponseEntity.ok(actualizado);
    }

    /** ELIMINAR */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE_CREAR')") // o 'PACIENTE_ELIMINAR' si existe
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}