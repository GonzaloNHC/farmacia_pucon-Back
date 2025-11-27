package Farmacia_Pucon.demo.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoteVademecumDTO {

    private Long loteId;
    private String codigoLote;
    private LocalDate fechaVencimiento;
    private Integer cantidadDisponible;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotalLote;

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotalLote() {
        return precioTotalLote;
    }

    public void setPrecioTotalLote(BigDecimal precioTotalLote) {
        this.precioTotalLote = precioTotalLote;
    }
}
