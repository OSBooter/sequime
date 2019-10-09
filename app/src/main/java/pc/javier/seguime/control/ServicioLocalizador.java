package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasBD;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasDifusion;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasInternet;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasSMS;
import utilidades.localizacion.Localizador;





public class ServicioLocalizador extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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








    // ------------------------------------------------------------------------------

    private Localizador localizador;



    private void iniciarServicio () {

        evSuscribir();

        // localizador gps
        if (localizador == null)
            localizador = new Localizador(this);
        localizador.activar();
    }


    private void detenerServicio () {
        if (localizador != null)
            localizador.desactivar();

        evDesuscribir();
    }







    // --- Eventos ----------

    Preferencias preferencias;

    ReceptorCoordenadasBD receptorCoordenadasBD;
    ReceptorCoordenadasInternet receptorCoordenadasInternet ;
    ReceptorCoordenadasSMS receptorCoordenadasSMS ;
    ReceptorCoordenadasDifusion receptorCoordenadasDifusion ;

    private void evSuscribir () {
        preferencias = new Preferencias(this);

        receptorCoordenadasBD = new ReceptorCoordenadasBD(this);
        receptorCoordenadasBD.suscribir();

        receptorCoordenadasInternet = new ReceptorCoordenadasInternet(this);
        if (preferencias.getSesionIniciada())
            receptorCoordenadasInternet.suscribir();


        receptorCoordenadasSMS = new ReceptorCoordenadasSMS(this);
        if (preferencias.getRastreo() == true)
            if (preferencias.getNumeroSms().isEmpty() == false)
                receptorCoordenadasSMS.suscribir();


        receptorCoordenadasDifusion = new ReceptorCoordenadasDifusion(this);
        if (preferencias.getConectarRedesAbiertas() == true)
            receptorCoordenadasDifusion.suscribir();

    }

    private void evDesuscribir () {
        receptorCoordenadasBD.desuscribir();
        receptorCoordenadasInternet.desuscribir();
        receptorCoordenadasSMS.desuscribir();
        receptorCoordenadasDifusion.desuscribir();
    }



}
