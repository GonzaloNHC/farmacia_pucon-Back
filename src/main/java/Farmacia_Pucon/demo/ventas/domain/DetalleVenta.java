package Farmacia_Pucon.demo.ventas.domain;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== RELACIÓN CON VENTA =====
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // ===== RELACIÓN CON LOTE =====
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    // ===== CONSTRUCTORES =====

    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Lote lote, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.venta = venta;
        this.lote = lote;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
