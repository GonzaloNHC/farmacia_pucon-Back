package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.service.InventarioConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioConsultaController {

    private final InventarioConsultaService consultaService;

    public InventarioConsultaController(InventarioConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/productos/{id}/stock-total")
    public ResponseEntity<Integer> obtenerStockTotal(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.obtenerStockTotal(id));
    }

    @GetMapping("/productos/{id}/lotes")
    public ResponseEntity<List<Lote>> obtenerLotes(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.obtenerLotesDeMedicamento(id));
    }

    @GetMapping("/productos/{id}/lote-fefo")
    public ResponseEntity<Lote> obtenerPrimerLoteDisponible(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.obtenerPrimerLoteDisponible(id));
    }
}
