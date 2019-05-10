package utilidades;

import java.text.DecimalFormat;

import javax.xml.transform.sax.SAXResult;

import static java.lang.Math.round;

/**
 *  Javier 2019.
 */

public class Velocidad {
    private float velocidad = 0;

    public Velocidad (float datoGps) {
        velocidad = datoGps;
    }



    public float kmh () {
        return (float) (velocidad * 3.6000);
    }

    public String kmhString () {
        String respuesta = formatoDecimal(kmh());
        respuesta = respuesta + " km/h";
        return respuesta;
    }


    private String formatoDecimal (float valor) {
        return  String.format("%.2f", valor).replace(",", ".");
    }

    public enum Unidad {
        gps,
        kmh,

    }

}
