package Farmacia_Pucon.demo.inventario.utils;

public class RUTValidator {
 /**
     * Valida rut con o sin puntos y con guion y dígito verificador.
     * Ejemplos válidos: "12.345.678-5", "12345678-5", "12345678k"
     */
    public static boolean validarRut(String rutRaw) {
        if (rutRaw == null) return false;
        String rut = rutRaw.replace(".", "").replace(" ", "").toUpperCase();
        rut = rut.replace("-", "");
        if (rut.length() < 2) return false;
        String numero = rut.substring(0, rut.length() - 1);
        char dv = rut.charAt(rut.length() - 1);

        try {
            int num = Integer.parseInt(numero);
            char dvCalc = calcularDigitoVerificador(num);
            return dvCalc == dv;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static char calcularDigitoVerificador(int rut) {
        int m = 0, s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        if (s != 0) {
            return (char) (s + 47); // '0'..'9' or 'K' (75)
        } else {
            return 'K';
        }
    }
}