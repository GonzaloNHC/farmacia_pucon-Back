package Farmacia_Pucon.demo.inventario.dto;

import java.time.LocalDateTime;

public class MovimientoInventarioDTO {

    private Long id;
    private String tipo;
    private Integer cantidad;
    private String descripcion;
    private LocalDateTime fechaHora;

    private Long loteId;
    private Long medicamentoId;
    //private Long usuarioId;
    //private String usuarioNombre;

    public MovimientoInventarioDTO() {
    }

    public MovimientoInventarioDTO(
            Long id,
            String tipo,
            Integer cantidad,
            String descripcion,
            LocalDateTime fechaHora,
            Long loteId,
            Long medicamentoId
    ) {
        this.id = id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.loteId = loteId;
        this.medicamentoId = medicamentoId;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }*/
}
