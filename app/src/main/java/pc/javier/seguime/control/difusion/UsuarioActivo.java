package pc.javier.seguime.control.difusion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.Control;
import pc.javier.seguime.control.ServicioAplicacion;
import utilidades.Servicios;

/**
 * Javier 2019.
 * Modificado 2019
 */

public class UsuarioActivo extends BroadcastReceiver {
    @Override
    public void onReceive(Context contexto, Intent intent) {

        Preferencias preferencias = new Preferencias(contexto);

        if (preferencias.getServicioActivo() == false)
            return;

        if (preferencias.getActivarConPantalla() == false)
            return;

        Class servicio = ServicioAplicacion.class;

        Servicios servicios = new Servicios(contexto);
        Intent intentS = new Intent(contexto, servicio);

        if (servicios.activo(servicio))
            contexto.stopService(intentS);

        contexto.startActivity(intentS);

    }
}