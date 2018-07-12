package pc.javier.seguime.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import pc.javier.seguime.interfaz.Aplicacion;


/**
 * Created by Usuario Javier on 25 jun 2018.
 */

public class ReceptorUsuarioActivo extends BroadcastReceiver {

    @Override
    public void onReceive(Context contexto, Intent intent) {


        boolean aplicacionActiva = Aplicacion.preferenciaBooleano("activa");
        if (Aplicacion.preferenciaBooleano("pantalladesbloqueada"))
            if (aplicacionActiva) {


            }

    }
}
