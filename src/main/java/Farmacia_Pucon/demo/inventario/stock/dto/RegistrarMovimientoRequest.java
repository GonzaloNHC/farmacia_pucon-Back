package Farmacia_Pucon.demo.inventario.stock.dto;

public class RegistrarMovimientoRequest {

    private Long loteId;
    private Integer cantidad;      // positivo entra stock / negativo sale stock
    private String tipo;           // "DEVOLUCION", "AJUSTE", "VENTA"
    private String referencia;     // texto libre

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
