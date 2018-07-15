package pc.javier.seguime.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.utilidades.Parametro;


/**
 * Created by Usuario Javier on 25 jun 2018.
 */

public class ReceptorUsuarioActivo extends BroadcastReceiver {

    @Override
    public void onReceive(Context contexto, Intent intent) {

        Log.d("USUARIO ACTIVO","se intentara reiiciar el servicio");
        if (Parametro.aplicacion == null)
            return;

        Log.d("USUARIO ACTIVO","hay aplicacion");

        if (!Parametro.aplicacion.servicioActivo())
            return;

        Log.d("USUARIO ACTIVO","hay servicio");



        if (!Aplicacion.preferenciaBooleano("activa"))
            return;

        Log.d("USUARIO ACTIVO","preferencia activada");


        if (Aplicacion.preferenciaBooleano("activarconpantalla"))
            Parametro.aplicacion.ReiniciarServicio();



    }
}
