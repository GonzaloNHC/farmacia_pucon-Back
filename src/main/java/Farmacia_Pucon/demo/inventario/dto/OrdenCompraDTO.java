package Farmacia_Pucon.demo.inventario.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompraDTO {
    private Long id;
    private ProveedorDTO proveedor;
    private String usuario;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<DetalleOrdenDTO> detalles;

    //setter y getter
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProveedorDTO getProveedor() {
        return proveedor;
    }
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public List<DetalleOrdenDTO> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleOrdenDTO> detalles) {
        this.detalles = detalles;
    }

}
