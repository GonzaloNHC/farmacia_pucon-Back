package Farmacia_Pucon.demo.ventas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VentaResponseDTO {

    private Long id;
    private LocalDateTime fechaHora;
    private String nombreUsuario;
    private String nombrePaciente;
    private BigDecimal total;
    private String estado;

    private List<DetalleVentaDTO> detalles;

    public static class DetalleVentaDTO {
        private String nombreProducto;
        private String codigoLote;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;

        // getters y setters
    }

    // getters y setters
}
