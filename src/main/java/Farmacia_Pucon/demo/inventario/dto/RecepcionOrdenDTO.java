package Farmacia_Pucon.demo.inventario.dto;

import java.util.List;

public class RecepcionOrdenDTO {
    private List<RecepcionItemDTO> items;

    public List<RecepcionItemDTO> getItems() { return items; }
    public void setItems(List<RecepcionItemDTO> items) { this.items = items; }
}
