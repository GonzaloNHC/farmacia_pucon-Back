package Farmacia_Pucon.demo.inventario.ingreso.dto;

import java.time.LocalDate;

public class DetalleIngresoResponseDTO {

    private Long id;
    private Long medicamentoId;
    private String medicamentoNombre;
    private Long loteId;
    private String codigoLote;
    private Integer cantidad;
    private LocalDate fechaVencimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getMedicamentoNombre() {
        return medicamentoNombre;
    }

    public void setMedicamentoNombre(String medicamentoNombre) {
        this.medicamentoNombre = medicamentoNombre;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
