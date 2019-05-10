package pc.javier.seguime.control.receptor;

import android.app.Activity;

import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Pantalla;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.vista.PantallaPrincipal;
import utilidades.MensajeRegistro;
import utilidades.ReceptorEventos;

/**
 * Javier 2019.
 *  Recibe comandos por parte del Servidor
 */

public class ReceptorComandosInternet extends ReceptorEventos{
    private Preferencias preferencias;
    private Activity activity;
    private PantallaPrincipal pantallaPrincipal;

    public ReceptorComandosInternet(Activity activity, String clave) {
        super(clave);
        this.activity = activity;
        preferencias = new Preferencias(activity);
        pantallaPrincipal = new PantallaPrincipal(activity);
    }

    @Override
    public void procesar(String dato) {
        dato = dato.trim();
        MensajeRegistro.msj("receptor COMANDOS: " +dato);


        // al dato lo "subdivide" en comando y parametro
        int indice = dato.indexOf(" ");
        String comando = dato;
        String parametro = "";

        if (indice > 0) {
            comando = dato.substring(0, indice).trim();
            parametro = dato.substring(indice + 1).trim();
        }

        ejecutar (comando, parametro);
    }





    private void ejecutar (String comando, String parametro) {

        mensajeLog("COMANDO: "+comando + " | paramentro: " + parametro);

        switch (comando) {
            case "sesion":
                sesion(parametro);
                break;
            case "notificacion":
                notificacion (parametro);
                break;
            case "mensaje":
                mensaje (parametro);
                break;

            case "mensajeestado":
                mensajeEstado (parametro);
                break;
        }



        // comandos que requieren sesión iniciada
        if (preferencias.getSesionIniciada() == false)
            return;

        switch (comando) {
            case "marcar":
                marcar(parametro);
                break;
            case "bloqueo":
                bloqueo(parametro);
                break;
            case "rastreo":
                rastreo (parametro);
                break;
            case "eliminartodo":
                eliminarCoordenadas();
                break;

            case "alarmaservidor":
                alarmaServidor (parametro);
                break;


        }
    }




    // ------------------------------------------- COMANDOS ----

    private void sesion (String estado) {
        boolean sesionIniciada = boleano(estado);
        preferencias.setSesionIniciada(sesionIniciada);
        if (sesionIniciada == false)
            return;
        Pantalla pantalla = new Pantalla(activity);
        pantalla.finalizarActividad();
    }

    private void mensaje (String mensaje) {
        preferencias.setMensaje(mensaje);
        pantallaPrincipal.mostrarMensajePrincipal(mensaje);
    }

    private void notificacion (String mensaje) {
        preferencias.setNotificacion(mensaje);
    }

    private void mensajeEstado (String mensaje) {
        Pantalla pantalla = new Pantalla(activity);
            pantalla.snack(mensaje);
        mensajeLog ("recibido mensaje de estado: " +mensaje);
    }


    // -------

    private void marcar (String codigo) {
        BD bd = new BD (activity);
        bd.coordenadaMarcar(codigo);
        bd.cerrar();
    }

    private void eliminarCoordenadas () {
        BD bd = new BD (activity);
        bd.eliminarTodoYCerrar();
    }

    private void bloqueo (String estado) {
        boolean bloqueo = boleano(estado);
        preferencias.setBloqueado(bloqueo);
        pantallaPrincipal.iconoBloqueado(bloqueo);
    }

    private void rastreo (String estado) {
        boolean rastreo = boleano(estado);
        preferencias.setRastreo(rastreo);
        pantallaPrincipal.iconoRastreo(rastreo);
    }

    private void alarmaServidor (String estado) {
        boolean alarma = boleano(estado);
        preferencias.setAlarmaServidor(alarma);
        pantallaPrincipal.iconoTemporizadorServidor(alarma);
    }




    // -----------------------------------------------------

    private int  entero (String numero) {
        try { return Integer.parseInt(numero); }
            catch (Exception e) { return 0; }
    }

    private boolean boleano (String valor) {
        try { return Boolean.parseBoolean(valor); }
        catch (Exception e) { return false; }
    }






    private void mensajeLog (String texto) { MensajeRegistro.msj("RECEPTOR COMANDOS", texto);}
}
