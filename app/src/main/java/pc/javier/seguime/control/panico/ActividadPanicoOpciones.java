package pc.javier.seguime.control.panico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pc.javier.seguime.R;

/**
 * Created by Javier on 25/7/2019.
 */

public class ActividadPanicoOpciones extends AppCompatActivity {



    ControlPantallaPanico control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcionespanico);
        control = new ControlPantallaPanico( this);
    }


    public void cancelar (View v) {
        control.cancelar ();
    }


    public void guardar (View v) {
        control.guardar();
    }


}
