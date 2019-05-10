package pc.javier.seguime.control;

import android.app.Activity;
import android.content.Context;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.receptor.ReceptorAlarma;
import pc.javier.seguime.control.receptor.ReceptorComandosInternet;
import pc.javier.seguime.control.receptor.ReceptorConexionInternet;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasBD;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasInternet;
import pc.javier.seguime.control.receptor.ReceptorCoordenadasSMS;
import utilidades.ConexionHTTP;
import utilidades.Evento;


/**
 * Javier 2019.
 *  une emisores de eventos con receptores de eventos
 */

public class EnlaceEventos {

    private Activity activity;
    private Context contexto;
    private Evento evento;

    public EnlaceEventos (Context contexto) {
        this.contexto = contexto;
        activity = Aplicacion.actividadPrincipal;
    }

    public EnlaceEventos (Activity activity) {
        this.contexto = activity.getApplicationContext();
        this.activity = activity;
    }

    //public EnlaceEventos () { activity = Aplicacion.actividadPrincipal; contexto = activity; }





    public Evento obtenerEventoLocalizacion() {
        String claveEventos = "localizacion";
        evento = new Evento(claveEventos);
        agregarReceptorBaseDeDatos(claveEventos);
        agregarReceptorServidor (claveEventos);
        agregarReceptorSMS(claveEventos);
        return evento;
    }





    public Evento obtenerEventoConexionServidor () {
        String claveEstado = ConexionHTTP.claveEstado;
        String claveRespuesta = ConexionHTTP.claveRespuesta;
        evento = new Evento();
        agregarReceptorConexion(claveEstado);
        agregarReceptorComandos(claveRespuesta);
        return evento;
    }







    private void agregarReceptorComandos (String claveEventos) {
        ReceptorComandosInternet receptorComandosInternet = new ReceptorComandosInternet(activity, claveEventos);
        evento.agregarReceptor(receptorComandosInternet);
    }


    private void agregarReceptorConexion (String claveEventos) {
        ReceptorConexionInternet receptorConexionInternet = new ReceptorConexionInternet(activity, claveEventos);
        evento.agregarReceptor(receptorConexionInternet);
    }


    // urlServidor que enviará las coordenadas
    private void agregarReceptorServidor (String claveEventos) {
        Preferencias preferencias = new Preferencias(contexto);
        if (preferencias.getSesionIniciada() == false)
            return;
        // objeto que recibe coordenadas desde eventos y las envia al urlServidor
        ReceptorCoordenadasInternet receptorCoordenadasInternet = new ReceptorCoordenadasInternet(claveEventos, contexto);
        evento.agregarReceptor(receptorCoordenadasInternet);
    }

    // base de datos donde almacenará las coordenadas
    private void agregarReceptorBaseDeDatos (String claveEventos) {
        ReceptorCoordenadasBD receptorCoordenadasBD = new ReceptorCoordenadasBD(claveEventos, contexto);
        evento.agregarReceptor(receptorCoordenadasBD);
    }

    // SMS - enviará coordenadas por sms
    private void agregarReceptorSMS (String claveEventos) {
        Preferencias preferencias = new Preferencias(contexto);
        if (preferencias.getRastreo() == false)
            return;
        if (preferencias.getNumeroSms().isEmpty())
            return;
        ReceptorCoordenadasSMS receptorCoordenadasSMS = new ReceptorCoordenadasSMS(claveEventos, contexto);
        evento.agregarReceptor(receptorCoordenadasSMS);
    }





    private Evento envioMensajes (String mensaje ) {
        // String mensaje = preferencias.getMensajeAlerta (); ver-
        // if (preferencias.getRastreo() == false) return;
        String claveEventos = "rastreo";
        evento = new Evento(claveEventos);
        agregarReceptorSMS(claveEventos);
        agregarReceptorTelegram(claveEventos);
        return evento;
    }







    public Evento obtenerEventoAlarma () {
        String claveEventos = "regresiva";
        evento = new Evento(claveEventos);

        ReceptorAlarma receptorAlarma = new ReceptorAlarma(claveEventos);
        evento.agregarReceptor(receptorAlarma);
        return evento;
    }



    private void agregarReceptorTelegram (String claveEventos) {

    }




}
