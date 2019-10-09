package pc.javier.seguime.control.receptor;

import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.vista.PantallaSesion;
import utilidades.conexion.ConexionHTTP;
import utilidades.conexion.ReceptorConexionHTTP;

/**
 * Created by Javier on 21/9/2019.
 */

public class ReceptorPantallaSesion extends ReceptorConexionHTTP {

    PantallaSesion pantallaSesion;

    public ReceptorPantallaSesion(Activity activity) {
        super(activity);
        if (activity != null)
            pantallaSesion = new PantallaSesion(activity);
    }


    @Override
    public void estado(ConexionHTTP.Estado estado) {

        if (activity == null)
            return;


        switch (estado) {
            case Conectando:
                pantallaSesion.setEstado(R.string.conectando);
                pantallaSesion.habiltarBotonIniciar(false);
                break;

            case Finalizado:
                pantallaSesion.setEstado(R.string.conexionfinalizada);
                pantallaSesion.habiltarBotonIniciar(true);
                pantallaSesion.conexionActiva = false;
                break;

            case Error:
                pantallaSesion.setEstado(R.string.errorconexion);
                pantallaSesion.habiltarBotonIniciar(true);
                pantallaSesion.conexionActiva = false;
                break;

        }


    }

    @Override
    public void ejecutar(String comando, String parametro) {

        if (activity == null)
            return;

        switch (comando) {
            case "sesion":
                boolean sesionIniciada = boleano(parametro);
                if (sesionIniciada == true)
                    pantallaSesion.finalizarActividad();

                break;

            case "notificacion":
                pantallaSesion.snack(parametro);
                break;

            case "mensajeestado":
                pantallaSesion.snack(parametro);
                break;

        }

    }
}