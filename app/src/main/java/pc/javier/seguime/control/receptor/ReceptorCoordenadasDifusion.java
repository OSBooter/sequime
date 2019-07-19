package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.EmisorDifusion;
import pc.javier.seguime.adaptador.Preferencias;
import utilidades.conexion.InfoInternet;

/**
 * Javier 2019.
 * Cuando recibe una coordenada emite una señal para activar aplicaciones (Wifi-Automagico)
 */

public class ReceptorCoordenadasDifusion extends ReceptorCoordenadas {

    private Context contexto;
    public ReceptorCoordenadasDifusion(String clave, Context contexto) {
        super(clave);
        this.contexto = contexto;
    }

    @Override
    protected void procesarCoordenada(Coordenada coordenada) {

        Preferencias preferencias = new Preferencias(contexto);
        if (!preferencias.getRastreo())
            return;

        InfoInternet infoInternet = new InfoInternet(contexto);

        EmisorDifusion emisorDifusion = new EmisorDifusion(contexto);
        // si no está conectado, emite
        if (!infoInternet.conectado())
            emisorDifusion.emitirWifi();


        emisorDifusion.emitirCamara();

    }
}
