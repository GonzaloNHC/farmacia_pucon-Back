package Farmacia_Pucon.demo.inventario.dto;

public class MedicamentoResponseDTO {

    private Long id;
    private String nombreComercial;
    private String nombreGenerico;

    private String presentacion;
    private String dosificacion;

    private String categoria;
    private String tipoVenta;
    private String laboratorio;
    private String formaFarmaceutica;

    private Boolean activo;
    private String codigoBarras;

    public MedicamentoResponseDTO(
            Long id,
            String nombreComercial,
            String nombreGenerico,
            String presentacion,
            String dosificacion,
            String categoria,
            String tipoVenta,
            String laboratorio,
            String formaFarmaceutica,
            Boolean activo,
            String codigoBarras
    ) {
        this.id = id;
        this.nombreComercial = nombreComercial;
        this.nombreGenerico = nombreGenerico;
        this.presentacion = presentacion;
        this.dosificacion = dosificacion;
        this.categoria = categoria;
        this.tipoVenta = tipoVenta;
        this.laboratorio = laboratorio;
        this.formaFarmaceutica = formaFarmaceutica;
        this.activo = activo;
        this.codigoBarras = codigoBarras;
    }

    public Long getId() { return id; }
    public String getNombreComercial() { return nombreComercial; }
    public String getNombreGenerico() { return nombreGenerico; }
    public String getPresentacion() { return presentacion; }
    public String getDosificacion() { return dosificacion; }
    public String getCategoria() { return categoria; }
    public String getTipoVenta() { return tipoVenta; }
    public String getLaboratorio() { return laboratorio; }
    public String getFormaFarmaceutica() { return formaFarmaceutica; }
    public Boolean getActivo() { return activo; }
    public String getCodigoBarras() { return codigoBarras; }
}