package Farmacia_Pucon.demo.inventario.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    private Integer stock = 0;

    @Column(name = "id_proveedor")
    private Long idProveedor;

    // Campo agregado para PPP
    @Column(name = "ppp_actual")
    private Double pppActual = 0.0;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long idProveedor) { this.idProveedor = idProveedor; }

    public Double getPppActual() { return pppActual; }
    public void setPppActual(Double pppActual) { this.pppActual = pppActual; }
}
