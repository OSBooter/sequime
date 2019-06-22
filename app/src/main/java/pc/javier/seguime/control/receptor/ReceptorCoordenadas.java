package pc.javier.seguime.control.receptor;

import java.util.ArrayList;


import pc.javier.seguime.adaptador.Coordenada;
import utilidades.basico.ReceptorEventos;

/**
 * Javier 2019.
 * Recibe coordenadas por parte de eventos del localizador
 */

public abstract class ReceptorCoordenadas extends ReceptorEventos {

    public ReceptorCoordenadas(String clave) {
        super(clave);
    }

    @Override
    public void procesar (ArrayList lista) {

        // recorre la lista
        for (Object coordenada: lista) {
            // convierte una coordenada de utilidades en una coordenada de adaptardor
            utilidades.localizacion.Coordenada coordenadaBasica = (utilidades.localizacion.Coordenada ) coordenada;
            Coordenada nuevaCoordenada = new Coordenada();
            nuevaCoordenada.setLatitud(coordenadaBasica.getLatitud());
            nuevaCoordenada.setLongitud(coordenadaBasica.getLongitud());
            nuevaCoordenada.setProveedor(coordenadaBasica.getProveedor());
            nuevaCoordenada.setFechaHora(coordenadaBasica.getFechaHora());
            nuevaCoordenada.setVelocidad(coordenadaBasica.getVelocidad());
            nuevaCoordenada.setCodigo(coordenadaBasica.getCodigo());
            procesarCoordenada(nuevaCoordenada);
        }

    }


    protected abstract void procesarCoordenada (Coordenada coordenada);





}
