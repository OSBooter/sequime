package pc.javier.seguime.interfaz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;


import pc.javier.seguime.ActividadRegistros;
import pc.javier.seguime.control.Sms;
import pc.javier.seguime.utilidades.ItemRegistro;

/**
 * Created by Javier on 14 feb 2018.
 * guarda coordenadas en la base de datos
 * las envia al servidor
 */

public class GestorCoordenadas {

    private Coordenada coordenada;
    private BD basededatos;
    private SharedPreferences preferencias;

    private String latitud;
    private String longitud;
    private String fecha;
    private String extra;
    private int id;
    private int recibido;

    private String usuario;
    private String clave;
    String servidor;
    private Handler handler;

    public GestorCoordenadas(BD basededatos, SharedPreferences preferencias) {
        this.preferencias = preferencias;
        this.basededatos = basededatos;
        usuario = preferencias.getString("usuario", "");
        clave = preferencias.getString("clave", "");
        servidor = preferencias.getString("servidor", "");
        handler = Aplicacion.handler;
    }


    public void setCoordenada (Coordenada coordenada){
        this.coordenada = new Coordenada();
        this.coordenada = coordenada;

        fecha = coordenada.getFecha();
        latitud = coordenada.getLatitud();
        longitud = coordenada.getLongitud();
        extra = coordenada.getExtra();
        id = coordenada.getId();
        recibido = coordenada.getRecibido();
    }


    public void insertarBdD () {
        basededatos.coordenadaInsertar(latitud, longitud, extra);

        // obtiene la ID de la base de datos
        coordenada = basededatos.coordenadaObtenerUltima();
        id = coordenada.getId();

    }


    public void enviarServidor () {


        String parametros = "";
        boolean rastreo = preferencias.getBoolean("rastreo", false);
        String telegram = preferencias.getString("telegram", "");



        parametros = "comando=posicion" + "&latitud=" + latitud + "&longitud=" + longitud + "&fecha=" + fecha + "&id=" + String.valueOf(id) + "&extra=" + extra;

        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;

        if (rastreo == true)
            if (telegram != "")
                parametros = "telegram=" + telegram + "&" + parametros;


        mensajeLog ( "Enviando ultima posicion - ID: " + id);
        mensajeLog ( "Enviando ultima posicion - Parametros: " + parametros);


        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();
    }




    public void enviarNada () {


        String parametros = "";

        parametros = "comando=ping" ;

        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;


        mensajeLog ( "Enviando conexion vacia: " + parametros);

        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();
    }




    public void enviarNotificaciones () {
        // los valores de rastreo y SMS los obtiene de esta manera
        // porque pueden ser cambiados desde el servidor
        // asi se tiene "la ultima version" de esta configuracion

        boolean rastreo = preferencias.getBoolean("rastreo", false);
        if (rastreo == false)
            return;
        String sms = preferencias.getString("sms", "");
        if (sms != "") {
            Sms mensaje = new Sms(sms, "(Seguime) nueva posicion geo:"+latitud+","+longitud+"?z=19");
            mensaje.enviar();
        }
    }



    private void mostrarEnRegistros () {
        ItemRegistro item = new ItemRegistro();

        item.latitud = coordenada.getLatitud();
        item.longitud = coordenada.getLongitud();
        item.fecha = coordenada.getFecha();
        item.extra = coordenada.getExtra();
        item.recibido = coordenada.getRecibido();
        item.id = coordenada.getId();

        //ActividadRegistros.agregar(item);


    }






    private void mensajeLog (String texto) {
        Log.d("Gestor", texto);
    }
}
