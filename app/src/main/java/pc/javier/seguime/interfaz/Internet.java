package pc.javier.seguime.interfaz;

import android.os.Handler;

import pc.javier.seguime.control.InternetConexion;

/**
 * Created by Javier on 14 feb 2018.
 *
 * realiza conexiones a internet
 * lanza un hilo que envia datos a una direccion de internet
 * el servidor realiza una respuesta y se reciben comandos que seran procesados
 */

public class Internet {

    private String url;
    private String parametros;
    private Handler handler;
    private InternetConexion conexion;
    private Thread hilo;

    public Internet(String url, String parametros, Handler handler) {
        this.parametros = parametros;
        this.url = url;
        this.handler = handler;
    }


    public void conectar() {
        conexion = new InternetConexion(url,parametros,handler);
        hilo = new Thread(conexion);
        hilo.start();
    }



}
