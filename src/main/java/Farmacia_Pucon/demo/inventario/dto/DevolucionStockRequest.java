package Farmacia_Pucon.demo.inventario.dto;

public class DevolucionStockRequest {

    private Integer cantidad;
    private String motivo; // o "referencia", como prefieras

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}