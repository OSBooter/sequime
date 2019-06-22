package pc.javier.seguime.control.receptor;

import android.app.Activity;

import pc.javier.seguime.control.Alerta;
import pc.javier.seguime.control.Aplicacion;
import pc.javier.seguime.vista.PantallaPrincipal;
import utilidades.basico.MensajeRegistro;
import utilidades.basico.ReceptorEventos;

/**
 * Javier 2019.
 *  Recibe la señal de un evento cuando se activa una alarma
 *  delega el proceso de envios (coordenadas y mensajes) a la clase Alarma
 */

public class ReceptorAlarma extends ReceptorEventos {

    public ReceptorAlarma(String clave) {
        super(clave);
    }

    private Activity activity = Aplicacion.actividadPrincipal;

    @Override
    public void procesar (String dato) {
        activarALARMA();
    }


    private void activarALARMA () {
        MensajeRegistro.msj("alarma: controla iconos en pantalla");
        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(activity);
        pantallaPrincipal.iconoRastreo(true);
        pantallaPrincipal.iconoTemporizador(false);

        // pantallaPrincipal.snack(R.string.alarma_activada);

        enviarMensajes();
    }



    private void enviarMensajes () {
        Alerta alerta = new Alerta(activity);
        alerta.enviarMensajeAlerta();
    }
}
