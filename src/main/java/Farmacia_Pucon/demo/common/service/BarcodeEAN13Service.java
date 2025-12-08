package Farmacia_Pucon.demo.common.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.common.BitMatrix;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class BarcodeEAN13Service {

    public byte[] generarEAN13(String codigo) {

        if (codigo == null || codigo.length() != 13)
            throw new IllegalArgumentException("El código EAN-13 debe tener exactamente 13 dígitos.");

        try {
            int width = 300;
            int height = 140;

            EAN13Writer writer = new EAN13Writer();
            BitMatrix bitMatrix = writer.encode(codigo, BarcodeFormat.EAN_13, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando código EAN-13", e);
        }
    }
}
