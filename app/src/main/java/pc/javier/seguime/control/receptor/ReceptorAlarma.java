package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Aplicacion;
import pc.javier.seguime.vista.PantallaPrincipal;
import utilidades.eventos.ReceptorDeEventos;

/**
 * Javier 2019.
 *  Recibe la señal de un evento cuando se activa una alarma
 *  Solo actualiza la pantalla
 */

public class ReceptorAlarma extends ReceptorDeEventos {

    private Context contexto;
    private PantallaPrincipal pantallaPrincipal;
    public final static String CLAVE_EVENTO = "ALARMA";

    public ReceptorAlarma (Context contexto) {
        this.contexto = contexto;
        objetivo = Aplicacion.EV_ALARMA;
        clave = CLAVE_EVENTO;
    }

    @Override
    public void procesar (String dato) {
        activarALARMA();
    }


    private void activarALARMA () {


        if (pantallaPrincipal == null)
            return;

        pantallaPrincipal.iconoRastreo(true);
        pantallaPrincipal.iconoTemporizador(false);

        pantallaPrincipal.snack(R.string.principal_alarma);
    }



}
