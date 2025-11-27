package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoVademecumDTO;
import Farmacia_Pucon.demo.inventario.service.VademecumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vademecum")
@CrossOrigin("*")
public class VademecumController {

    private final VademecumService vademecumService;

    public VademecumController(VademecumService vademecumService) {
        this.vademecumService = vademecumService;
    }

    /**
     * Obtener ficha Vademécum de un medicamento específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoVademecumDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vademecumService.obtenerPorId(id));
    }

    /**
     * Buscar medicamentos para Vademécum por texto (nombre comercial o genérico)
     * Ej: GET /api/vademecum?texto=parac
     */
    @GetMapping
    public ResponseEntity<List<MedicamentoVademecumDTO>> buscarPorTexto(
            @RequestParam("texto") String texto
    ) {
        return ResponseEntity.ok(vademecumService.buscarPorTexto(texto));
    }

    @GetMapping("/{id}/equivalentes")
    public ResponseEntity<List<MedicamentoVademecumDTO>> obtenerEquivalentes(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(vademecumService.obtenerEquivalentes(id));
    }
}
