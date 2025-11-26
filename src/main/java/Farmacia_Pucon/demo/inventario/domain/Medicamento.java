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
}
