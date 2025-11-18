package Farmacia_Pucon.demo.ventas.domain;

import Farmacia_Pucon.demo.productos.domain.Producto;
import Farmacia_Pucon.demo.productos.domain.Lote;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    // getters y setters
}
