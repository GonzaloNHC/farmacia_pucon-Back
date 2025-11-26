package Farmacia_Pucon.demo.ventas.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegistrarVentaRequest {

    private Long usuarioId;                  // 👈 nuevo: quién vende
    private Long pacienteId;                 // quién compra
    private List<ItemVentaRequest> items;    // detalle de venta
    private List<PagoRequest> pagos;         // pagos

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

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