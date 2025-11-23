package Farmacia_Pucon.demo.ventas.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoDTO {

    private Long idPago;
    private String metodoPago;
    private BigDecimal monto;
    private LocalDateTime fechaHora;

    public Long getIdPago() {
        return idPago;
    }   
    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

}


