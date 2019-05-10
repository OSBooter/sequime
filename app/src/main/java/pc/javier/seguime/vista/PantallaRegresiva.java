package pc.javier.seguime.vista;

import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Javier 2019.
 */

public class PantallaRegresiva extends Pantalla {
    public PantallaRegresiva(Activity activity) {
        super(activity);

        definirLimites();
    }

    private  int idHora = R.id.temporizador_hora;
    private int idMinuto = R.id.temporizador_minuto;
    private int idSegundo = R.id.temporizador_segundo;

    private int idTexto = R.id.temporizador_mensaje_alerta;
    private int idSms = R.id.temporizador_sms;

    private void definirLimites () {

        getNumberPicker(idHora).setWrapSelectorWheel(true);
        getNumberPicker(idHora).setMinValue(0);
        getNumberPicker(idHora).setMaxValue(24);

        getNumberPicker(idMinuto).setWrapSelectorWheel(true);
        getNumberPicker(idMinuto).setMinValue(0);
        getNumberPicker(idMinuto).setMaxValue(59);

        getNumberPicker(idSegundo).setWrapSelectorWheel(true);
        getNumberPicker(idSegundo).setMinValue(0);
        getNumberPicker(idSegundo).setMaxValue(59);

        contador(0,5,0);
    }


    public void contador (int hora, int minuto, int segundo) {
        getNumberPicker(idHora).setValue(hora);
        getNumberPicker(idMinuto).setValue(minuto);
        getNumberPicker(idSegundo).setValue(segundo);
    }




    public void dibujarBoton (boolean activo) {
        int idBoton = R.id.temporizador_boton;
        if (activo) {
            setFondo(idBoton, cuadroRojoRedondeando());
            setButtonText(idBoton, R.string.detener);
        } else {
            setFondo(idBoton, cuadroVerdeRedondeando());
            setButtonText(idBoton, R.string.iniciar);
        }

    }
}
