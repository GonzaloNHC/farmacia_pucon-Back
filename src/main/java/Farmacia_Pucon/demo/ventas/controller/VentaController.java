package Farmacia_Pucon.demo.ventas.controller;

import Farmacia_Pucon.demo.ventas.dto.RegistrarVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.ComprobantePacienteDTO;
import Farmacia_Pucon.demo.ventas.dto.VentaResponseDTO;
import Farmacia_Pucon.demo.ventas.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin("*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrar(
            @RequestBody RegistrarVentaRequest request,
            Principal principal
    ) {
        String username = principal != null ? principal.getName() : null;
        VentaResponseDTO respuesta = ventaService.registrarVenta(request, username);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVenta(id));
    }

    @GetMapping("/{id}/comprobante-paciente")
    public ResponseEntity<ComprobantePacienteDTO> obtenerComprobantePaciente(@PathVariable Long id) {
        ComprobantePacienteDTO comprobante = ventaService.generarComprobantePaciente(id);
        return ResponseEntity.ok(comprobante);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }
}
