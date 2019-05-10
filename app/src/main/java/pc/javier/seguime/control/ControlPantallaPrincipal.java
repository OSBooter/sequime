package pc.javier.seguime.control;

import android.view.MenuItem;

import pc.javier.seguime.ActividadAyuda;
import pc.javier.seguime.ActividadClave;
import pc.javier.seguime.ActividadDesbloqueo;
import pc.javier.seguime.ActividadOpciones;
import pc.javier.seguime.ActividadPresentacion;
import pc.javier.seguime.ActividadRegistros;
import pc.javier.seguime.ActividadRegresiva;
import pc.javier.seguime.ActividadSesion;
import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Constante;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.vista.PantallaPrincipal;
import utilidades.Dialogo;
import utilidades.EnlaceExterno;
import utilidades.MensajeRegistro;

/**
 * Javier 2019.
 */

public class ControlPantallaPrincipal  extends Control {

    private PantallaPrincipal pantalla;

    public ControlPantallaPrincipal(PantallaPrincipal pantalla, Preferencias preferencias) {
        super(pantalla, preferencias);
        this.pantalla = pantalla;
    }





    // menú


    public void click (MenuItem opcion) {

        boolean bloueado = getPreferencias().obtenerBoolean(Preferencias.TipoPreferencia.bloqueado);
        String usuario = getPreferencias().obtenerString(Preferencias.TipoPreferencia.usuario);
        boolean versionregistrada = getPreferencias().obtenerBoolean(Preferencias.TipoPreferencia.versionRegistrada);


        switch (opcion.getItemId()) {

            case R.id.menu_sesion:
                if (preferencias.getSesionIniciada())
                    cerrarSesion();
                else
                    iniciarActividad(ActividadSesion.class);
                break;

            case R.id.menu_ayuda:
                iniciarActividad(ActividadAyuda.class);
                break;

            case R.id.menu_opciones:
                iniciarActividad(ActividadOpciones.class);
                break;

            case R.id.menu_registros:
                //if (consultarAplicacionBloqueada() == false)
                iniciarActividad(ActividadRegistros.class);
                break;

            case R.id.menu_cuentaregresiva:
                //if (consultarAplicacionBloqueada() == false)
                iniciarActividad(ActividadRegresiva.class);
                break;

            case R.id.menu_registraraplicacion:
                iniciarActividad(ActividadClave.class);
                break;

            case R.id.menu_donar:
                donar();
                break;

            case R.id.menu_cerrarsesion:
                cerrarSesion();
                break;

            case R.id.menu_bloquear:
                bloquear();
                break;
        }
    }












    // botón principal


    // pulsa el botón de iniciar / detener la aplicación
    public void botonIniciar () {
        if (servicioActivo()) {
            detenerServicio();
        } else {
            iniciarServicio();
        }

        mostrarBoton(servicioActivo());
        mostrarIconos();
    }


    // dibuja el boton principal
    private void mostrarBoton () {;
        mostrarBoton(servicioActivo());
    }
    private void mostrarBoton (boolean estadoBoton) {;
        pantalla.dibujarBoton(estadoBoton);
    }









    public void regresar () {
        pantalla = new PantallaPrincipal(activity);

        if (preferencias.getPresentacionInicial() == false) {
            preferencias.setPresentacionInicial(true);
            iniciarActividad(ActividadPresentacion.class);
            return;
        }


        String notificacion = preferencias.getNotificacion();
        if(notificacion != ""){
            pantalla.snack(notificacion);
            preferencias.borrar(Preferencias.TipoPreferencia.notificacion);
        }

        String mensaje = preferencias.getMensaje();
        if(mensaje != "")
            pantalla.mostrarMensajePrincipal(mensaje);





        if (preferencias.getVersionRegistrada() == true)
            pantalla.versionRegistrada();


        // ver-
        mostrarBoton();
        mostrarIconos();
        //personalizarMenu();

    }





    public void cerrarMensaje () {
        pantalla.borrarMensajePrincipal();
        preferencias.borrar(Preferencias.TipoPreferencia.mensaje);
    }


    // -------------------------------------------------------------------------- ÍCONOS


    private void mostrarIconos () {
        pantalla.iconoTemporizador(alarmaIniciada());
        pantalla.iconoTemporizadorServidor(alarmaServidor());
        pantalla.iconoSeguime( aplicacionActiva());
        pantalla.iconoRastreo(rastreoActivo());
    }














    // --------------------------------------------------------------------------





    private boolean aplicacionBloqueada () {
        return preferencias.obtenerBoolean(Preferencias.TipoPreferencia.bloqueado);
    }

    private boolean consultarAplicacionBloqueada () {
        boolean respuesta = aplicacionBloqueada();
        if (respuesta == true)
            pantalla.snack(R.string.txt_bloqueado);
        return respuesta;
    }


    private boolean aplicacionActiva () {
        return servicioActivo();
    }

    // verifica si el servicio está activo
    public boolean servicioActivo () {
        return servicioActivo(ServicioAplicacion.class);
    }


    private boolean rastreoActivo () {
        return preferencias.obtenerBoolean(Preferencias.TipoPreferencia.rastreo);
    }




    private boolean alarmaServidor() {
        return preferencias.obtenerBoolean(Preferencias.TipoPreferencia.alarmaServidor);
    }


    private boolean alarmaIniciada () {
        return  preferencias.getAlarma() != 0;
    }





    private void cerrarSesion () {
        if (consultarAplicacionBloqueada()) {
            pantalla.snack(R.string.txt_bloqueado);
            return;
        }
        if (alarmaIniciada()) {
            pantalla.snack(R.string.txt_bloqueado_alarma);
            return;
        }
        Dialogo dialogo = new Dialogo(activity) {
            @Override
            public void respuestaPositiva () { cerrarSesionDefinitivamente (); }
        };
        dialogo.setTitulo (R.string.cerrar_sesion);
        dialogo.setMensaje(R.string.cerrarsesion_mensajeadvertencia);
        dialogo.mostrar();

    }

    private void cerrarSesionDefinitivamente () {
        MensajeRegistro.msj("CERRANDO SESION ***");
        detenerServicio();
        preferencias.eliminarTodo();
        new BD(activity).eliminarTodoYCerrar();
        cerrarAplicacion();
    }



    private void bloquear () {
        if (preferencias.getBloqueado())
            iniciarActividad(ActividadDesbloqueo.class);
        else {
            preferencias.setBloqueado(true);
            cerrarAplicacion();
        }


    }


    private void donar (){
        (new EnlaceExterno(activity)).abrirEnlace(Constante.urlPaypal);
    }








    private void detenerServicio () {
        if (preferencias.getAlarma() != 0) {
            pantalla.snack(R.string.txt_bloqueado_alarma);
            return;
        }

        if (preferencias.getBloqueado()) {
            pantalla.snack(R.string.txt_bloqueado);
            return;
        }

        detenerServicio(ServicioAplicacion.class);
        preferencias.setServicioActivo(false);


    }

    public void iniciarServicio () {
        iniciarServicio(ServicioAplicacion.class);
        preferencias.setServicioActivo(true);
    }


}
