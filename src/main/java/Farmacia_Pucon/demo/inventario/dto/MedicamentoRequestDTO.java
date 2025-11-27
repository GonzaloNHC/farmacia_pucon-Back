package Farmacia_Pucon.demo.inventario.dto;

import jakarta.persistence.Column;

public class MedicamentoRequestDTO {

    private String nombreComercial;
    private String nombreGenerico;
    private String presentacion;
    private String dosificacion;
    private Boolean activo; // si viene null se asume true en el service
    private String codigoBarras;
    private String indicaciones;
    private String contraindicaciones;
    private String advertencias;
    private String interacciones;
    private Boolean requiereReceta = false;
    private String tipoReceta;
    private Boolean controlado = false;

    public MedicamentoRequestDTO() {
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(String dosificacion) {
        this.dosificacion = dosificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Boolean getControlado() {
        return controlado;
    }

    public void setControlado(Boolean controlado) {
        this.controlado = controlado;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }

    public void setTipoReceta(String tipoReceta) {
        this.tipoReceta = tipoReceta;
    }

    public Boolean getRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(Boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public String getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(String interacciones) {
        this.interacciones = interacciones;
    }

    public String getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(String advertencias) {
        this.advertencias = advertencias;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }

    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
