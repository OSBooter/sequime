package pc.javier.seguime.utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by javier on 26/11/2016.
 * Permite obtener fechas y horas actuales
 */
public abstract class FechaHora {



    public static String info () {
        Date fecha = new Date();
        return  fecha.toString();
    }

    // hora actual
    public static String hora() {
        Date fecha = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm:ss");

        //String hora = String.valueOf(fecha.getHours()) + ":" + String.valueOf(fecha.getMinutes()) + ":" + String.valueOf(fecha.getSeconds()) ;
        return fhora.format(fecha);
    }

    // fecha actual formato normal
    public static String fechanormal () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("dd-MM-yyyy");
        return ffecha.format(fecha);
    }

    // fecha formato invertido
    public static String fecha () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd");
        return ffecha.format(fecha);
    }

    // fecha y hora invertida
    public static String complacto () {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ffecha.format(fecha);
    }





    // fecha hora invertida
    public static String hora (String fechacompleta) {
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatomini = new SimpleDateFormat("HH:mm:ss");

        try {
            return formatomini.format(formatofecha.parse(fechacompleta));

        } catch (Exception e) {}
        return "";
    }

    // fecha y hora formato normal
    public static String fechanormal (String fechacompleta) {
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatomini = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatomini.format(formatofecha.parse(fechacompleta));

        } catch (Exception e) {}
        return "";
    }


    // fecha y hora normal
    public static String fechaHora(String fechacompleta) {
        String respuesta = fechanormal(fechacompleta);
        respuesta = respuesta + " ";
        respuesta = respuesta + hora(fechacompleta);
        return respuesta;
    }

    // fecha normal
    public static String fecha(String fechacompleta) {
        String respuesta = fechanormal(fechacompleta);
        return respuesta;
    }



    public static String suma (String intervalo ){
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long in = fecha.getTime();
        long fin = Integer.parseInt(intervalo) *1000;
        Long diff= (fin+in);
        return ffecha.format(diff);
    }

    public static String suma (int intervalo ){
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long in = fecha.getTime();
        long fin = intervalo *1000;
        Long diff= (fin+in);
        return ffecha.format(diff);
    }

    public static int diferencia (Date fechaIn, Date fechaFinal ){
        long in = fechaIn.getTime();
        long fin = fechaFinal.getTime();
        Long diff= (fin-in)/1000;
        return diff.intValue();
    }


    // cantidad de segundos respecto de la hora actual
    public static long intervalo (String fechaFinal){
        try {
        Date fecha = new Date();
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long in = fecha.getTime();
        long fin = ffecha.parse(fechaFinal).getTime();
        Long diff= (fin-in)/1000;
        if (diff<=0)
            return 0;
        return diff.intValue();
        } catch (Exception e) {  }
        return 0;
    }

    public static int cantidadHoras (String fechacompleta) {
        SimpleDateFormat formato = new SimpleDateFormat("HH");
        int respuesta= 0;
        try {
            respuesta=Integer.parseInt(formato.format(formato.parse(fechacompleta)));
        } catch (Exception e) {}
        return respuesta;
    }

    public static int cantidadMinutos (String fechacompleta) {
        SimpleDateFormat formato = new SimpleDateFormat("mm");
        int respuesta= 0;
        try {
            respuesta=Integer.parseInt(formato.format(formato.parse(fechacompleta)));
        } catch (Exception e) {}
        return respuesta;
    }
    public static int cantidadDias (String fechacompleta) {
        SimpleDateFormat formato = new SimpleDateFormat("dd");
        int respuesta= 0;
        try {
            respuesta=Integer.parseInt(formato.format(formato.parse(fechacompleta)));
        } catch (Exception e) {}
        return respuesta;
    }


    public  static String HoraUTC () {
        TimeZone actual = TimeZone.getDefault();
        return suma (-actual.getRawOffset()/1000);
    }


    public  static String HoraServidor () {
        TimeZone actual = TimeZone.getDefault();
        return suma ((actual.getRawOffset()/1000) + (3*60*60));
    }

    // segundos de diferencia
    public  static int DiferenciaServidor () {
        // -(3*60*60) = zona horaria argentina
        return (DiferenciaUTC() + (3*60*60));
    }

    public  static int DiferenciaUTC () {
        TimeZone actual = TimeZone.getDefault();
        return (actual.getRawOffset()/1000);
    }
}
