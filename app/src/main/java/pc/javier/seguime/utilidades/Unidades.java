package pc.javier.seguime.utilidades;
import static java.lang.Math.round;

/**
 * Created by Javier on 27 mar 2018.
 * Unidades de medidas
 */

public abstract class Unidades {
    public static String unidadVelocidad = "km/h";
    public static String velocidad (String valor) {

        int velocidad = 0;
        if (unidadVelocidad.equals("km/h")) {
            float x = 0;
            try {
                x = Float.parseFloat(valor);

            } catch (Exception e) {

            }
            velocidad = (int) round(x * 3.600);

        }
        return String.valueOf(velocidad) + " " +unidadVelocidad;
    }

}
