package Farmacia_Pucon.demo.inventario.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoteResponseDTO {

    private Long id;
    private Long medicamentoId;
    private String nombreMedicamento;
    private String codigoLote;
    private LocalDate fechaVencimiento;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotalLote;
    private Integer stockInicial;
    private Integer stockMinimo;
    private Integer cantidadDisponible;
    private Boolean activo;

    public LoteResponseDTO(
            Long id,
            Long medicamentoId,
            String nombreMedicamento,
            String codigoLote,
            LocalDate fechaVencimiento,
            BigDecimal precioUnitario,
            BigDecimal precioTotalLote,
            Integer stockInicial,
            Integer stockMinimo,
            Integer cantidadDisponible,
            Boolean activo
    ) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.nombreMedicamento = nombreMedicamento;
        this.codigoLote = codigoLote;
        this.fechaVencimiento = fechaVencimiento;
        this.precioUnitario = precioUnitario;
        this.precioTotalLote = precioTotalLote;
        this.stockInicial = stockInicial;
        this.stockMinimo = stockMinimo;
        this.cantidadDisponible = cantidadDisponible;
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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getPrecioTotalLote() {return precioTotalLote;}

    public Integer getStockInicial() {
        return stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public Boolean getActivo() {
        return activo;
    }
}
