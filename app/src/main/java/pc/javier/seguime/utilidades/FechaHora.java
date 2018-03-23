package pc.javier.seguime.utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javier on 26/11/2016.
 * Permite obtener fechas y horas actuales
 */
public abstract class FechaHora {



    public static String info () {
        Date fecha = new Date();

        return  fecha.toString();
    }

    public static String hora() {
        Date fecha = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm:ss");

        //String hora = String.valueOf(fecha.getHours()) + ":" + String.valueOf(fecha.getMinutes()) + ":" + String.valueOf(fecha.getSeconds()) ;
        return fhora.format(fecha);
    }

    public static String fechainvertida () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("dd-MM-yyyy");
        return ffecha.format(fecha);
    }

    public static String fecha () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd");
        return ffecha.format(fecha);
    }

    public static String complacto () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ffecha.format(fecha);
    }





    public static String hora (String fechacompleta) {
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatomini = new SimpleDateFormat("HH:mm:ss");

        try {
            return formatomini.format(formatofecha.parse(fechacompleta));

        } catch (Exception e) {}
        return "";
    }

    public static String fechainvertida (String fechacompleta) {
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatomini = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatomini.format(formatofecha.parse(fechacompleta));

        } catch (Exception e) {}
        return "";
    }


    public static String fechaHora(String fechacompleta) {
        String respuesta = fechainvertida(fechacompleta);
        respuesta = respuesta + " ";
        respuesta = respuesta + hora(fechacompleta);
        return respuesta;
    }

}
