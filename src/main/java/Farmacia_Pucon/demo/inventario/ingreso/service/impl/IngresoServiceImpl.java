package Farmacia_Pucon.demo.inventario.ingreso.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.ingreso.domain.DetalleIngreso;
import Farmacia_Pucon.demo.inventario.ingreso.domain.Ingreso;
import Farmacia_Pucon.demo.inventario.ingreso.dto.CrearIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.DetalleIngresoRequest;
import Farmacia_Pucon.demo.inventario.ingreso.dto.DetalleIngresoResponseDTO;
import Farmacia_Pucon.demo.inventario.ingreso.dto.IngresoResponseDTO;
import Farmacia_Pucon.demo.inventario.ingreso.repository.DetalleIngresoRepository;
import Farmacia_Pucon.demo.inventario.ingreso.repository.IngresoRepository;
import Farmacia_Pucon.demo.inventario.ingreso.service.IngresoService;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngresoServiceImpl implements IngresoService {

    private final IngresoRepository ingresoRepository;
    private final DetalleIngresoRepository detalleIngresoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final LoteRepository loteRepository;

    public IngresoServiceImpl(IngresoRepository ingresoRepository,
                              DetalleIngresoRepository detalleIngresoRepository,
                              MedicamentoRepository medicamentoRepository,
                              LoteRepository loteRepository) {
        this.ingresoRepository = ingresoRepository;
        this.detalleIngresoRepository = detalleIngresoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.loteRepository = loteRepository;
    }

    // ================== CREAR ==================

    @Override
    @Transactional
    public IngresoResponseDTO registrarIngreso(CrearIngresoRequest request) {

        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new RuntimeException("El ingreso debe contener al menos un detalle");
        }

        // 1) Crear la entidad Ingreso
        Ingreso ingreso = new Ingreso();
        ingreso.setFechaIngreso(LocalDateTime.now());
        ingreso.setObservacion(request.getObservacion());

        ingreso = ingresoRepository.save(ingreso);

        // 2) Crear DetalleIngreso + Lote por cada detalle
        for (DetalleIngresoRequest detReq : request.getDetalles()) {

            if (detReq.getMedicamentoId() == null) {
                throw new RuntimeException("El medicamentoId es obligatorio en cada detalle");
            }
            if (detReq.getCantidad() == null || detReq.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero en cada detalle");
            }
            if (detReq.getFechaVencimiento() == null) {
                throw new RuntimeException("La fecha de vencimiento es obligatoria en cada detalle");
            }
            if (detReq.getCodigoLote() == null || detReq.getCodigoLote().isBlank()) {
                throw new RuntimeException("El código de lote es obligatorio en cada detalle");
            }

            Medicamento medicamento = medicamentoRepository.findById(detReq.getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Medicamento no encontrado para id: " + detReq.getMedicamentoId()));

            // Crear el Lote asociado
            Lote lote = new Lote();
            lote.setMedicamento(medicamento);
            lote.setCodigoLote(detReq.getCodigoLote());
            lote.setFechaVencimiento(detReq.getFechaVencimiento());
            lote.setStockInicial(detReq.getCantidad());
            lote.setCantidadDisponible(detReq.getCantidad());
            lote.setStockMinimo(0); // puedes cambiar esto si manejas stock mínimo
            lote.setActivo(true);

            lote = loteRepository.save(lote);

            // Crear el DetalleIngreso
            DetalleIngreso detalle = new DetalleIngreso();
            detalle.setIngreso(ingreso);
            detalle.setMedicamento(medicamento);
            detalle.setLote(lote);
            detalle.setCantidad(detReq.getCantidad());
            detalle.setFechaVencimiento(detReq.getFechaVencimiento());
            detalle.setPrecioCompra(detReq.getPrecioCompra());

            detalleIngresoRepository.save(detalle);
        }

        // 3) Recargar el ingreso con los detalles ya persistidos
        Ingreso ingresoGuardado = ingresoRepository.findById(ingreso.getId())
                .orElseThrow(() -> new RuntimeException("Error al recuperar el ingreso registrado"));

        return mapToResponse(ingresoGuardado);
    }

    // ================== LEER ==================

    @Override
    @Transactional(readOnly = true)
    public IngresoResponseDTO obtenerIngreso(Long id) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
        return mapToResponse(ingreso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngresoResponseDTO> listarIngresos() {
        return ingresoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ================== ACTUALIZAR ==================
    // Para no romper stock ni lotes, solo permitimos cambiar la observación.

    @Override
    @Transactional
    public IngresoResponseDTO actualizarIngreso(Long id, CrearIngresoRequest request) {

        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));

        // Por ahora solo actualizamos la observación
        ingreso.setObservacion(request.getObservacion());

        Ingreso actualizado = ingresoRepository.save(ingreso);

        return mapToResponse(actualizado);
    }

    // ================== ELIMINAR ==================
    // Eliminamos ingreso y sus detalles. No tocamos lotes para no romper ventas.

    @Override
    @Transactional
    public void eliminarIngreso(Long id) {

        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));

        // Borrar primero detalles (por FK)
        if (ingreso.getDetalles() != null && !ingreso.getDetalles().isEmpty()) {
            detalleIngresoRepository.deleteAll(ingreso.getDetalles());
        }

        // Luego el ingreso
        ingresoRepository.delete(ingreso);
    }

    // ================== MAPEOS A DTO ==================

    private IngresoResponseDTO mapToResponse(Ingreso ingreso) {
        IngresoResponseDTO dto = new IngresoResponseDTO();
        dto.setId(ingreso.getId());
        dto.setFechaIngreso(ingreso.getFechaIngreso());
        dto.setObservacion(ingreso.getObservacion());

        List<DetalleIngresoResponseDTO> detalles = ingreso.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        dto.setDetalles(detalles);

        return dto;
    }

    private DetalleIngresoResponseDTO mapDetalleToDTO(DetalleIngreso detalle) {
        DetalleIngresoResponseDTO dto = new DetalleIngresoResponseDTO();
        dto.setId(detalle.getId());
        dto.setMedicamentoId(detalle.getMedicamento().getId());
        dto.setMedicamentoNombre(detalle.getMedicamento().getNombreComercial());
        dto.setLoteId(detalle.getLote().getId());
        dto.setCodigoLote(detalle.getLote().getCodigoLote());
        dto.setCantidad(detalle.getCantidad());
        dto.setFechaVencimiento(detalle.getFechaVencimiento());
        return dto;
    }
}
