package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;
import Farmacia_Pucon.demo.inventario.service.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin("*")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO request) {
        return ResponseEntity.ok(medicamentoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> listar() {
        return ResponseEntity.ok(medicamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody MedicamentoRequestDTO request
    ) {
        return ResponseEntity.ok(medicamentoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para buscar por texto (nombre comercial o genérico)
    @GetMapping("/buscar")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscar(@RequestParam("texto") String texto) {
        return ResponseEntity.ok(medicamentoService.buscarPorTexto(texto));
    }
}
