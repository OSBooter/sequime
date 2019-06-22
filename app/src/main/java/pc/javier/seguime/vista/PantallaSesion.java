package pc.javier.seguime.vista;

import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Javier 2019.
 * maneja la pantalla de sesión
 */

public class PantallaSesion extends Pantalla {
    public static boolean conexionActiva = false;
    private Activity activity;
    public PantallaSesion(Activity activity) {
        super(activity);
        this.activity = activity;
    }




    public void setServidor (String texto) {
        setEditText(R.id.sesion_servidor, texto);
    }
    public void setUsuario (String texto) {
        setEditText(R.id.sesion_usuario, texto);
    }

    public void setEstado (String texto) { setTextView(R.id.sesion_estado, texto); }
    public void setEstado (int idString) { setTextView(R.id.sesion_estado, idString); }


    public String getClaveRepetida () {
        return getEditText(R.id.sesion_claverepetida).getText().toString();
    }
    public String getClave () {
        return getEditText(R.id.sesion_clave).getText().toString();
    }
    public String getUsuario () {
        return getEditText(R.id.sesion_usuario).getText().toString();
    }
    public String getServidor () {
        return getEditText(R.id.sesion_servidor).getText().toString();
    }


    public boolean modoRegistro () {
        return getRadio(R.id.sesion_radio_registro).isChecked();
    }





    public void cambiarRadio () {
        if (getRadio(R.id.sesion_radio_iniciar).isChecked()) {
            setInvisible(R.id.sesion_claverepetida);
            setInvisible(R.id.sesion_claverepetida_texto);
            setButtonText(R.id.sesion_boton_iniciar, R.string.iniciarsesion);
        } else {
            setVisible(R.id.sesion_claverepetida);
            setVisible(R.id.sesion_claverepetida_texto);
            setButtonText(R.id.sesion_boton_iniciar, R.string.registrarse);
        }
    }



    public void habiltarBotonIniciar (boolean habilitado) {
        if (getButton(R.id.sesion_boton_iniciar) == null)
            return;

        getButton(R.id.sesion_boton_iniciar).setEnabled(habilitado);
        if (habilitado)
            setFondo(R.id.sesion_boton_iniciar, cuadroVerdeRedondeando());
        else
            setFondo(R.id.sesion_boton_iniciar, cuadroGrisRedondeando());
    }


}
