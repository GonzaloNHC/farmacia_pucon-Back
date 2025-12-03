package Farmacia_Pucon.demo.inventario.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import Farmacia_Pucon.demo.inventario.domain.*;
import Farmacia_Pucon.demo.inventario.dto.*;
import Farmacia_Pucon.demo.inventario.exception.BadRequestException;
import Farmacia_Pucon.demo.inventario.exception.ResourceNotFoundException;
import Farmacia_Pucon.demo.inventario.mapper.OrdenCompraMapper;
import Farmacia_Pucon.demo.inventario.repository.*;
import Farmacia_Pucon.demo.inventario.service.OrdenCompraService;

@Service
@Transactional
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository ordenRepo;
    private final ProveedorRepository proveedorRepo;
    private final MedicamentoRepository medicamentoRepo;
    private final LoteRepository loteRepo;
    private final MovimientoInventarioRepository movimientoRepo;

    public OrdenCompraServiceImpl(
            OrdenCompraRepository ordenRepo,
            ProveedorRepository proveedorRepo,
            MedicamentoRepository medicamentoRepo,
            LoteRepository loteRepo,
            MovimientoInventarioRepository movimientoRepo
    ) {
        this.ordenRepo = ordenRepo;
        this.proveedorRepo = proveedorRepo;
        this.medicamentoRepo = medicamentoRepo;
        this.loteRepo = loteRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    public OrdenCompraDTO crear(OrdenCompraCreateDTO dto) {

        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new BadRequestException("Proveedor no existe"));

        OrdenCompra oc = new OrdenCompra();
        oc.setProveedor(proveedor);
        oc.setUsuario(dto.getUsuario());
        oc.setEstado("PENDIENTE");

        // Procesar detalles
        List<DetalleOrden> detalles = dto.getDetalles().stream().map(detDto -> {
            DetalleOrden det = new DetalleOrden();
            det.setOrdenCompra(oc);
            det.setProductoId(detDto.getProductoId());
            det.setCantidad(detDto.getCantidad());
            det.setCostoUnitario(detDto.getCostoUnitario());
            return det;
        }).collect(Collectors.toList());

        oc.setDetalles(detalles);

        ordenRepo.save(oc);

        return OrdenCompraMapper.toDto(oc);
    }

    // Mantengo firma original para compatibilidad: si lo llaman sin dto, lanza error
    @Override
    public OrdenCompraDTO recepcionar(Long id) {
        throw new BadRequestException("Debe enviar datos de recepción (código lote, fechaVencimiento, precioUnitario, stockMinimo). Usa recepcionar(id, RecepcionOrdenDTO).");
    }

    @Override
    public OrdenCompraDTO recepcionar(Long id, RecepcionOrdenDTO recepcionDto) {
        OrdenCompra oc = ordenRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        if (oc.getEstado() == null || !"PENDIENTE".equalsIgnoreCase(oc.getEstado())) {
            throw new BadRequestException("La orden no está en estado PENDIENTE y no puede recepcionarse");
        }

        if (recepcionDto == null || CollectionUtils.isEmpty(recepcionDto.getItems())) {
            throw new BadRequestException("Datos de recepción vacíos");
        }

        // Para cada item de recepción, buscar el detalle correspondiente en la orden
        for (RecepcionItemDTO item : recepcionDto.getItems()) {

            if (item.getDetalleId() == null) {
                throw new BadRequestException("detalleId es obligatorio en cada item de recepción");
            }

            DetalleOrden det = oc.getDetalles().stream()
                    .filter(d -> d.getId().equals(item.getDetalleId()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("DetalleOrden id=" + item.getDetalleId() + " no pertenece a la orden"));

            // Validaciones mínimas
            if (item.getCodigoLote() == null || item.getCodigoLote().trim().isEmpty()) {
                throw new BadRequestException("codigoLote es obligatorio para detalleId=" + item.getDetalleId());
            }
            if (item.getFechaVencimiento() == null || item.getFechaVencimiento().trim().isEmpty()) {
                throw new BadRequestException("fechaVencimiento es obligatoria para detalleId=" + item.getDetalleId());
            }
            if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException("precioUnitario inválido para detalleId=" + item.getDetalleId());
            }
            if (item.getStockMinimo() == null || item.getStockMinimo() < 0) {
                throw new BadRequestException("stockMinimo inválido para detalleId=" + item.getDetalleId());
            }

            // Buscar medicamento por productoId (campo en DetalleOrden)
            Medicamento med = medicamentoRepo.findById(det.getProductoId())
                    .orElseThrow(() -> new BadRequestException("Medicamento id=" + det.getProductoId() + " no existe"));

            // Parsear fechaVencimiento (formato yyyy-MM-dd)
            LocalDate fechaVenc;
            try {
                fechaVenc = LocalDate.parse(item.getFechaVencimiento());
            } catch (Exception ex) {
                throw new BadRequestException("fechaVencimiento con formato inválido para detalleId=" + item.getDetalleId() + ". Usa yyyy-MM-dd");
            }

            // Crear Lote
            Lote lote = new Lote();
            lote.setMedicamento(med);
            lote.setCodigoLote(item.getCodigoLote());
            lote.setFechaVencimiento(fechaVenc);
            lote.setCosto(det.getCostoUnitario()); // costo viene de la orden
            lote.setPrecioUnitario(item.getPrecioUnitario());
            lote.setStockInicial(det.getCantidad());
            lote.setCantidadDisponible(det.getCantidad());
            lote.setStockMinimo(item.getStockMinimo());
            BigDecimal precioTotal = item.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantidad()));
            lote.setPrecioTotalLote(precioTotal);
            lote.setFechaIngreso(LocalDate.now());
            lote.setActivo(true);

            loteRepo.save(lote);

            // Registrar movimiento en MovimientoInventario (kardex)
            MovimientoInventario mov = new MovimientoInventario();
            mov.setLote(lote);
            mov.setFechaHora(LocalDateTime.now());
            mov.setCantidad(det.getCantidad()); // entrada positiva
            mov.setTipo("INGRESO");
            mov.setReferencia("Recepción OC #" + oc.getId());
            movimientoRepo.save(mov);

            // (Opcional) Si tu Medicamento tiene un campo stockTotal y deseas actualizarlo,
            // descomenta y ajusta según tu entidad Medicamento.
            // if (med.getStockTotal() != null) {
            //     med.setStockTotal(med.getStockTotal() + det.getCantidad());
            //     medicamentoRepo.save(med);
            // }
        }

        // Finalmente, marcar la orden como RECIBIDA
        oc.setEstado("RECIBIDA");
        ordenRepo.save(oc);

        return OrdenCompraMapper.toDto(oc);
    }

    @Override
    public List<OrdenCompraDTO> listarPendientes() {
        return ordenRepo.findAll().stream()
                .filter(oc -> oc.getEstado() != null && oc.getEstado().equalsIgnoreCase("PENDIENTE"))
                .map(OrdenCompraMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCompraDTO> listarTodas() {
        return ordenRepo.findAll().stream()
                .map(OrdenCompraMapper::toDto)
                .collect(Collectors.toList());
    }
}
