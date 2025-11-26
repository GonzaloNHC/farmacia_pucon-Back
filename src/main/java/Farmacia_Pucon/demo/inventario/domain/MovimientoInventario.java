package Farmacia_Pucon.demo.inventario.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lote sobre el cual se hizo el movimiento
    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /**
     * Cantidad del movimiento.
     * - Positivo -> entra stock (DEVOLUCION, AJUSTE POSITIVO)
     * - Negativo -> sale stock (VENTA, AJUSTE NEGATIVO)
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Tipo de movimiento, por ejemplo:
     * "VENTA", "DEVOLUCION", "AJUSTE"
     */
    @Column(nullable = false)
    private String tipo;

    /**
     * Campo libre para trazabilidad:
     * Ej: "Devolución de venta 15", "Ajuste inventario", etc.
     */
    private String referencia;

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}