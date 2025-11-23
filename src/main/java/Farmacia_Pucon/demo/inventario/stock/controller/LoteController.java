package Farmacia_Pucon.demo.inventario.stock.controller;

import Farmacia_Pucon.demo.inventario.stock.dto.ActualizarStockRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.CrearLoteRequest;
import Farmacia_Pucon.demo.inventario.stock.dto.LoteResponseDTO;
import Farmacia_Pucon.demo.inventario.stock.service.LoteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lotes")
@CrossOrigin("*")
public class LoteController {

    private final LoteService loteService;

    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @PostMapping
    public ResponseEntity<LoteResponseDTO> crear(@RequestBody CrearLoteRequest request) {
        return ResponseEntity.ok(loteService.crearLote(request));
    }

    @GetMapping
    public ResponseEntity<List<LoteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(loteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.obtenerPorId(id));
    }

    @GetMapping("/medicamento/{medicamentoId}")
    public ResponseEntity<List<LoteResponseDTO>> listarPorMedicamento(@PathVariable Long medicamentoId) {
        return ResponseEntity.ok(loteService.listarPorMedicamento(medicamentoId));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<LoteResponseDTO> actualizarStock(
            @PathVariable Long id,
            @RequestBody ActualizarStockRequest request
    ) {
        return ResponseEntity.ok(loteService.actualizarStock(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        loteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
