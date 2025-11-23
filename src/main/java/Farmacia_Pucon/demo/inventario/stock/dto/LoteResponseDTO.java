package Farmacia_Pucon.demo.inventario.stock.dto;

import java.time.LocalDate;

public class LoteResponseDTO {

    private Long id;
    private Long medicamentoId;
    private String nombreMedicamento;  // opcional, para mostrar en front
    private String codigoLote;
    private LocalDate fechaVencimiento;
    private Integer stockInicial;
    private Integer stockMinimo;
    private Boolean activo;

    public LoteResponseDTO(
            Long id,
            Long medicamentoId,
            String nombreMedicamento,
            String codigoLote,
            LocalDate fechaVencimiento,
            Integer stockInicial,
            Integer stockMinimo,
            Boolean activo
    ) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.nombreMedicamento = nombreMedicamento;
        this.codigoLote = codigoLote;
        this.fechaVencimiento = fechaVencimiento;
        this.stockInicial = stockInicial;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public Boolean getActivo() {
        return activo;
    }
}
