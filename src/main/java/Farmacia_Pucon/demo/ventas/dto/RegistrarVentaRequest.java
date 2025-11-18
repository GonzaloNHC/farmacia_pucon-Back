package Farmacia_Pucon.demo.ventas.dto;

import java.util.List;

public class RegistrarVentaRequest {

    private Long pacienteId;
    private List<ItemVentaRequest> items;
    private PagoRequest pago;

    // getters y setters
}
