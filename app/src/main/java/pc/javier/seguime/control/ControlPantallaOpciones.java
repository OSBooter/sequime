package pc.javier.seguime.control;

import android.content.Intent;
import android.view.MenuItem;

import pc.javier.seguime.ActividadAyudaOpciones;
import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Constante;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.vista.PantallaOpciones;
import utilidades.Contacto;
import utilidades.basico.EnlaceExterno;
import utilidades.basico.MensajeRegistro;

import static android.app.Activity.RESULT_OK;

/**
 * Javier 2019.
 */

public class ControlPantallaOpciones extends Control {

    private Intent intentContactos;
    private PantallaOpciones pantalla;
    private Contacto contacto;

    public ControlPantallaOpciones(PantallaOpciones pantalla, Preferencias preferencias) {
        super(pantalla, preferencias);

        this.pantalla = pantalla;

        cargarOpciones();
    }




    public void guardar () {
        guardarOpciones();
        getPantalla().finalizarActividad();
    }


    public void cancelar () {
        getPantalla().finalizarActividad();
    }





    private void cargarOpciones() {
        pantalla.setTelegram(preferencias.getIdTelegram());
        pantalla.setActividad(preferencias.getIntervaloActividad());
        pantalla.setInactividad(preferencias.getIntervaloInactividad());
        pantalla.setRastreo(preferencias.getRastreo());
        pantalla.setIniciarConSistema(preferencias.getIniciarConSistema());
        pantalla.setActivarConPantalla(preferencias.getActivarConPantalla());
        pantalla.setSms(preferencias.getNumeroSms());
        pantalla.setInternet(preferencias.getIntervaloInternet());
        pantalla.setEnviarInfoConexion(preferencias.getEnviarDatosDeConexion());
        pantalla.setPermitirConfigurarSMS(preferencias.getPermitirConfigurarSMS());
        pantalla.setConectarseRedesAbiertas(preferencias.getConectarRedesAbiertas());
        pantalla.setEnviarFotografias(preferencias.getEnviarFotografias());

        if (!Constante.versionConSMS) {
            pantalla.setHabilitado(R.id.opciones_sms, false);
            pantalla.snack(R.string.versionIncompleta_txt);
            pantalla.setFondo(R.id.opciones_sms, pantalla.cuadroGrisRedondeando());
        }

    }





    private void guardarOpciones () {
        verificarRegistro();

        preferencias.setIntervaloInternet(pantalla.getInternet());
        preferencias.setNumeroSms (pantalla.getSms());
        preferencias.setIdTelegram( pantalla.getTelegram());
        preferencias.setIntervaloActividad( pantalla.getActividad());
        preferencias.setIntervaloInactividad( pantalla.getInactividad());
        preferencias.setRastreo( pantalla.getRastreo());
        preferencias.setIniciarConSistema( pantalla.getIniciarConSistema());
        preferencias.setActivarConPantalla( pantalla.getActivarConPantalla());
        preferencias.setEnviarDatosDeConexion(pantalla.getEnviarInfoConexion());
        preferencias.setPermitirConfigurarSMS(pantalla.getPermitirConfigurarSMS());
        preferencias.setConectarRedesAbiertas(pantalla.getConectarRedesAbiertas());
        preferencias.setEnviarFotografias(pantalla.getEnviarFotografias());

    }


    public void verificarRegistro () {

        if (Constante.versionCompleta)
            return;

        if (pantalla.getConectarRedesAbiertas())
            requiereVersionDonacion();

        if (pantalla.getEnviarFotografias())
            requiereVersionDonacion();

        if (preferencias.getVersionRegistrada())
            return;


        if (pantalla.getIniciarConSistema())
            versionNoRegistrada ();

        if (pantalla.getActivarConPantalla())
            versionNoRegistrada();

    }

    private void versionNoRegistrada() {
        pantalla.setIniciarConSistema(false);
        pantalla.setActivarConPantalla(false);
        pantalla.snack(R.string.requiereregistro);
    }

    private void requiereVersionDonacion () {
        pantalla.setConectarseRedesAbiertas(false);
        pantalla.snack(R.string.requieredonacion);
    }

    public void mostrarContactos () {
        // ver-
    }



    public void menu (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ayuda:
                iniciarActividad(ActividadAyudaOpciones.class);
                break;

            case R.id.menu_bot:
                EnlaceExterno enlaceExterno = new EnlaceExterno(activity);
                enlaceExterno.abrirEnlace(Constante.urlBot);
                break;

            case R.id.menu_contactos:
                // intentContactos = iniciarActividad(ActividadContactos.class);

                contacto = new Contacto(activity);
                contacto.abrirPantalla();
                break;

            default:
                // es el botón de atrás
                pantalla.finalizarActividad();
                break;
        }
    }





    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != Contacto.CODIGO)
            return;

        if (resultCode != RESULT_OK)
            return;

        String numero = contacto.getNumero(data);
        pantalla.setSms(numero);

    }

    private void mensajeLog (String texto) {
        MensajeRegistro.msj (this, texto);
    }
}
