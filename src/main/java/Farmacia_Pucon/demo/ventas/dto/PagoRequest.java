package Farmacia_Pucon.demo.ventas.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoRequest {

    private String metodoPago;
    private BigDecimal monto;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
