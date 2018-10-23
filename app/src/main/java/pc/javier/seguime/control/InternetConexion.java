package pc.javier.seguime.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Javier on 14 feb 2018.
 * Conexion a Internet, envia y recibe datos
 */

public class InternetConexion implements Runnable {
    @Override
    public void run() {
        conectar();
    }


    private String url = "http://seguime.000webhostapp.com/servicio.php";
    private String parametros = "latitud=0&longitud=0&fecha=2016/1/1 13:15";
    private Handler handler;
    private boolean ssl = false;

    public InternetConexion (String url, String parametros, Handler handler) {
        this.parametros = parametros;
        this.url = url;
        this.handler = handler;
        //this.ssl = ssl;
    }




    public void conectar() {
        try {

            mensajeLog ( "conectando a " + url);
            mensaje("conexion", "conectando");

            URL direccion = new URL(url);
            HttpURLConnection conexion = (HttpURLConnection) direccion.openConnection();
            //HttpsURLConnection conexion = (HttpsURLConnection) direccion.openConnection();

            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("USER-AGENT", "seguime");
            conexion.setConnectTimeout(1000*20);





            mensaje("conexion", "enviando");
            mensajeLog ("enviando datos " + parametros);
            // enviar datos
            conexion.setDoOutput(true);
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            salida.writeBytes(parametros);
            salida.flush();
            salida.close();

            mensaje("conexion", "recibiendo");
            mensajeLog ( "preparandose para recibir datos");
            // recibir datos
            BufferedReader br = new BufferedReader( new InputStreamReader(conexion.getInputStream()));

            String respuesta = "";

            mensaje("conexion", "finalizado");

            while ((respuesta = br.readLine()) != null)
                mensaje("dato", respuesta);

            br.close();


            mensajeLog ( "datos recibidos " + respuesta);






        } catch (Exception e) {
            mensaje("conexion", "error");
            mensajeLog ( "Error " + e.toString());
        }

    }




    // comunica a la aplicacion eventos ocurridos
    private void mensaje (String clave, String texto) {

        if (handler == null)
            return;

        mensajeLog("(handler) " + clave +"-"+ texto);

        Message mensaje = new Message();
        Bundle dato = new Bundle();
        dato.putString(clave, texto);
        mensaje.setData(dato);

        handler.sendMessage(mensaje);

    }



    private void mensajeLog (String texto) {
        Log.d("Internet Conexion:", texto);
    }

}


