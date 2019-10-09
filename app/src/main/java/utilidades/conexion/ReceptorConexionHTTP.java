package utilidades.conexion;

import android.app.Activity;
import android.os.Bundle;

import utilidades.basico.MensajeRegistro;
import utilidades.eventos.ReceptorDeEventos;

/**
 * Created by Javier on 22/9/2019.
 * Típico formato para recibir Eventos de la conexión.
 */

public abstract class ReceptorConexionHTTP extends ReceptorDeEventos {

    protected Activity activity;

    public ReceptorConexionHTTP (Activity activity) {
        objetivo = ConexionHTTP.ID_EVENTO;
        this.activity = activity;
    }


    @Override
    public void procesar (Bundle bundle) {
        if (bundle.containsKey(ConexionHTTP.claveEstado))
            procesar(  bundle.getInt (ConexionHTTP.claveEstado));
        else
        if (bundle.containsKey(ConexionHTTP.claveRespuesta))
            procesar(bundle.getString(ConexionHTTP.claveRespuesta));
    }




    @Override
    public void procesar(String dato) {
        dato = dato.trim();
        MensajeRegistro.msj("receptor COMANDOS: " +dato);


        // al dato lo "subdivide" en comando y parametro
        int indice = dato.indexOf(" ");
        String comando = dato;
        String parametro = "";

        if (indice > 0) {
            comando = dato.substring(0, indice).trim();
            parametro = dato.substring(indice + 1).trim();
        }

        ejecutar (comando, parametro);
    }




    @Override
    public void procesar(int dato) {

        MensajeRegistro.msj("receptor CONEXION: " + dato);

        ConexionHTTP.Estado estado = ConexionHTTP.Estado.Error;

        if (dato == ConexionHTTP.Estado.Conectando.ordinal())
            estado = ConexionHTTP.Estado.Conectando;
        else

        if (dato == ConexionHTTP.Estado.Finalizado.ordinal())
            estado = ConexionHTTP.Estado.Finalizado;
        else

        if (dato == ConexionHTTP.Estado.Recibiendo.ordinal())
            estado = ConexionHTTP.Estado.Recibiendo;
        else

        if (dato == ConexionHTTP.Estado.Enviando.ordinal())
            estado = ConexionHTTP.Estado.Enviando;
        else

        if (dato == ConexionHTTP.Estado.Error.ordinal())
            estado = ConexionHTTP.Estado.Error;


        estado (estado);
    }

    public abstract void estado (ConexionHTTP.Estado estado);

    public abstract void ejecutar (String comando, String parametro) ;



    // -----------------------------------------------------

    protected int  entero (String numero) {
        try { return Integer.parseInt(numero); }
        catch (Exception e) { return 0; }
    }

    protected boolean boleano (String valor) {
        try { return Boolean.parseBoolean(valor); }
        catch (Exception e) { return false; }
    }



}
