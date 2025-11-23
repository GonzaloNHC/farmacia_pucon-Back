package Farmacia_Pucon.demo.ventas.service.impl;

import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;
import Farmacia_Pucon.demo.authentication.paciente.repository.PacienteRepository;
import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.ventas.domain.DetalleVenta;
import Farmacia_Pucon.demo.ventas.domain.Pago;
import Farmacia_Pucon.demo.ventas.domain.Venta;
import Farmacia_Pucon.demo.ventas.dto.ComprobantePacienteDTO;
import Farmacia_Pucon.demo.ventas.dto.DetalleVentaDTO;
import Farmacia_Pucon.demo.ventas.dto.PagoDTO;
import Farmacia_Pucon.demo.ventas.dto.PagoRequest;
import Farmacia_Pucon.demo.ventas.dto.ItemVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.RegistrarVentaRequest;
import Farmacia_Pucon.demo.ventas.dto.VentaResponseDTO;
import Farmacia_Pucon.demo.ventas.repository.DetalleVentaRepository;
import Farmacia_Pucon.demo.ventas.repository.PagoRepository;
import Farmacia_Pucon.demo.ventas.repository.VentaRepository;
import Farmacia_Pucon.demo.ventas.service.VentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final PagoRepository pagoRepository;
    private final LoteRepository loteRepository;
    private final PacienteRepository pacienteRepository;
    private final UserRepository userRepository;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            DetalleVentaRepository detalleVentaRepository,
                            PagoRepository pagoRepository,
                            LoteRepository loteRepository,
                            PacienteRepository pacienteRepository,
                            UserRepository userRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.pagoRepository = pagoRepository;
        this.loteRepository = loteRepository;
        this.pacienteRepository = pacienteRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public VentaResponseDTO registrarVenta(RegistrarVentaRequest request, String usernameCajero) {

        // Validaciones básicas
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("La venta debe contener al menos un ítem");
        }
        if (request.getPagos() == null || request.getPagos().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un pago");
        }

        // 1) Calcular total de la venta a partir de los ítems
        BigDecimal total = request.getItems().stream()
                .map(item -> {
                    if (item.getCantidad() == null || item.getCantidad() <= 0) {
                        throw new RuntimeException("La cantidad debe ser mayor a cero");
                    }
                    if (item.getPrecioUnitario() == null ||
                            item.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new RuntimeException("El precioUnitario debe ser mayor a cero");
                    }
                    return item.getPrecioUnitario()
                            .multiply(BigDecimal.valueOf(item.getCantidad()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2) Crear entidad Venta
        Venta venta = new Venta();
        venta.setFechaHora(LocalDateTime.now());
        venta.setTotal(total);
        venta.setEstado("COMPLETADA"); // Podrías usar otros estados más adelante

        // 2.a) Asociar usuario (cajero) a la venta
        if (usernameCajero != null && !usernameCajero.isBlank()) {
            User usuario = userRepository.findByUsername(usernameCajero)
                    .orElseThrow(() -> new RuntimeException("Usuario (cajero) no encontrado: " + usernameCajero));
            venta.setUsuario(usuario);
        }

        // 2.b) Asociar paciente a la venta (si viene en el request)
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() ->
                            new RuntimeException("Paciente no encontrado para id: " + request.getPacienteId())
                    );
            venta.setPaciente(paciente);
        }

        venta = ventaRepository.save(venta);

        // 3) Crear DetalleVenta por cada ítem + VALIDACIÓN DE STOCK + DESCUENTO
        for (ItemVentaRequest itemReq : request.getItems()) {

            Lote lote = loteRepository.findById(itemReq.getLoteId())
                    .orElseThrow(() ->
                            new RuntimeException("Lote no encontrado para id: " + itemReq.getLoteId()));

            // ===== VALIDAR STOCK =====
            if (lote.getCantidadDisponible() < itemReq.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente en el lote " + lote.getCodigoLote() +
                                ". Disponible: " + lote.getCantidadDisponible() +
                                ", requerido: " + itemReq.getCantidad()
                );
            }

            // ===== DESCONTAR STOCK =====
            int nuevoStock = lote.getCantidadDisponible() - itemReq.getCantidad();
            lote.setCantidadDisponible(nuevoStock);
            loteRepository.save(lote);

            // ===== CREAR DETALLE =====
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setLote(lote);
            detalle.setCantidad(itemReq.getCantidad());
            detalle.setPrecioUnitario(itemReq.getPrecioUnitario());

            BigDecimal subtotal = itemReq.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(itemReq.getCantidad()));
            detalle.setSubtotal(subtotal);

            detalleVentaRepository.save(detalle);
        }

        // 4) Registrar pagos
        for (PagoRequest pagoReq : request.getPagos()) {

            if (pagoReq.getMonto() == null ||
                    pagoReq.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El monto del pago debe ser mayor a cero");
            }

            Pago pago = new Pago();
            pago.setVenta(venta);
            pago.setMonto(pagoReq.getMonto());
            pago.setMetodoPago(pagoReq.getMetodoPago());
            pago.setFechaHora(LocalDateTime.now());

            pagoRepository.save(pago);
        }

        // 5) Recargar venta desde BD con detalles y pagos ya asociados
        Venta ventaGuardada = ventaRepository.findById(venta.getId())
                .orElseThrow(() -> new RuntimeException("Error al recuperar la venta registrada"));

        return mapToResponse(ventaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return mapToResponse(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ================== HU-20: Comprobante de Paciente ==================

    @Override
    @Transactional(readOnly = true)
    public ComprobantePacienteDTO generarComprobantePaciente(Long ventaId) {

        // 1) Buscar la venta
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));

        // 2) Verificar que tenga paciente asociado
        Paciente paciente = venta.getPaciente();
        if (paciente == null) {
            throw new RuntimeException("La venta no tiene un paciente asociado, no se puede generar el comprobante.");
        }

        // 3) Mapear detalles y pagos
        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        List<PagoDTO> pagosDTO = venta.getPagos().stream()
                .map(this::mapPagoToDTO)
                .collect(Collectors.toList());

        // 4) Construir el comprobante
        ComprobantePacienteDTO comprobante = new ComprobantePacienteDTO();
        comprobante.setIdVenta(venta.getId());
        comprobante.setFechaHoraVenta(venta.getFechaHora());
        comprobante.setTotal(venta.getTotal());
        comprobante.setEstado(venta.getEstado());

        comprobante.setPacienteId(paciente.getId());
        comprobante.setRutPaciente(paciente.getRut());
        comprobante.setNombrePaciente(paciente.getNombreCompleto());
        comprobante.setTelefonoPaciente(paciente.getTelefono());
        comprobante.setDireccionPaciente(paciente.getDireccion());
        comprobante.setEmailPaciente(paciente.getEmail());

        if (venta.getUsuario() != null) {
            comprobante.setNombreUsuario(venta.getUsuario().getNombreCompleto());
        }

        comprobante.setDetalles(detallesDTO);
        comprobante.setPagos(pagosDTO);

        return comprobante;
    }

    // ================== Mapeos a DTO ==================

    private VentaResponseDTO mapToResponse(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFechaHora(venta.getFechaHora());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        List<DetalleVentaDTO> detalles = venta.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        List<PagoDTO> pagos = venta.getPagos().stream()
                .map(this::mapPagoToDTO)
                .collect(Collectors.toList());

        dto.setDetalles(detalles);
        dto.setPagos(pagos);

        return dto;
    }

    private DetalleVentaDTO mapDetalleToDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();

        dto.setLoteId(detalle.getLote().getId());
        dto.setMedicamentoNombre(detalle.getLote().getMedicamento().getNombreComercial());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());

        return dto;
    }

    private PagoDTO mapPagoToDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getId());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setMonto(pago.getMonto());
        dto.setFechaHora(pago.getFechaHora());
        return dto;
    }
}
