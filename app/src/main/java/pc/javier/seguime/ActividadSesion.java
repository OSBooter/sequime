package pc.javier.seguime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import pc.javier.seguime.control.ControlPantallaSesion;



/**
 * Javier  2017.
 * Editado 2019
 */

public class ActividadSesion extends AppCompatActivity {


    ControlPantallaSesion control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion);


        control = new ControlPantallaSesion(this);
        control.cargarOpciones();
    }


    @Override
    protected  void onResume () {
        super.onResume();
        control.actualizarBotones ();
        control.resumir();
    }


    @Override
    protected void onDestroy () {
        super.onDestroy();
        control.destruir();
    }




    public void iniciar (View v) {
        control.iniciar();
    }

    public void cancelar (View v) {
        control.cancelar ();
    }

    public void cambiarRadio (View view) {
        control.cambiarRadio();
    }



    private void mensajeLog (String texto) {
        Log.d("Actividad Sesion", texto);
    }

}
