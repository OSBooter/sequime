package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Servidor;
import pc.javier.seguime.control.EnlaceEventos;

/**
 * Javier 2019.
 * Recibe coordenadas y las envia al Servidor
 */

public class ReceptorCoordenadasInternet extends ReceptorCoordenadas {


    private Context contexto;
    public ReceptorCoordenadasInternet(String clave, Context contexto) {
        super(clave);

        this.contexto = contexto;

    }


    @Override
    protected void procesarCoordenada(Coordenada coordenada) {
        Servidor servidor = new Servidor( contexto);


        // ver- / ésto debería estar en EnlaceEventos, y obtener el servidor por parametro
        EnlaceEventos enlaceEventos = new EnlaceEventos(contexto);
        servidor.setEvento(enlaceEventos.obtenerEventoConexionServidor());

        servidor.agregarCoordenada(coordenada);

        servidor.enviar();
    }




}
