package pc.javier.seguime.control.difusion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.ServicioAplicacion;

/**
 * Javier 2018.
 * Modificado 2019
 */

public class InicioConSistema extends BroadcastReceiver {
    @Override
    public void onReceive(Context contexto, Intent intent) {
        Preferencias preferencias = new Preferencias(contexto);
        if (preferencias.getServicioActivo())
            if (preferencias.getIniciarConSistema()) {
                Intent servicio = new Intent(contexto, ServicioAplicacion.class);
                contexto.startService(servicio);
            }
    }
}