package pc.javier.seguime.vista;

import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Javier 2019.
 */

public class PantallaOpciones extends Pantalla {
    public PantallaOpciones(Activity activity) {
        super(activity);
    }




    // gets
    public boolean getEnviarInfoConexion () {
        return getToggle (R.id.opciones_enviarInfoConexion).isChecked();
    }

    public int getInternet () {
        return entero(getTexto(R.id.opciones_internet));
    }

    public int getActividad () {
        return entero(getTexto(R.id.opciones_actividad));
    }

    public int getInactividad () {
        return entero(getTexto(R.id.opciones_inactividad));
    }

    public String getSms () {
        return getTexto(R.id.opciones_sms).trim();
    }

    public String getTelegram () {
        return getTexto(R.id.opciones_telegram);
    }

    public boolean getIniciarConSistema () {
        return getToggle (R.id.opciones_iniciarConElSistema).isChecked();
    }

    public boolean getRastreo () {
        return getToggle (R.id.opciones_rastreo).isChecked();
    }

    public boolean getActivarConPantalla () {
        return getToggle (R.id.opciones_activarconpantalla).isChecked();
    }






    // sets -

    public void  setEnviarInfoConexion (boolean valor) {
        setToggle(R.id.opciones_enviarInfoConexion, valor);
    }

    public void  setInternet (int valor) {
        setTextView(R.id.opciones_internet, String.valueOf(valor));
    }

    public void  setActividad (int valor) {
        setTextView(R.id.opciones_actividad, String.valueOf(valor));
    }

    public void setInactividad (int valor) {
        setTextView(R.id.opciones_inactividad, String.valueOf(valor));
    }

    public void setSms (String valor) {
        setTextView(R.id.opciones_sms, valor);
    }

    public void setTelegram (String valor) {
        setTextView(R.id.opciones_telegram, valor);
    }

    public void setIniciarConSistema (boolean valor) {
        setToggle (R.id.opciones_iniciarConElSistema, valor);
    }

    public void setRastreo (boolean valor) {
        setToggle (R.id.opciones_rastreo, valor);
    }

    public void setActivarConPantalla (boolean valor) {
        setToggle (R.id.opciones_activarconpantalla, valor);
    }





}
