package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.EmisorDifusion;
import utilidades.conexion.InfoInternet;

/**
 * Javier 2019.
 */

public class ReceptorCoordenadasDifusion extends ReceptorCoordenadas {

    private Context contexto;
    public ReceptorCoordenadasDifusion(String clave, Context contexto) {
        super(clave);
        this.contexto = contexto;
    }

    @Override
    protected void procesarCoordenada(Coordenada coordenada) {

    }
}
