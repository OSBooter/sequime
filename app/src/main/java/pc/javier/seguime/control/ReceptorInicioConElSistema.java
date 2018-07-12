package pc.javier.seguime.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import pc.javier.seguime.interfaz.Aplicacion;


/**
 * Created by Usuario Javier on 25 jun 2018.
 */

public class ReceptorInicioConElSistema extends BroadcastReceiver {

    @Override
    public void onReceive(Context contexto, Intent intent) {

        // SharedPreferences preferencias = contexto.getSharedPreferences("preferencias",contexto.MODE_PRIVATE);
        Aplicacion.contexto = contexto;


        boolean aplicacionActiva = Aplicacion.preferenciaBooleano("activa");
        if (Aplicacion.preferenciaBooleano("iniciar"))
        if (aplicacionActiva) {
            Intent servicio = new Intent(contexto, ServicioTemporizador.class);
            contexto.startService(servicio);
        }

/*

        // LANZAR ACTIVIDAD
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
*/


    }


}
