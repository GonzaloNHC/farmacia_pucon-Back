package Farmacia_Pucon.demo.inventario.ingreso.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IngresoResponseDTO {

    private Long id;
    private LocalDateTime fechaIngreso;
    private String observacion;
    private List<DetalleIngresoResponseDTO> detalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<DetalleIngresoResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleIngresoResponseDTO> detalles) {
        this.detalles = detalles;
    }
}
