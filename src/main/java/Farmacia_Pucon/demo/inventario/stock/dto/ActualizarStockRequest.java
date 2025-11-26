package Farmacia_Pucon.demo.inventario.stock.dto;

import java.math.BigDecimal;

public class ActualizarStockRequest {

    private Integer stockInicial;
    private Integer stockMinimo;
    private BigDecimal PrecioUnitario;

    public Integer getStockInicial() {
        return stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public BigDecimal getPrecioUnitario() {
        return PrecioUnitario;
    }
}
