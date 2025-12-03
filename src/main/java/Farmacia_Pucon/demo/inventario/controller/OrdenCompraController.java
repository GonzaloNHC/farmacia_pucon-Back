package Farmacia_Pucon.demo.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import Farmacia_Pucon.demo.inventario.dto.*;
import Farmacia_Pucon.demo.inventario.service.OrdenCompraService;

@RestController
@RequestMapping("/api/orden-compra")
public class OrdenCompraController {

    private final OrdenCompraService service;

    public OrdenCompraController(OrdenCompraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrdenCompraDTO> crear(@RequestBody OrdenCompraCreateDTO dto) {
        return ResponseEntity.status(201).body(service.crear(dto));
    }

    @PutMapping("/{id}/recepcionar")
    public ResponseEntity<OrdenCompraDTO> recepcionar(
            @PathVariable Long id,
            @RequestBody RecepcionOrdenDTO recepcionDto) {
        return ResponseEntity.ok(service.recepcionar(id, recepcionDto));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<OrdenCompraDTO>> listarPendientes() {
        return ResponseEntity.ok(service.listarPendientes());
    }

    @GetMapping
    public ResponseEntity<List<OrdenCompraDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}
