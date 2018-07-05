package pc.javier.seguime.interfaz;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Usuario NoTeBooK on 4 jul 2018.
 */

public class Temporizador {

    private  Date fechaHoraActivacion = new Date();

    public  void DefinirActivacion (Date FechaHoraActivacion) {
        fechaHoraActivacion = FechaHoraActivacion;
    }

    public  void DefinirActivacion (long segundosTotales) {
        fechaHoraActivacion.setTime(segundosTotales *1000);
    }

    public  void DefinirActivacion (int intervaloSegundos) {
        Date date = new Date();
        fechaHoraActivacion.setTime(date.getTime() + (intervaloSegundos *1000));
    }

    public  void DefinirActivacion (String fechaHora) {
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fechaHoraActivacion = formatoFechaHora.parse(fechaHora);
        } catch (Exception e) {}
    }

    public  boolean Activado () {
        return  (fechaHoraActivacion != null);
    }

    public  void Eliminar () {
        fechaHoraActivacion = null;
    }

    public  Date FechaHora () {
        return fechaHoraActivacion;
    }

    public  String FechaHoraString () {
        SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ffecha.format(fechaHoraActivacion);
    }

    public  Date FechaHoraUTC () {
        long milisegundos = fechaHoraActivacion.getTime() ;
        milisegundos = milisegundos - ZonaHorariaDiferenciaUTC();
        Date date = new Date();
        date.setTime(milisegundos);
        return date;
    }

    public  Date FechaHoraServidor () {
        long milisegundos = fechaHoraActivacion.getTime() ;
        milisegundos = milisegundos - ZonaHorariaDiferenciaServidor();
        Date date = new Date();
        date.setTime(milisegundos);
        return date;
    }


    public  String FechaHoraServidorString () {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(FechaHoraServidor());
    }

    public  String FechaHoraUTCString () {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(FechaHoraUTC());
    }



    private  Date SumaIntervalo (int intervaloSegundos ){
        return SumaIntervalo(new Date(), intervaloSegundos);
    }

    private  Date SumaIntervalo (Date fecha, int intervaloSegundos ){
        long ini = fecha.getTime();
        long fin = intervaloSegundos *1000;
        Long diff= (fin+ini);
        fecha.setTime(diff);
        return fecha;
    }



    // zona horaria
    // segundos de diferencia
    private  long zonaArgentina = -(3*60*60*1000);

    private   long ZonaHorariaDiferenciaServidor () {
        Log.d("TEMPORIZADOR ", "ZONA HORARIA ARG " + ((ZonaHorariaDiferenciaUTC() - zonaArgentina)/1000/60/60));
        return (ZonaHorariaDiferenciaUTC() - zonaArgentina);
    }

    private  long ZonaHorariaDiferenciaUTC () {
        Log.d("TEMPORIZADOR ", "ZONA HORARIA UTC " + (TimeZone.getDefault().getRawOffset()/1000/60/60));
        return (TimeZone.getDefault().getRawOffset());
    }
}
