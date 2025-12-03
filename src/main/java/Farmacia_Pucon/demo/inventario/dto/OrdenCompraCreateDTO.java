package Farmacia_Pucon.demo.inventario.dto;

import java.util.List;

public class OrdenCompraCreateDTO {
    private Long proveedorId;
    private String usuario;
    private List<DetalleOrdenCreateDTO> detalles;

    // getters y setters
    public Long getProveedorId() {
        return proveedorId;
    }
    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public List<DetalleOrdenCreateDTO> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleOrdenCreateDTO> detalles) {
        this.detalles = detalles;
    }

}
