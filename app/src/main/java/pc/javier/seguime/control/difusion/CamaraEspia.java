package pc.javier.seguime.control.difusion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.adaptador.Servidor;
import pc.javier.seguime.control.EnlaceEventos;
import utilidades.basico.MensajeRegistro;

/**
 * Created by Javier on 14/07/2019.
 */

public class CamaraEspia extends BroadcastReceiver {

    @Override
    public void onReceive(Context contexto, Intent intent) {

        Preferencias preferencias = new Preferencias(contexto);

        preferencias.setMensaje("Fotografía recibida - Requiere versión donación");

    }
}
