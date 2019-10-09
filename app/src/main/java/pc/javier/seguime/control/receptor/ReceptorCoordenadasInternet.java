package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Servidor;

/**
 * Javier 2019.
 * Recibe coordenadas y las envia al Servidor
 */

public class ReceptorCoordenadasInternet extends ReceptorCoordenadas {


    private Context contexto;
    public ReceptorCoordenadasInternet(Context contexto) {
        super();
        this.contexto = contexto;

    }


    @Override
    protected void procesarCoordenada(Coordenada coordenada) {
        Servidor servidor = new Servidor( contexto);
        servidor.agregarCoordenada(coordenada);
        servidor.enviar();
    }




}
