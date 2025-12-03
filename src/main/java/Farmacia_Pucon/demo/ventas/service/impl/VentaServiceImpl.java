package Farmacia_Pucon.demo.ventas.service.impl;

import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;
import Farmacia_Pucon.demo.authentication.paciente.repository.PacienteRepository;
import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import Farmacia_Pucon.demo.inventario.domain.Lote;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.stock.dto.LoteVentaDTO;
import Farmacia_Pucon.demo.inventario.stock.dto.RegistrarMovimientoRequest;
import Farmacia_Pucon.demo.inventario.stock.service.LoteService;
import Farmacia_Pucon.demo.ventas.domain.DetalleVenta;
import Farmacia_Pucon.demo.ventas.domain.Pago;
import Farmacia_Pucon.demo.ventas.domain.Venta;
import Farmacia_Pucon.demo.ventas.dto.*;
import Farmacia_Pucon.demo.ventas.repository.DetalleVentaRepository;
import Farmacia_Pucon.demo.ventas.repository.PagoRepository;
import Farmacia_Pucon.demo.ventas.repository.VentaRepository;
import Farmacia_Pucon.demo.ventas.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final LoteService loteService;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            DetalleVentaRepository detalleVentaRepository,
                            PagoRepository pagoRepository,
                            LoteRepository loteRepository,
                            PacienteRepository pacienteRepository,
                            UserRepository userRepository,
                            LoteService loteService) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.pagoRepository = pagoRepository;
        this.loteRepository = loteRepository;
        this.pacienteRepository = pacienteRepository;
        this.userRepository = userRepository;
        this.loteService = loteService;
    }

    // ================== Registrar Venta ==================

    @Override
    @Transactional
    public VentaResponseDTO registrarVenta(RegistrarVentaRequest request) {

        // ---- Validaciones de negocio obligatorias ----

        if (request.getUsuarioId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar un usuarioId para registrar la venta"
            );
        }

        if (request.getPacienteId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar un pacienteId para registrar la venta"
            );
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta debe contener al menos un ítem"
            );
        }

        if (request.getPagos() == null || request.getPagos().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta debe tener al menos un pago"
            );
        }

        // ---- Buscar usuario y paciente ----

        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado para id: " + request.getUsuarioId()
                ));

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente no encontrado para id: " + request.getPacienteId()
                ));

        // ---- Crear entidad Venta inicial ----
        // Total se calcula después, usando SIEMPRE el precio del lote (HU17)

        Venta venta = new Venta();
        venta.setFechaHora(LocalDateTime.now());
        venta.setTotal(BigDecimal.ZERO);
        venta.setEstado("COMPLETADA");
        venta.setUsuario(usuario);
        venta.setPaciente(paciente);

        venta = ventaRepository.save(venta);

        BigDecimal total = BigDecimal.ZERO;

        // ---- Crear DetalleVenta por cada ítem + validar/descontar stock ----

        for (ItemVentaRequest itemReq : request.getItems()) {

            if (itemReq.getCantidad() == null || itemReq.getCantidad() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La cantidad debe ser mayor a cero"
                );
            }

            Lote lote = loteRepository.findById(itemReq.getLoteId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Lote no encontrado para id: " + itemReq.getLoteId()
                    ));

            // 🔥 HU17: el precio de venta debe venir DESDE el lote, no desde el request
            BigDecimal precioLote = lote.getPrecioUnitario();
            if (precioLote == null || precioLote.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El lote " + lote.getCodigoLote() + " no tiene un precio de venta válido"
                );
            }

            // Validar stock
            if (lote.getCantidadDisponible() < itemReq.getCantidad()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Stock insuficiente en el lote " + lote.getCodigoLote() +
                                ". Disponible: " + lote.getCantidadDisponible() +
                                ", requerido: " + itemReq.getCantidad()
                );
            }

            // Descontar stock del lote
            int nuevoStock = lote.getCantidadDisponible() - itemReq.getCantidad();
            lote.setCantidadDisponible(nuevoStock);
            loteRepository.save(lote);

            loteService.registrarMovimiento(new RegistrarMovimientoRequest(
                    lote.getId(),
                    itemReq.getCantidad(),
                    "SALIDA",
                    "Venta #" + venta.getId() + " – Lote " + lote.getCodigoLote()
            ));

            // Crear detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setLote(lote);
            detalle.setCantidad(itemReq.getCantidad());
            detalle.setPrecioUnitario(precioLote);

            BigDecimal subtotal = precioLote.multiply(BigDecimal.valueOf(itemReq.getCantidad()));
            detalle.setSubtotal(subtotal);

            detalleVentaRepository.save(detalle);

            // Acumular total
            total = total.add(subtotal);
        }

        // Actualizamos el total real de la venta (precio desde lote)
        venta.setTotal(total);
        venta = ventaRepository.save(venta);

        // ---- Registrar pagos + calcular vuelto efectivo ----

        BigDecimal totalPagado = BigDecimal.ZERO;
        BigDecimal totalEfectivo = BigDecimal.ZERO;

        for (PagoRequest pagoReq : request.getPagos()) {

            if (pagoReq.getMonto() == null ||
                    pagoReq.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El monto del pago debe ser mayor a cero"
                );
            }

            totalPagado = totalPagado.add(pagoReq.getMonto());

            if ("EFECTIVO".equalsIgnoreCase(pagoReq.getMetodoPago())) {
                totalEfectivo = totalEfectivo.add(pagoReq.getMonto());
            }

            Pago pago = new Pago();
            pago.setVenta(venta);
            pago.setMonto(pagoReq.getMonto());
            pago.setMetodoPago(pagoReq.getMetodoPago());
            pago.setFechaHora(LocalDateTime.now());

            pagoRepository.save(pago);
        }

        // Validar que los pagos cubren el total de la venta
        if (totalPagado.compareTo(total) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los pagos no cubren el total de la venta. Total: " + total +
                            ", pagado: " + totalPagado
            );
        }

        // Calcular vuelto solo si existe pago en efectivo y el total pagado supera el total
        BigDecimal vueltoEfectivo = BigDecimal.ZERO;

        if (totalEfectivo.compareTo(BigDecimal.ZERO) > 0 &&
                totalPagado.compareTo(total) > 0) {
            vueltoEfectivo = totalPagado.subtract(total);
        }

        // Guardar vuelto efectivo en la venta (asegúrate de tener este campo en la entidad Venta)
        venta.setVueltoEfectivo(vueltoEfectivo);
        venta = ventaRepository.save(venta);

        // Recargar venta desde BD con detalles y pagos ya asociados
        Venta ventaGuardada = ventaRepository.findById(venta.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error al recuperar la venta registrada"
                ));

        return mapToResponse(ventaGuardada);
    }

    // ================== Obtener y listar ==================

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Venta no encontrada"
                ));
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

    // ================== Comprobante Paciente ==================

    @Override
    @Transactional(readOnly = true)
    public ComprobantePacienteDTO generarComprobantePaciente(Long ventaId) {

        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Venta no encontrada con id: " + ventaId
                ));

        Paciente paciente = venta.getPaciente();
        if (paciente == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta no tiene un paciente asociado, no se puede generar el comprobante."
            );
        }

        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        List<PagoDTO> pagosDTO = venta.getPagos().stream()
                .map(this::mapPagoToDTO)
                .collect(Collectors.toList());

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

        // Si quieres mostrar el vuelto en el comprobante, puedes agregar el campo en el DTO
        // y mapearlo aquí:
        comprobante.setVueltoEfectivo(venta.getVueltoEfectivo());

        return comprobante;
    }

    // ================== Mapeos a DTO ==================

    private VentaResponseDTO mapToResponse(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFechaHora(venta.getFechaHora());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        if (venta.getUsuario() != null) {
            dto.setNombreUsuario(venta.getUsuario().getNombreCompleto());
        }
        if (venta.getPaciente() != null) {
            dto.setNombrePaciente(venta.getPaciente().getNombreCompleto());
        }

        List<DetalleVentaDTO> detalles = venta.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        List<PagoDTO> pagos = venta.getPagos().stream()
                .map(this::mapPagoToDTO)
                .collect(Collectors.toList());

        dto.setDetalles(detalles);
        dto.setPagos(pagos);

        // Si agregas vueltoEfectivo al DTO:
        dto.setVueltoEfectivo(venta.getVueltoEfectivo());

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

    @Override
    @Transactional(readOnly = true)
    public List<LoteVentaDTO> obtenerLotesPorVenta(Long ventaId) {

        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada"));

        return venta.getDetalles().stream().map(det -> {
            Lote lote = det.getLote();

            LoteVentaDTO dto = new LoteVentaDTO();
            dto.setLoteId(lote.getId());
            dto.setCodigoLote(lote.getCodigoLote());
            dto.setMedicamento(lote.getMedicamento().getNombreComercial());
            dto.setCantidadVendida(det.getCantidad());
            dto.setFechaVencimiento(lote.getFechaVencimiento());
            dto.setStockRestante(lote.getCantidadDisponible());

            return dto;
        }).collect(Collectors.toList());
    }

}
