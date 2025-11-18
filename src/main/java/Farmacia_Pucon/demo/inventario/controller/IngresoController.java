package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.dto.IngresoDTO;
import Farmacia_Pucon.demo.inventario.service.IngresoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingreso")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    @PostMapping
    public ResponseEntity<?> ingresar(@RequestBody IngresoDTO dto) {
        ingresoService.ingresar(dto);
        return ResponseEntity.ok("Ingreso registrado correctamente");
    }
}
