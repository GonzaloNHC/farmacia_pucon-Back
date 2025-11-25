package Farmacia_Pucon.demo.inventario.ingreso.controller;

import Farmacia_Pucon.demo.inventario.ingreso.dto.CrearIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.IngresoResponseDTO;
import Farmacia_Pucon.demo.inventario.ingreso.service.IngresoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    // =============== CREATE ===============

    @PostMapping
    public ResponseEntity<IngresoResponseDTO> registrarIngreso(
            @RequestBody CrearIngresoRequest request
    ) {
        IngresoResponseDTO creado = ingresoService.registrarIngreso(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // =============== READ ===============

    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponseDTO> obtenerIngreso(@PathVariable Long id) {
        return ResponseEntity.ok(ingresoService.obtenerIngreso(id));
    }

    @GetMapping
    public ResponseEntity<List<IngresoResponseDTO>> listarIngresos() {
        return ResponseEntity.ok(ingresoService.listarIngresos());
    }

    // =============== UPDATE ===============

    @PutMapping("/{id}")
    public ResponseEntity<IngresoResponseDTO> actualizarIngreso(
            @PathVariable Long id,
            @RequestBody CrearIngresoRequest request
    ) {
        IngresoResponseDTO actualizado = ingresoService.actualizarIngreso(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // Si quieres soportar PATCH, reutilizamos la misma lógica
    @PatchMapping("/{id}")
    public ResponseEntity<IngresoResponseDTO> patchIngreso(
            @PathVariable Long id,
            @RequestBody CrearIngresoRequest request
    ) {
        IngresoResponseDTO actualizado = ingresoService.actualizarIngreso(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // =============== DELETE ===============

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable Long id) {
        ingresoService.eliminarIngreso(id);
        return ResponseEntity.noContent().build();
    }
}
