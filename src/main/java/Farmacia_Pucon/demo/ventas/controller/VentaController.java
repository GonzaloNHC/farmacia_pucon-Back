/*package Farmacia_Pucon.demo.ventas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Farmacia_Pucon.demo.ventas.dto.RegistrarVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.VentaResponseDTO;
import Farmacia_Pucon.demo.ventas.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin("*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(
            @RequestBody RegistrarVentaRequest request,
            Principal principal) {

        String username = principal.getName();
        VentaResponseDTO venta = ventaService.registrarVenta(request, username);

        return ResponseEntity.ok(venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVenta(id));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }
}
*/
