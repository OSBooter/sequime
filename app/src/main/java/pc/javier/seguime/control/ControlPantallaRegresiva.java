package pc.javier.seguime.control;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import pc.javier.seguime.ActividadAyudaRegresiva;
import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Constante;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.receptor.ReceptorContadorRegresiva;
import pc.javier.seguime.vista.PantallaRegresiva;
import utilidades.Alarma;
import utilidades.Contacto;
import utilidades.basico.Evento;
import utilidades.basico.MensajeRegistro;
import utilidades.basico.Temporizador;

import static android.app.Activity.RESULT_OK;

/**
 * Javier 15/04/2019.
 */

public class ControlPantallaRegresiva extends Control {

    private Alarma alarma = new Alarma();
    private Temporizador temporizador;
    private PantallaRegresiva pantalla;
    private Evento evento;
    private Contacto contacto;



    public ControlPantallaRegresiva(Activity activity) {
        super(activity);
        this.pantalla  = new PantallaRegresiva(activity);

        String clave = "regresiva";
        ReceptorContadorRegresiva receptorContadorRegresiva = new ReceptorContadorRegresiva(clave, pantalla);
        evento = new Evento(receptorContadorRegresiva, clave);

        String sms = preferencias.getNumeroSms();
        String telegram = preferencias.getIdTelegram();

        pantalla.setEditText(R.id.temporizador_sms, sms);
        pantalla.setEditText(R.id.temporizador_telegram, telegram);

        if (Constante.versionCompleta == false) {
            pantalla.setHabilitado(R.id.temporizador_sms, false);
            pantalla.snack(R.string.versionIncompleta_txt);
        }

    }



    // on resume
    public void actualizarPantalla () {


        boolean alarmaExiste = alarmaExiste();
        pantalla.dibujarBoton(alarmaExiste);
        if (!alarmaExiste)
            return;


        alarma.setFin(preferencias.getAlarma());
        activarTemporizador();


        MensajeRegistro.msj(preferencias.getAlarma());
        MensajeRegistro.msj(alarma.getFin().toString());
        MensajeRegistro.msj(alarma.getMinutos());
        MensajeRegistro.msj(alarma.getSegundos());



    }

    // on destroy
    public void salir () {
        if (temporizador != null)
            temporizador.detener();
        MensajeRegistro.msj ("control finalizado");
    }



    // al pulsar el boton de iniciar / detener
    public void iniciarCuentaRegresiva() {
        if (alarmaExiste())
            detenerAlarma ();
        else
            iniciarAlarma();
    }







    // estados de la alarma -----------------------------------------------
    private boolean alarmaExiste() {
        return  preferencias.getAlarma() != 0;
    }


    // control de la alarma --------------------------------------------------

    private void detenerAlarma() {
        if (temporizador != null)
            temporizador.detener();
        preferencias.borrar(Preferencias.TipoPreferencia.alarma);
        pantalla.dibujarBoton(false);
    }



    private void iniciarAlarma() {
        // configura el objeto alarma según lo indicado en pantalla
        actualizarRelojInterno();

        // guarda la configuración de la alarma
        preferencias.setAlarma(alarma.getFin().getTime());

        // guarda el mensaje de emergencia
        preferencias.setAlarmaMensaje (pantalla.getTexto(R.id.temporizador_mensaje_alerta));

        // guarda el número sms
        preferencias.setNumeroSms(pantalla.getTexto(R.id.temporizador_sms));

        // guarda el número telegram
        preferencias.setIdTelegram(pantalla.getTexto(R.id.temporizador_telegram));

        // reinicia el sistema
        detenerServicio(ServicioAplicacion.class);
        iniciarServicio(ServicioAplicacion.class);

        pantalla.dibujarBoton(true);

        // temporizador interno, para visualizar el tiempo restante
        activarTemporizador();

    }





    // temporizador ---------------------------------------------------------------------
    // (usado para mostrar el tiempo restante en la pantalla)


    // configura la alarma según lo indicado en pantalla
    private void actualizarRelojInterno () {
        int h = pantalla.getNumberPicker(R.id.temporizador_hora).getValue();
        int m = pantalla.getNumberPicker(R.id.temporizador_minuto).getValue();
        int s = pantalla.getNumberPicker(R.id.temporizador_segundo).getValue();
        alarma.setDuracion(h,m,s);
    }



    // pone en marcha el temporizador interno para visualizar el tiempo restante
    private void activarTemporizador () {
        if (alarma.activada())
            return;
        temporizador = new Temporizador() {
            @Override
            public void ejecutarTarea () {
                evento.emitir(String.valueOf(alarma.getFin().getTime()));
                if (alarma.activada())
                    detener();
            }
        };
        temporizador.setIntervalo(1);
        temporizador.iniciar();
    }









    public void menu (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ayuda:
                iniciarActividad(ActividadAyudaRegresiva.class);
                break;

            case R.id.menu_contactos:
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

        pantalla.setEditText(R.id.temporizador_sms, numero);

    }

}
