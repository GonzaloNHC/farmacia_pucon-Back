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

}
