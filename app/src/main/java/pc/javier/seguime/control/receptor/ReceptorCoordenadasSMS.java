package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Preferencias;
import utilidades.SMS;

/**
 * Javier 2019.
 */

public class ReceptorCoordenadasSMS extends ReceptorCoordenadas {

    private Context contexto;
    private Preferencias preferencias;

    public ReceptorCoordenadasSMS(String clave, Context contexto) {
        super(clave);
        this.contexto = contexto;
        preferencias = new Preferencias(contexto);
    }

    @Override
    protected void procesarCoordenada(Coordenada coordenada) {
        String numero = preferencias.getNumeroSms();
        if (numero.isEmpty())
            return;

        String mensaje = "Seguime "
                + coordenada.obtenerEnlaceOSM()
                + " "
                + coordenada.obtenerEnlaceGPS();
        SMS sms = new SMS (numero, mensaje);
        sms.enviar();
    }
}

