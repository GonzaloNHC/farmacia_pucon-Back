package Farmacia_Pucon.demo.inventario.service;

import Farmacia_Pucon.demo.inventario.dto.MedicamentoVademecumDTO;

import java.util.List;

public interface VademecumService {

    MedicamentoVademecumDTO obtenerPorId(Long medicamentoId);

    List<MedicamentoVademecumDTO> buscarPorTexto(String texto);

    List<MedicamentoVademecumDTO> obtenerEquivalentes(Long medicamentoId);
}
