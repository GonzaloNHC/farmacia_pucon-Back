package Farmacia_Pucon.demo.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Farmacia_Pucon.demo.inventario.dto.ProveedorCreateDTO;
import Farmacia_Pucon.demo.inventario.dto.ProveedorDTO;
import Farmacia_Pucon.demo.inventario.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> crear(@RequestBody ProveedorCreateDTO dto) {
        ProveedorDTO creado = service.crear(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listar(@RequestParam(name = "activo", required = false) Boolean activo) {
        List<ProveedorDTO> lista = service.listar(activo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Long id, @RequestBody ProveedorCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<ProveedorDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(service.activar(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ProveedorDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }
}