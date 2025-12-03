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
    private MovimientoInventarioDTO toDTO(MovimientoInventario m) {

        Long loteId = null;
        Long medicamentoId = null;

        if (m.getLote() != null) {
            loteId = m.getLote().getId();
            if (m.getLote().getMedicamento() != null) {
                medicamentoId = m.getLote().getMedicamento().getId();
            }
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

    // Consultar movimientos por producto (medicamento)
    @GetMapping("/producto/{id}")
    public List<MovimientoInventarioDTO> kardexPorProducto(@PathVariable Long id) {
        return movimientoInventarioRepository.findByLote_Medicamento_Id(id)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Consultar movimientos por tipo (ENTRADA, SALIDA, DEVOLUCION, etc.)
    @GetMapping("/tipo/{tipo}")
    public List<MovimientoInventarioDTO> kardexPorTipo(@PathVariable String tipo) {
        return movimientoInventarioRepository.findByTipo(tipo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Consultar movimientos por lote
    @GetMapping("/lote/{id}")
    public List<MovimientoInventarioDTO> kardexPorLote(@PathVariable Long id) {
        return movimientoInventarioRepository.findByLoteId(id)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener el kardex completo ordenado por fecha descendente
    @GetMapping("/ordenado")
    public List<MovimientoInventarioDTO> obtenerKardexOrdenado() {
        return movimientoInventarioRepository.findAllByOrderByFechaHoraDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
