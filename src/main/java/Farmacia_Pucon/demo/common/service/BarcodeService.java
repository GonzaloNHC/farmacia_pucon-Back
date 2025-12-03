package Farmacia_Pucon.demo.common.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class BarcodeService {

    public byte[] generarCode128(String texto) {
        try {
            int width = 400;
            int height = 120;

            Code128Writer writer = new Code128Writer();
            BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.CODE_128, width, height);

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
            throw new RuntimeException("Error al generar código de barras", e);
        }
    }
}
