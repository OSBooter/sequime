package pc.javier.seguime.control.difusion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import utilidades.conexion.InfoInternet;
import utilidades.basico.MensajeRegistro;

/**
 * Javier 2019.
 */

public class ConexionInternet extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        InfoInternet infoInternet = new InfoInternet(context);

        mensajeLog("cambios en la conexion");

        if (!infoInternet.conectado())
            return;

        mensajeLog ("tipo: " + infoInternet.getTipo()); // tipo de red (wifi)
        mensajeLog ("extra info: " + infoInternet.getInfo()); // nombre de red wifi


        /*
        En desarrollo

        Preferencias preferencias = new Preferencias(context);
        if (preferencias.getUsuario().isEmpty())
            return;

        preferencias.setConexionTipo (infoInternet.getTipo());
        preferencias.setConexionInfo (infoInternet.getInfo());



        EnlaceEventos enlaceEventos = new EnlaceEventos(context);
        Servidor servidor = new Servidor(context);
        servidor.setEvento(enlaceEventos.obtenerEventoConexionServidor());
        servidor.enviar();
        */


    }




    private void mensajeLog (String texto) {
        MensajeRegistro.msj (this, texto);
    }











}
