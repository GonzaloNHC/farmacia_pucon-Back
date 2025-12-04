package Farmacia_Pucon.demo.inventario.controller;

import Farmacia_Pucon.demo.inventario.domain.MovimientoInventario;
import Farmacia_Pucon.demo.inventario.dto.MovimientoInventarioDTO;
import Farmacia_Pucon.demo.inventario.repository.MovimientoInventarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kardex")
@CrossOrigin("*")
public class MovimientoInventarioController {

    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public MovimientoInventarioController(MovimientoInventarioRepository movimientoInventarioRepository) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    // ------- Helper para mapear a DTO --------
    private MovimientoInventarioDTO mapToDTO(MovimientoInventario m) {

        Long loteId = null;
        Long medicamentoId = null;

        if (m.getLote() != null) {
            loteId = m.getLote().getId();
        }

        // Si MovimientoInventario tiene campo "medicamento"
        if (m.getMedicamento() != null) {
            medicamentoId = m.getMedicamento().getId();
        }
        // (opcional) fallback por si en algún momento sólo viene por el lote
        else if (m.getLote() != null && m.getLote().getMedicamento() != null) {
            medicamentoId = m.getLote().getMedicamento().getId();
        }

        return new MovimientoInventarioDTO(
                m.getId(),
                m.getTipo(),
                m.getCantidad(),
                m.getReferencia(),
                m.getFechaHora(),
                loteId,
                medicamentoId
        );
    }

    // 🔹 Trazabilidad por producto (medicamento)
    @GetMapping("/producto/{id}")
    public List<MovimientoInventarioDTO> kardexPorProducto(@PathVariable Long id) {
        return movimientoInventarioRepository.findByMedicamento_Id(id)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Trazabilidad por tipo (INGRESO, SALIDA, DEVOLUCION, etc.)
    @GetMapping("/tipo/{tipo}")
    public List<MovimientoInventarioDTO> kardexPorTipo(@PathVariable String tipo) {
        return movimientoInventarioRepository.findByTipo(tipo)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Trazabilidad por lote
    @GetMapping("/lote/{id}")
    public List<MovimientoInventarioDTO> kardexPorLote(@PathVariable Long id) {
        return movimientoInventarioRepository.findByLoteId(id)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Kardex completo ordenado por fecha desc
    @GetMapping("/ordenado")
    public List<MovimientoInventarioDTO> obtenerKardexOrdenado() {
        return movimientoInventarioRepository.findAllByOrderByFechaHoraDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
