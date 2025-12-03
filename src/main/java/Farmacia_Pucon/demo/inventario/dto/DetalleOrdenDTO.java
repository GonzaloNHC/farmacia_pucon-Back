package Farmacia_Pucon.demo.inventario.dto;

import java.math.BigDecimal;

public class DetalleOrdenDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal costoUnitario;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductoId() {
        return productoId;
    }
    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }
}
