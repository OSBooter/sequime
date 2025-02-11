package pc.javier.seguime.control.receptor;

import android.content.Context;
import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Coordenada;

/**
    Javier 2019.
    Recibe coordenadas y las almacena en base de datos
 */

public class ReceptorCoordenadasBD extends ReceptorCoordenadas {


    Context contexto;
    public ReceptorCoordenadasBD(Context contexto) {
        super();
        this.contexto = contexto;
    }


    @Override
    protected void procesarCoordenada(Coordenada coordenada) {
        BD baseDeDatos = new BD(contexto);
        baseDeDatos.coordenadaInsertar(coordenada);
        baseDeDatos.cerrar();
    }



}
