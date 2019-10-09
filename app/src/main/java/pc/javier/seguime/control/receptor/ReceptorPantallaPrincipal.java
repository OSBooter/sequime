package pc.javier.seguime.control.receptor;

import android.app.Activity;

import pc.javier.seguime.vista.PantallaPrincipal;
import utilidades.conexion.ConexionHTTP;
import utilidades.conexion.ReceptorConexionHTTP;

/**
 * Created by Javier on 22/9/2019.
 */

public class ReceptorPantallaPrincipal extends ReceptorConexionHTTP {

    public ReceptorPantallaPrincipal(Activity activity) {
        super(activity);
        if (activity != null)
            pantallaPrincipal = new PantallaPrincipal(activity);
    }

    PantallaPrincipal pantallaPrincipal;

    @Override
    public void estado(ConexionHTTP.Estado estado) {

        if (activity == null)
            return;

        switch (estado) {
            case Conectando:
                pantallaPrincipal.iconoInternet(true);
                break;

            case Finalizado:
            case Error:
                pantallaPrincipal.iconoInternet(false);
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
                if (sesionIniciada == false)
                    pantallaPrincipal.finalizarActividad();
                break;

            case "notificacion":
                pantallaPrincipal.snack(parametro);
                break;

            case "mensajeestado":
                pantallaPrincipal.snack(parametro);
                break;

            case "mensaje":
                pantallaPrincipal.mostrarMensajePrincipal(parametro);
                break;

            case "bloqueo":
                boolean bloqueo = boleano(parametro);
                pantallaPrincipal.iconoBloqueado(bloqueo);
                break;

            case "rastreo":
                boolean rastreo = boleano(parametro);
                pantallaPrincipal.iconoRastreo(rastreo);
                break;

            case "alarmaservidor":
                boolean alarma = boleano(parametro);
                pantallaPrincipal.iconoTemporizadorServidor(alarma);
                break;

            case "sms":
                pantallaPrincipal.mostrarMensajePrincipal("Modificación de SMS");
                break;

            case "telegram":
                pantallaPrincipal.mostrarMensajePrincipal("ID Telegram Modificado");
                break;

        }

    }
}
