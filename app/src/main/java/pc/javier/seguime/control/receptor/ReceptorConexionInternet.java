package pc.javier.seguime.control.receptor;


import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.control.Aplicacion;
import pc.javier.seguime.vista.PantallaPrincipal;
import pc.javier.seguime.vista.PantallaSesion;
import utilidades.conexion.ConexionHTTP;
import utilidades.basico.ReceptorEventos;


/**
 * Javier 2019.
 *  recibe estados de la conexion a internet
 */

public class ReceptorConexionInternet extends ReceptorEventos {

    private PantallaSesion pantallaSesion;
    private PantallaPrincipal pantallaPrincipal;

    public ReceptorConexionInternet(Activity activity, String clave) {
        super(clave);
        pantallaSesion = new PantallaSesion(activity);
        pantallaPrincipal = new PantallaPrincipal(Aplicacion.actividadPrincipal);
    }


    @Override
    public void procesar(String dato) {

        if (dato.equals(ConexionHTTP.Estado.Conectando.name())) {
            pantallaSesion.setEstado (R.string.conectando);
            pantallaSesion.habiltarBotonIniciar(false);
            pantallaPrincipal.iconoInternet(true);
        }


        if (dato.equals(ConexionHTTP.Estado.Finalizado.name())) {
            pantallaSesion.setEstado (R.string.conexionfinalizada);
            pantallaSesion.habiltarBotonIniciar(true);
            pantallaSesion.conexionActiva = false;
            pantallaPrincipal.iconoInternet(false);
        }

        if (dato.equals(ConexionHTTP.Estado.Error.name())) {
            pantallaSesion.setEstado(R.string.errorconexion);
            pantallaSesion.habiltarBotonIniciar(true);
            pantallaPrincipal.iconoInternet(false);
            pantallaSesion.conexionActiva = false;
        }

        if (dato.equals(ConexionHTTP.Estado.Recibiendo.name())) {
            pantallaSesion.setEstado (R.string.recibiendo);
        }

        if (dato.equals(ConexionHTTP.Estado.Enviando.name())) {

        }

    }


}
