package utilidades.localizacion;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Javier 2019.
 */

public class Servicio extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        iniciarServicio();
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        detenerServicio();
        super.onDestroy();
    }



    Localizador localizador;
    private void iniciarServicio () {
        if (localizador == null)
            localizador = new Localizador(this);
        localizador.activar();
    }

    private void detenerServicio () {
        if (localizador != null)
            localizador.desactivar();
    }


}
