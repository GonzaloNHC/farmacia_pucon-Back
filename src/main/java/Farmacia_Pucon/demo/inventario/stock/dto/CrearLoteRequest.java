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
}
