package Farmacia_Pucon.demo.ventas.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "boletas")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Folio interno o del SII (por ahora lo puedes simular)
    @Column(nullable = false, unique = true)
    private String folio;

    // Fecha y hora en que se emite la boleta
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    // Monto total de la boleta (puede ser igual al total de la venta)
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    // Estado: EJEMPLO: "PENDIENTE", "ACEPTADA_SII", "RECHAZADA_SII"
    @Column(nullable = false, length = 30)
    private String estado;

    // Id de seguimiento que podría devolver el SII (por ahora simulado)
    @Column(name = "sii_track_id")
    private String siiTrackId;

    // Relación 1:1 con Venta
    @OneToOne
    @JoinColumn(name = "venta_id", nullable = false, unique = true)
    private Venta venta;

    // ====== CONSTRUCTORES ======

    public Boleta() {
    }

    public Boleta(String folio,
                  LocalDateTime fechaEmision,
                  BigDecimal total,
                  String estado,
                  Venta venta) {
        this.folio = folio;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.estado = estado;
        this.venta = venta;
    }

    // ====== GETTERS y SETTERS ======

    public Long getId() {
        return id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSiiTrackId() {
        return siiTrackId;
    }

    public void setSiiTrackId(String siiTrackId) {
        this.siiTrackId = siiTrackId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
