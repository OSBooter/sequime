package pc.javier.seguime.control;

import pc.javier.seguime.adaptador.Constante;
import utilidades.conexion.ConexionHTTP;
import utilidades.eventos.ReceptorDeEventos;


/**
 * Javier 2019.
 */














 /*

        NO FUNCIONAL
         ES CONCEPTO

  */
public class ComprobarVersion {



    private ConexionHTTP conexionHTTP;

    public ComprobarVersion () {

        String parametro = "version="+Constante.version;


        ReceptorVersion receptorVersion = new ReceptorVersion();
        receptorVersion.suscribir();

        conexionHTTP = new ConexionHTTP(Constante.urlSitio, parametro);

    }



    private class ReceptorVersion extends ReceptorDeEventos {

        public ReceptorVersion () {
            objetivo = 101010;
        }


        @Override
        public void procesar(String dato) {
            desuscribir();

            if (dato.equals(Constante.version))
                return;

            //Preferencias preferencias = new Preferencias(Aplicacion.actividadPrincipal);
            //preferencias.setNotificacion("¡Hay una nueva versión!");
        }
    }
}
