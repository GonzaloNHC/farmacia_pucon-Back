package Farmacia_Pucon.demo.ventas.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegistrarVentaRequest {

    private Long pacienteId;
    private List<ItemVentaRequest> items;
    private List<PagoRequest> pagos;

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public List<ItemVentaRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemVentaRequest> items) {
        this.items = items;
    }

    public List<PagoRequest> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoRequest> pagos) {
        this.pagos = pagos;
    }
}
