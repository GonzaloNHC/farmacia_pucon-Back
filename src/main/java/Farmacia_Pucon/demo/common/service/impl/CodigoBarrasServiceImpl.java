package Farmacia_Pucon.demo.common.service.impl;

import Farmacia_Pucon.demo.common.domain.CodigoBarras;
import Farmacia_Pucon.demo.common.service.CodigoBarrasService;
import Farmacia_Pucon.demo.common.util.EncryptionUtil;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import org.springframework.stereotype.Service;

@Service
public class CodigoBarrasServiceImpl implements CodigoBarrasService {

    @Override
    public CodigoBarras generarParaMedicamento(Medicamento medicamento) {
        // 1) Construimos el “payload” con los atributos del medicamento
        //    Puedes cambiar el formato, aquí uso un separador simple.
        String payload = String.join("|",
                medicamento.getNombreComercial(),
                medicamento.getNombreGenerico(),
                medicamento.getPresentacion(),
                medicamento.getDosificacion()
        );

        // 2) Encriptamos el payload
        String cifrado = EncryptionUtil.encrypt(payload);

        // 3) Lo guardamos en el value object
        return new CodigoBarras(cifrado);
    }

    @Override
    public String desencriptar(String codigoBarras) {
        return EncryptionUtil.decrypt(codigoBarras);
    }
}
