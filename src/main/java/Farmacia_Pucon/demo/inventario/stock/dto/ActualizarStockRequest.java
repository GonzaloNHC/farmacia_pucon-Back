package Farmacia_Pucon.demo.inventario.stock.dto;

import java.math.BigDecimal;

public class ActualizarStockRequest {

    private Integer stockInicial;
    private Integer stockMinimo;
    private BigDecimal precioUnitario;

    public Integer getStockInicial() {
        return stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
}
