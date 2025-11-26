package Farmacia_Pucon.demo.ventas.domain;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private String estado; // EJ: COMPLETADA, ANULADA

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Pago> pagos = new ArrayList<>();

    // ===== Getters y setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    // ===== Métodos de conveniencia =====

    public void agregarDetalle(DetalleVenta detalle) {
        detalle.setVenta(this);
        this.detalles.add(detalle);
    }

    public void agregarPago(Pago pago) {
        pago.setVenta(this);
        this.pagos.add(pago);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> feature/devpeque
