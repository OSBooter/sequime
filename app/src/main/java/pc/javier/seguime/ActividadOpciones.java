package pc.javier.seguime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.ControlPantallaOpciones;
import pc.javier.seguime.vista.PantallaOpciones;


public class ActividadOpciones extends AppCompatActivity {

    ControlPantallaOpciones control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        PantallaOpciones pantalla = new PantallaOpciones(this);
        Preferencias preferencias = new Preferencias(this);
        control = new ControlPantallaOpciones(pantalla, preferencias);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        control.onActivityResult(requestCode, resultCode, data);
    }

    public void verificarRegistro (View v) {
        control.verificarRegistro ();
    }

    public void cancelar (View v) {
        control.cancelar ();
    }


    public void aceptar (View v) {
        control.guardar();
    }







    // Menú superior ---------------
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        control.menu (item);
        return true;
    }



}
