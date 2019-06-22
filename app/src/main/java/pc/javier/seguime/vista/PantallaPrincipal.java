package pc.javier.seguime.vista;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.AnimationUtils;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;
import utilidades.vista.Efecto;

/**
 * Javier 2019.
 */

public class PantallaPrincipal extends Pantalla {
    public PantallaPrincipal(Activity activity) {
        super(activity);
    }



    public void mostrarMensajePrincipal (String mensaje) {
        setTextView(R.id.princ_mensaje, mensaje);
        setVisibilidad(R.id.princ_mensaje, visible());
    }

    public void borrarMensajePrincipal () {
        setTextView(R.id.princ_mensaje, "");
        setVisibilidad(R.id.princ_mensaje, invisible());
    }



    public void versionRegistrada () {
        setTextView(R.id.princ_registrada, R.string.versionRegistrada);
    }

    public void sesionIniciada () {

    }

    public void botonActivado (boolean estado) {

    }



    public void dibujarBoton (boolean activado) {

        int idTextViewEstado = R.id.princ_estado;
        int idBoton = R.id.princ_boton;


        int botonActivado = 0;
        int botonDesactivado = 0;

        // diferente forma y color para el botón
        if (estaModoPaisaje(getButton(idBoton))){
            botonActivado = R.drawable.botonprincipalcuadradoverde;
            botonDesactivado =R.drawable.botonprincipalcuadradogris;
        } else {
            botonActivado = R.drawable.anilloverde;
            botonDesactivado =R.drawable.botonprincipalredondogris;
        }


        if (activado) {
            setButtonText(idBoton, R.string.desactivar_aplicacion);
            getButton(idBoton).setBackgroundResource(botonActivado);
            getButton(idBoton).setTextColor(Color.BLACK);
            getTextView(idTextViewEstado).setTextColor(Color.BLACK);
            setTextView(idTextViewEstado, R.string.txt_servicio_activo);

            // pantalla.setBackgroundResource(R.drawable.camino);
        } else {
            setButtonText(idBoton, R.string.activar_aplicacion);
            getButton(idBoton).setTextColor(Color.DKGRAY);
            getTextView(idTextViewEstado).setTextColor(Color.RED);
            setTextView(idTextViewEstado, R.string.txt_servicio_inactivo);
            getButton(idBoton).setBackgroundResource(botonDesactivado);

        }



        getButton(idBoton).clearAnimation();
        getButton(idBoton).startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_atras_entrada));


    }










    public void iconoTemporizador (boolean visible) {
        mostrarIcono(R.id.princ_iconotemporizador, visible);
    }

    public void iconoTemporizadorServidor (boolean visible) {
        mostrarIcono(R.id.princ_iconotemporizadorservidor, visible);
    }
    public void iconoSeguime (boolean visible) {
        mostrarIcono(R.id.princ_iconoseguime, visible);
    }
    public void iconoRastreo (boolean visible) {
        mostrarIcono(R.id.princ_iconorastreo, visible);
    }

    public void iconoInternet (boolean visible) {
        mostrarIcono(R.id.princ_iconointernet, visible);
    }

    public void iconoBloqueado (boolean visible) {
        mostrarIcono(R.id.princ_iconobloqueo, visible);
    }

    private void mostrarIcono (int idIcono, boolean activo) {
        setVisibilidad(idIcono, activo);

        Efecto.AnimarIcono(getImageView(idIcono),  activo);
    }



    /* public void usuario (String usuario) {
        setTextView(R.id.menu_texto_usuario, usuario);
    }*/



}
