package Farmacia_Pucon.demo.inventario.dto;

public class MedicamentoRequestDTO {

    private String nombreComercial;
    private String nombreGenerico;
    private String presentacion;
    private String dosificacion;
    private Boolean activo; // opcional, si viene null se asume true

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
}
