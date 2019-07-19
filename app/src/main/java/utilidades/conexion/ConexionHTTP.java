package utilidades.conexion;


import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import utilidades.basico.Evento;

/**
 * Javier 2019.
 * Conexion a Internet, envia y recibe datos
 * 04 05 19
 */

public class ConexionHTTP implements Runnable {
    @Override
    public void run() {
        conectar();
    }

    private String method = "POST";
    private String userAgent = "Aplicacion Javier";
    private int esperaMaxima = 1000*20;

    private String url = "";
    private String parametros = "";
    private boolean ssl = false;

    private boolean error = false;
    private String mensajeEstado = "";

    private String autorizacion = "";


    private Evento evento = null;

    public static final String claveRespuesta = "respuesta";
    public static final String claveEstado = "conexion";




    public ConexionHTTP (String url, String parametros, Evento evento) {
        this.parametros = parametros;
        this.url = url;
        this.evento = evento;
    }

    public ConexionHTTP (String url, Evento evento) {
        this.url = url;
        this.evento = evento;
    }

    public ConexionHTTP (String url, String parametros) {
        this.parametros = parametros;
        this.url = url;
    }

    public ConexionHTTP (String url) {
        this.url = url;
    }

    public ConexionHTTP () { }





    public void conectar() {
        error = false;
        mensajeEstado ="";
        try {

            mensajeLog ( "conectando a " + url);
            emitirEstado(Estado.Conectando);

            URL direccion = new URL(url);


            HttpURLConnection conexion;
            if (ssl == false)
                conexion = (HttpURLConnection) direccion.openConnection();
            else
                conexion = (HttpsURLConnection) direccion.openConnection();




            conexion.setConnectTimeout(esperaMaxima);
            conexion.setRequestMethod(method);
            conexion.setRequestProperty("USER-AGENT", userAgent);
            if (!autorizacion.isEmpty())
                conexion.setRequestProperty("Authorization", autorizacion);



            // conexion.setRequestProperty("Content-Type", "\"multipart/form-data; boundary=&");
            // conexion.setRequestProperty("USER-AGENT", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0.2 Waterfox/49.0.2");

            //conexion.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");












            mensajeLog ("enviando datos " + parametros);
            emitirEstado(Estado.Enviando);
            // enviar datos
            conexion.setDoOutput(true);
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            salida.writeBytes(parametros);
            salida.flush();
            salida.close();


            mensajeLog ( "recibiendo datos");
            emitirEstado(Estado.Recibiendo);
            // recibir datos
            BufferedReader br = new BufferedReader( new InputStreamReader(conexion.getInputStream()));

            String respuesta = "";



            while ((respuesta = br.readLine()) != null)
                emitirRespuesta(respuesta);

            br.close();


            emitirEstado(Estado.Finalizado);


            mensajeLog ( "datos recibidos " + respuesta);






        } catch (Exception e) {
            emitirEstado(Estado.Error);
            mensajeLog ( "Error " + e.toString());
            mensajeEstado = e.getMessage();
            error = true;
        }



    }

    private void emitirEstado (Estado estado) {
           emitirEvento(claveEstado, estado.toString());
    }

    private void emitirRespuesta (String respuesta) {
            emitirEvento(claveRespuesta, respuesta);
    }

    private void emitirEvento (String clave, String dato) {
        if (evento != null)
            evento.emitir(clave, dato);
    }



    private void mensajeLog (String texto) {
        Log.d("(ConexionHTTP):", texto);
    }



    public void agregarParametro (String parametro, String valor) {
        if (!parametros.isEmpty())
            parametros = parametros + "&" ;
        parametros = parametros + parametro + "=" + valor;
    }

    public void setAutorizacion (String valor) {
        autorizacion = valor;
    }


    public String method() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getEsperaMaxima() {
        return esperaMaxima;
    }

    public void setEsperaMaxima(int esperaMaxima) {
        this.esperaMaxima = esperaMaxima;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String GetParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }


    public boolean getSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean getError() {
        return error;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }




    public  String codificarBase64 (String texto) {
        return new String(Base64.encode(texto.getBytes(),Base64.NO_WRAP ));
    }


    // se ejecuta a si mismo en un hilo
    public void ejecutarHilo() {
        Thread hilo;
        hilo = new Thread(this);
        hilo.start();
    }


    public enum Estado {
        Conectando,
        Error,
        Enviando,
        Recibiendo,
        Finalizado,
    }

}


