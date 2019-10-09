package pc.javier.seguime.control;

import android.content.Context;

import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.adaptador.Servidor;
import utilidades.SMS;

/**
 * Javier 2019.
 *  Mensajes de alerta
 */

public class Alerta {
    private BD baseDeDatos;
    private Preferencias preferencias;
    private SMS sms;
    private Coordenada coordenada;
    private String mensaje;
    private Context contexto;


    public Alerta (Context contexto) {
        this.contexto = contexto;
        baseDeDatos = new BD(contexto);
        preferencias = new Preferencias(contexto);
    }



    public void enviarMensajeAlerta () {
        coordenada = baseDeDatos.coordenadaObtenerUltima();
        if (coordenada != null)
            agregarMensaje(coordenada.obtenerEnlaceOSM());
        agregarMensaje( preferencias.getAlarmaMensaje () );
        enviar();
    }


    private void enviar () {
        enviarSms();
        //enviarTelegram ();
        enviarServidor ();
    }



    private void enviarSms () {
        String numeroSms = preferencias.getNumeroSms();

        // si no hay número sms, sale
        if (numeroSms.equals(""))
            return;

        sms = new SMS(numeroSms, mensaje);
        sms.enviar();
        // baseDeDatos.coordenadaMarcarSMS(coordenada.getId()); ver-
    }


    private void enviarServidor() {
        Servidor servidor = new Servidor(contexto);
        servidor.agregarParametro(Servidor.Parametro.texto, mensaje);
        servidor.enviar();
    }




    private void agregarMensaje (String mensaje) {
        this.mensaje = this.mensaje + " " + mensaje;
    }
    private void borrarMensaje () {
        this.mensaje = "";
    }



}
