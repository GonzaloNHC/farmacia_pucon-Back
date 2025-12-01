package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;
import Farmacia_Pucon.demo.inventario.service.MedicamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin("*")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    // =====================
    // CRUD
    // =====================

    @PostMapping
    public List<MedicamentoResponseDTO> crear(@RequestBody Object body) {

        if (body instanceof List<?> lista) {
            return lista.stream()
                    .map(item -> objectMapper.convertValue(item, MedicamentoRequestDTO.class))
                    .map(medicamentoService::crear)
                    .toList();
        }

        MedicamentoRequestDTO request =
                objectMapper.convertValue(body, MedicamentoRequestDTO.class);

        return List.of(medicamentoService.crear(request));
    }


    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> listar() {
        return ResponseEntity.ok(medicamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody MedicamentoRequestDTO request
    ) {
        return ResponseEntity.ok(medicamentoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // =====================
    // BÚSQUEDAS AVANZADAS
    // =====================

    @GetMapping("/buscar")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscar(@RequestParam("texto") String texto) {
        return ResponseEntity.ok(medicamentoService.buscarPorTexto(texto));
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarPorCategoria(
            @RequestParam("value") String categoria
    ) {
        return ResponseEntity.ok(medicamentoService.buscarPorCategoria(categoria));
    }

    @GetMapping("/buscar/tipo-venta")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarPorTipoVenta(
            @RequestParam("value") String tipoVenta
    ) {
        return ResponseEntity.ok(medicamentoService.buscarPorTipoVenta(tipoVenta));
    }

    @GetMapping("/buscar/laboratorio")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarPorLaboratorio(
            @RequestParam("value") String laboratorio
    ) {
        return ResponseEntity.ok(medicamentoService.buscarPorLaboratorio(laboratorio));
    }

    @GetMapping("/buscar/forma-farmaceutica")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarPorFormaFarmaceutica(
            @RequestParam("value") String forma
    ) {
        return ResponseEntity.ok(medicamentoService.buscarPorFormaFarmaceutica(forma));
    }

    // =====================
    // CÓDIGO DE BARRAS
    // =====================

    @GetMapping("/codigo-barras/{codigo}")
    public ResponseEntity<MedicamentoResponseDTO> buscarPorCodigoBarras(@PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(medicamentoService.buscarPorCodigoBarras(codigo));
    }

    @PostMapping("/codigo-barras/decodificar")
    public ResponseEntity<String> decodificar(@RequestBody String codigo) {
        return ResponseEntity.ok(
                medicamentoService.decodificarCodigoBarras(codigo.trim())
        );
    }
}
