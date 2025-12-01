package Farmacia_Pucon.demo.inventario.stock.dto;

public class DevolucionVentaRequest {

    private Long ventaId;
    private Long detalleId;
    private Long loteId;
    private Long usuarioId;
    private Integer cantidad;
    private String motivo;

    public Long getVentaId() {
        return ventaId;
    }
    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }
    public Long getDetalleId() {
        return detalleId;
    }
    public void setDetalleId(Long detalleId) {
        this.detalleId = detalleId;
    }
    public Long getLoteId() {
        return loteId;
    }
    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
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