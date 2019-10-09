package pc.javier.seguime.control.receptor;

import android.os.Bundle;

import pc.javier.seguime.adaptador.Coordenada;
import utilidades.eventos.ReceptorDeEventos;
import utilidades.localizacion.Localizador;

/**
 * Javier 2019.
 * Recibe coordenadas por parte de eventos del localizador
 */

public abstract class ReceptorCoordenadas extends ReceptorDeEventos {


    public ReceptorCoordenadas () {
        objetivo = Localizador.EVENTO_ID;
    }



    @Override
    public void procesar (Bundle bundle) {

            Coordenada nuevaCoordenada = new Coordenada();

            nuevaCoordenada.setLatitud(bundle.getDouble("latitud"));
            nuevaCoordenada.setLongitud(bundle.getDouble("longitud"));
            nuevaCoordenada.setProveedor(bundle.getString("proveedor"));
            nuevaCoordenada.setVelocidad(bundle.getFloat("velocidad"));
            nuevaCoordenada.setCodigo(bundle.getString("codigo"));

            procesarCoordenada(nuevaCoordenada);


    }


    protected abstract void procesarCoordenada (Coordenada coordenada);





}
