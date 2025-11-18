package Farmacia_Pucon.demo.ventas.service.impl;
/*
import Farmacia_Pucon.demo.ventas.domain.*;
import Farmacia_Pucon.demo.ventas.dto.*;
import Farmacia_Pucon.demo.ventas.repository.*;
import Farmacia_Pucon.demo.ventas.service.VentaService;
import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import Farmacia_Pucon.demo.pacientes.domain.Paciente;
import Farmacia_Pucon.demo.pacientes.repository.PacienteRepository;
import Farmacia_Pucon.demo.productos.domain.Producto;
import Farmacia_Pucon.demo.productos.domain.Lote;
import Farmacia_Pucon.demo.productos.repository.ProductoRepository;
import Farmacia_Pucon.demo.productos.repository.LoteRepository;

import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleRepository;
    private final PagoRepository pagoRepository;
    private final UserRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final ProductoRepository productoRepository;
    private final LoteRepository loteRepository;

    public VentaServiceImpl(
            VentaRepository ventaRepository,
            DetalleVentaRepository detalleRepository,
            PagoRepository pagoRepository,
            UserRepository usuarioRepository,
            PacienteRepository pacienteRepository,
            ProductoRepository productoRepository,
            LoteRepository loteRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.detalleRepository = detalleRepository;
        this.pagoRepository = pagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.productoRepository = productoRepository;
        this.loteRepository = loteRepository;
    }

    @Override
    @Transactional
    public VentaResponseDTO registrarVenta(RegistrarVentaRequest request, String usernameVendedor) {

        User usuario = usuarioRepository.findByUsername(usernameVendedor)
                .orElseThrow(() -> new RuntimeException("Usuario vendedor no encontrado"));

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Venta venta = new Venta();
        venta.setFechaHora(LocalDateTime.now());
        venta.setUsuario(usuario);
        venta.setPaciente(paciente);
        venta.setEstado("REALIZADA");

        venta = ventaRepository.save(venta);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemVentaRequest item : request.getItems()) {

            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Lote lote = loteRepository.findById(item.getLoteId())
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

            if (lote.getStockActual() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente en el lote " + lote.getCodigo());
            }

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setLote(lote);
            detalle.setCantidad(item.getCantidad());

            BigDecimal precioUnitario = producto.getPrecioVenta();
            detalle.setPrecioUnitario(precioUnitario);

            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));
            detalle.setSubtotal(subtotal);

            total = total.add(subtotal);

            detalleRepository.save(detalle);

            lote.setStockActual(lote.getStockActual() - item.getCantidad());
            loteRepository.save(lote);
        }

        venta.setTotal(total);
        venta = ventaRepository.save(venta);

        Pago pago = new Pago();
        pago.setVenta(venta);
        pago.setFechaHora(LocalDateTime.now());
        pago.setMetodoPago(request.getPago().getMetodoPago());
        pago.setMonto(request.getPago().getMonto());

        pagoRepository.save(pago);

        return mapToResponseDTO(venta);
    }

    @Override
    public VentaResponseDTO obtenerVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        return mapToResponseDTO(venta);
    }

    @Override
    public List<VentaResponseDTO> listarVentas() {
        return ventaRepository.findAll()
                .stream().map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private VentaResponseDTO mapToResponseDTO(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();

        dto.setId(venta.getId());
        dto.setFechaHora(venta.getFechaHora());
        dto.setNombreUsuario(venta.getUsuario().getUsername());
        dto.setNombrePaciente(venta.getPaciente().getNombreCompleto());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        List<VentaResponseDTO.DetalleVentaDTO> detalles =
                venta.getDetalles().stream().map(det -> {
                    VentaResponseDTO.DetalleVentaDTO d = new VentaResponseDTO.DetalleVentaDTO();
                    d.setNombreProducto(det.getProducto().getNombre());
                    d.setCodigoLote(det.getLote().getCodigo());
                    d.setCantidad(det.getCantidad());
                    d.setPrecioUnitario(det.getPrecioUnitario());
                    d.setSubtotal(det.getSubtotal());
                    return d;
                }).collect(Collectors.toList());

        dto.setDetalles(detalles);

        return dto;
    }
}
*/
