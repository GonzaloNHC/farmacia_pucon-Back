package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.dto.AlertaDTO;
import Farmacia_Pucon.demo.inventario.service.AlertaInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaInventarioController {

    private final AlertaInventarioService alertaService;

    public AlertaInventarioController(AlertaInventarioService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> getAlertas() {
        return ResponseEntity.ok(alertaService.obtenerAlertas());
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<List<AlertaDTO>> getAlertasPorMedicamento(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.obtenerAlertasPorMedicamento(id));
    }
}
