package pc.javier.seguime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pc.javier.seguime.control.ControlPantallaRegresiva;


/**
 *  Javier  24 mar 2018.
 *  Editado 2019
 */

public class ActividadRegresiva extends AppCompatActivity {

    ControlPantallaRegresiva control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regresiva);

        control = new ControlPantallaRegresiva(this);
    }


    @Override
    public void onResume () {
        super.onResume();
        control.actualizarPantalla();
    }

    @Override
    public void onDestroy () {
        control.salir ();
        super.onDestroy();
    }


    // Menú superior ---------------
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresiva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        super.onOptionsItemSelected(item);
        control.menu (item);
        return true;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        control.onActivityResult (requestCode, resultCode, data);
    }

    public void clickBoton (View view) { control.iniciarCuentaRegresiva(); }



    private void mensajeLog (String texto) {
        Log.d("Actividad Regresiva", texto);
    }


}
