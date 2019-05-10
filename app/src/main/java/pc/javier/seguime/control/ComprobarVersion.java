package pc.javier.seguime.control;

import pc.javier.seguime.adaptador.Constante;

import pc.javier.seguime.adaptador.Preferencias;
import utilidades.ConexionHTTP;
import utilidades.Evento;
import utilidades.ReceptorEventos;

/**
 * Javier 2019.
 */

public class ComprobarVersion {



    private ConexionHTTP conexionHTTP;

    public ComprobarVersion () {

        String parametro = "version="+Constante.version;

        Evento evento = new Evento("version");
        ReceptorVersion receptorVersion = new ReceptorVersion("version");
        evento.agregarReceptor(receptorVersion);

        conexionHTTP = new ConexionHTTP(Constante.urlSitio, parametro, evento);
    }



    private class ReceptorVersion extends ReceptorEventos {

        public ReceptorVersion (String clave) {
            super(clave);
        }
        @Override
        public void procesar(String dato) {
            if (dato.equals(Constante.version))
                return;

            Preferencias preferencias = new Preferencias(Aplicacion.contexto);
            preferencias.setNotificacion("¡Hay una nueva versión!");
        }
    }
}
