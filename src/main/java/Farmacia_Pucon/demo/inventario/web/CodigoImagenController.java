package Farmacia_Pucon.demo.inventario.web;

import Farmacia_Pucon.demo.common.service.BarcodeService;
import Farmacia_Pucon.demo.common.service.QrCodeService;
import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codigos")
public class CodigoImagenController {

    private final MedicamentoRepository medicamentoRepository;
    private final QrCodeService qrService;
    private final BarcodeService barcodeService;

    public CodigoImagenController(
            MedicamentoRepository medicamentoRepository,
            QrCodeService qrService,
            BarcodeService barcodeService) {
        this.medicamentoRepository = medicamentoRepository;
        this.qrService = qrService;
        this.barcodeService = barcodeService;
    }

    // ------------------ QR ------------------
    @GetMapping(value = "/medicamento/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQr(@PathVariable Long id) {

        Medicamento m = medicamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        String texto = m.getCodigoBarras().getValor(); // tu campo "valor"

        return ResponseEntity.ok(qrService.generarQrPng(texto));
    }

    // ------------------ Código de Barras ------------------
    @GetMapping(value = "/medicamento/{id}/barcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBarcode(@PathVariable Long id) {

        Medicamento m = medicamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        String texto = m.getCodigoBarras().getValor();

        return ResponseEntity.ok(barcodeService.generarCode128(texto));
    }
}
