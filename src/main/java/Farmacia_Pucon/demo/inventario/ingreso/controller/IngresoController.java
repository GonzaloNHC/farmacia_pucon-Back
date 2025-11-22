package Farmacia_Pucon.demo.inventario.ingreso.controller;

import Farmacia_Pucon.demo.inventario.ingreso.dto.CrearIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.IngresoResponseDTO;
import Farmacia_Pucon.demo.inventario.ingreso.service.IngresoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
@CrossOrigin("*")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    // ================================
    //     REGISTRAR NUEVO INGRESO
    // ================================
    @PostMapping
    public ResponseEntity<IngresoResponseDTO> registrarIngreso(
            @RequestBody CrearIngresoRequest request
    ) {
        IngresoResponseDTO respuesta = ingresoService.registrarIngreso(request);
        return ResponseEntity.ok(respuesta);
    }

    // ================================
    //       OBTENER INGRESO POR ID
    // ================================
    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponseDTO> obtenerIngreso(@PathVariable Long id) {
        return ResponseEntity.ok(ingresoService.obtenerIngreso(id));
    }

    // ================================
    //          LISTAR INGRESOS
    // ================================
    @GetMapping
    public ResponseEntity<List<IngresoResponseDTO>> listarIngresos() {
        return ResponseEntity.ok(ingresoService.listarIngresos());
    }
}
