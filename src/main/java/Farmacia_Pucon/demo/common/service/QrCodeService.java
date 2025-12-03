package Farmacia_Pucon.demo.common.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeService {

    public byte[] generarQrPng(String texto) {
        try {
            int width = 250;
            int height = 250;

            QRCodeWriter qrWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream pngOutput = new ByteArrayOutputStream();
            ImageIO.write(image, "png", pngOutput);

            return pngOutput.toByteArray();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error al generar QR", e);
        }
    }
}
