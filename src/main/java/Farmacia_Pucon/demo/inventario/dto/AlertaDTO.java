package Farmacia_Pucon.demo.inventario.dto;

import Farmacia_Pucon.demo.inventario.domain.Lote;

public class AlertaDTO {

    private String tipo;  // STOCK_BAJO, VENCIMIENTO_PROXIMO, VENCIDO
    private String mensaje;
    private Lote lote;

    public AlertaDTO(String tipo, String mensaje, Lote lote) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.lote = lote;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Lote getLote() {
        return lote;
    }
}
