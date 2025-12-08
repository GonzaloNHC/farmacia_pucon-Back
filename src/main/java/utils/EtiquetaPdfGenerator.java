package Farmacia_Pucon.demo.inventario.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

public class EtiquetaPdfGenerator {

    public static byte[] generarEtiqueta(
            String nombre,
            String presentacion,
            String precio,
            String codigoTexto,
            BufferedImage codigoBarrasImg
    ) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document doc = new Document(new Rectangle(250, 150)); // tamaño etiqueta
            PdfWriter.getInstance(doc, baos);

            doc.open();

            Font title = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10);

            doc.add(new Paragraph(nombre, title));
            doc.add(new Paragraph(presentacion, normal));
            doc.add(new Paragraph("Precio: $" + precio, normal));
            doc.add(new Paragraph("Código: " + codigoTexto, normal));

            // Convertir la imagen a PNG para OpenPDF
            ByteArrayOutputStream pngBytes = new ByteArrayOutputStream();
            ImageIO.write(codigoBarrasImg, "png", pngBytes);

            Image barcodeImage = Image.getInstance(pngBytes.toByteArray());
            barcodeImage.scalePercent(70);

            doc.add(barcodeImage);

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar etiqueta PDF", e);
        }
    }
}
