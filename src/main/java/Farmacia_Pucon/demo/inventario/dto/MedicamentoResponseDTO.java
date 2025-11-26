package Farmacia_Pucon.demo.inventario.dto;

public class MedicamentoResponseDTO {

    private Long id;
    private String nombreComercial;
    private String nombreGenerico;
    private String presentacion;
    private String dosificacion;
    private Boolean activo;
    private String codigoBarras;


    public MedicamentoResponseDTO(
            Long id,
            String nombreComercial,
            String nombreGenerico,
            String presentacion,
            String dosificacion,
            Boolean activo,
            String codigoBarras
    ) {
        this.id = id;
        this.nombreComercial = nombreComercial;
        this.nombreGenerico = nombreGenerico;
        this.presentacion = presentacion;
        this.dosificacion = dosificacion;
        this.activo = activo;
        this.codigoBarras = codigoBarras;
    }

    public Long getId() {
        return id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String getDosificacion() {
        return dosificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }
}
