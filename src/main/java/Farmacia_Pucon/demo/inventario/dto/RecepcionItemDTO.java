package Farmacia_Pucon.demo.inventario.dto;

import java.math.BigDecimal;

public class RecepcionItemDTO {
    private Long detalleId;            // id del DetalleOrden
    private String codigoLote;         // código del lote que ingresa
    private String fechaVencimiento;   // "yyyy-MM-dd"
    private BigDecimal precioUnitario; // precio unitario de venta sugerido
    private Integer stockMinimo;       // stock mínimo para ese lote/medicamento

    // getters y setters
    public Long getDetalleId() { return detalleId; }
    public void setDetalleId(Long detalleId) { this.detalleId = detalleId; }

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
}
