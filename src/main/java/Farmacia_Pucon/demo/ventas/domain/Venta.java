package Farmacia_Pucon.demo.ventas.domain;

import Farmacia_Pucon.demo.usuarios.domain.Usuario;
import Farmacia_Pucon.demo.pacientes.domain.Paciente;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    private BigDecimal total;

    private String estado; // REALIZADA, ANULADA

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    // Getters y setters
}
