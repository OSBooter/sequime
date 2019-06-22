package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import utilidades.basico.Evento;
import utilidades.localizacion.Localizador;





public class ServicioLocalizador extends Service {
    public ServicioLocalizador() {
    }

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
    private Evento evento;



    private void iniciarServicio () {
        // prepara los receptores
        EnlaceEventos enlaceEventos = new EnlaceEventos(this);

        // herramienta emisora de eventos
        evento = enlaceEventos.obtenerEventoLocalizacion();

        // localizador gps
        localizador = new Localizador(this);
        // agrega al evento del localizador el receptor de coordenadas
        localizador.setEvento(evento);
        localizador.activar();
    }


    private void detenerServicio () {
        if (localizador != null)
            localizador.desactivar();
        if (evento != null)
            evento.quitarHandlers();
    }







}
