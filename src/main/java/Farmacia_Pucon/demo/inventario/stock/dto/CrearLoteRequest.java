package Farmacia_Pucon.demo.inventario.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CrearLoteRequest {

    private Long medicamentoId;
    private String codigoLote;
    private LocalDate fechaVencimiento;
    private BigDecimal precioUnitario;
    private Integer stockInicial;
    private Integer stockMinimo;
    private BigDecimal costo;
    private LocalDate fechaIngreso;

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }
    
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}
