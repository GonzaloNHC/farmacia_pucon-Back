package Farmacia_Pucon.demo.inventario.dto;

import java.util.List;

public class MedicamentoVademecumDTO {

    private Long id;
    private String nombreComercial;
    private String nombreGenerico;
    private String presentacion;
    private String dosificacion;
    private Boolean activo;
    private Integer stockTotal;
    private List<LoteVademecumDTO> lotes;

    private String indicaciones;
    private String contraindicaciones;
    private String advertencias;
    private String interacciones;

    private Boolean requiereReceta;
    private String tipoReceta;
    private Boolean controlado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void setDosificacion(String dosificacion, Boolean activo) {}

    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }
    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public List<LoteVademecumDTO> getLotes() {
        return lotes;
    }
    public void setLotes(List<LoteVademecumDTO> lotes) {
        this.lotes = lotes;
    }

    public String getIndicaciones() {
        return indicaciones;
    }
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getContraindicaciones() { return contraindicaciones; }
    public void setContraindicaciones(String contraindicaciones) { this.contraindicaciones = contraindicaciones; }

    public String getAdvertencias() { return advertencias; }
    public void setAdvertencias(String advertencias) { this.advertencias = advertencias; }

    public String getInteracciones() { return interacciones; }
    public void setInteracciones(String interacciones) { this.interacciones = interacciones; }

    public Boolean getRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(Boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    public String getTipoReceta() { return tipoReceta; }
    public void setTipoReceta(String tipoReceta) { this.tipoReceta = tipoReceta; }

    public Boolean getControlado() { return controlado; }
    public void setControlado(Boolean controlado) { this.controlado = controlado; }
}
