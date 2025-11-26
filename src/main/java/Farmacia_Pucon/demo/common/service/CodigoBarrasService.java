package Farmacia_Pucon.demo.common.service;

import Farmacia_Pucon.demo.common.domain.CodigoBarras;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;

public interface CodigoBarrasService {

    CodigoBarras generarParaMedicamento(Medicamento medicamento);

    String desencriptar(String codigoBarras);
}
