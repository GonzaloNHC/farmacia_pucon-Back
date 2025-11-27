package Farmacia_Pucon.demo.inventario.domain;

import jakarta.persistence.*;
import Farmacia_Pucon.demo.common.domain.CodigoBarras;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_comercial", nullable = false)
    private String nombreComercial;

    @Column(name = "nombre_generico", nullable = false)
    private String nombreGenerico;

    @Column(name = "presentacion", nullable = false)
    private String presentacion;

    @Column(name = "dosificacion", nullable = false)
    private String dosificacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Embedded
    private CodigoBarras codigoBarras;

    //Para qué sirve
    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    //Cuándo no usar
    @Column(name = "contraindicaciones", columnDefinition = "TEXT")
    private String contraindicaciones;

    @Column(name = "advertencias", columnDefinition = "TEXT")
    private String advertencias;

    //Con otros medicamentos
    @Column(name = "interacciones", columnDefinition = "TEXT")
    private String interacciones;

    @Column(name = "requiere_receta")
    private Boolean requiereReceta = false;

    //Si se debe retener o no
    @Column(name = "tipo_receta")
    private String tipoReceta;

    //Porque puede causar dependencia
    @Column(name = "es_controlado")
    private Boolean controlado = false;

    public Medicamento() {
    }

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public CodigoBarras getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(CodigoBarras codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }

    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    public String getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(String advertencias) {
        this.advertencias = advertencias;
    }

    public String getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(String interacciones) {
        this.interacciones = interacciones;
    }

    public Boolean getRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(Boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }

    public void setTipoReceta(String tipoReceta) {
        this.tipoReceta = tipoReceta;
    }

    public Boolean getControlado() {
        return controlado;
    }

    public void setControlado(Boolean controlado) {
        this.controlado = controlado;
    }
}
