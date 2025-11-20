package Farmacia_Pucon.demo.inventario.dto;

import java.time.LocalDate;

public class IngresoDTO {

    private Long idProducto;
    private Integer cantidadNueva;
    private Double precioNuevo;
    private String lote;
    private LocalDate vencimiento;

    // Getters y setters

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidadNueva() {
        return cantidadNueva;
    }

    public void setCantidadNueva(Integer cantidadNueva) {
        this.cantidadNueva = cantidadNueva;
    }

    // <-- Este es el getter que buscaba el service
    public Double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(Double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }
}
