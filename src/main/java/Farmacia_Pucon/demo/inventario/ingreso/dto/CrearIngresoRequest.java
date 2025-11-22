package Farmacia_Pucon.demo.inventario.ingreso.dto;

import java.util.List;

public class CrearIngresoRequest {

    private String observacion;
    private List<DetalleIngresoRequest> detalles;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<DetalleIngresoRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleIngresoRequest> detalles) {
        this.detalles = detalles;
    }
}
