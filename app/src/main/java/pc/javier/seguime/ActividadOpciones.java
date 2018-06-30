package pc.javier.seguime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import pc.javier.seguime.utilidades.Parametro;

public class ActividadOpciones extends AppCompatActivity {

    SharedPreferences preferencias;

    EditText Tsms ;
    EditText Ttelegram ;
    EditText Tactividad ;
    EditText Tinactividad;
    Switch Crastreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        Tsms = (EditText) findViewById(R.id.opciones_sms);
        Ttelegram = (EditText) findViewById(R.id.opciones_telegram);
        Tactividad = (EditText) findViewById(R.id.opciones_actividad);
        Tinactividad = (EditText) findViewById(R.id.opciones_inactividad);
        Crastreo = (Switch) findViewById(R.id.opciones_rastreo);

        preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);
        cargarOpciones();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Parametro.telefono == null)
            return;
        if (Parametro.telefono.equals(""))
            return;
        Tsms.setText(Parametro.telefono);
        Parametro.telefono = "";
    }




    public void cancelar (View v) {
        this.finish();
    }


    public void guardar (View v) {
        if (guardarOpciones())
            this.finish();
        else
            Toast.makeText(this, R.string.errorValidacion, Toast.LENGTH_LONG).show();

    }



    private void cargarOpciones() {


        String sms = preferencias.getString("sms", "");
        String telegram = preferencias.getString("telegram", "");
        int actividad = preferencias.getInt("actividad", 0);
        int inactividad = preferencias.getInt("inactividad", 0);
        boolean rastreo = preferencias.getBoolean("rastreo", false);


        Tsms.setText(sms);
        Ttelegram.setText(telegram);
        Tactividad.setText(String.valueOf(actividad));
        Tinactividad.setText(String.valueOf(inactividad));
        Crastreo.setChecked(rastreo);

    }

    private boolean guardarOpciones () {

        String activ = Tactividad.getText().toString();
        String inactiv = Tinactividad.getText().toString();
        int actividad =0;
        int inactividad =0;
        try {
            if (activ != "")
                actividad = Integer.valueOf(activ);
            if (inactiv != "")
                inactividad = Integer.valueOf(inactiv);
        } catch (Exception e) {
            return false;
        }


        String sms =  Tsms.getText().toString();
        String telegram = Ttelegram.getText().toString();
        boolean rastreo = Crastreo.isChecked();




        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("actividad", actividad);
        editor.putInt("inactividad", inactividad);

        editor.putString("sms", sms);
        editor.putString("telegram", telegram);

        editor.putBoolean("rastreo", rastreo);
        editor.commit();
        return true;
    }






    // contactos
    public  void mostrarContactos (View v) {

        Parametro.telefono = "";
        Intent contactos = new Intent(this, ActividadContactos.class);
        if (Tsms.getText().toString().equals(""))
            startActivity(contactos);
    }


    // menu

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }


    public void MenuClick (MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_ayuda:
                Intent i = new Intent(this, ActividadAyudaOpciones.class);
                startActivity(i);
                break;

            case R.id.menu_bot:

                navegar("http://t.me/seguimebot");

                break;
        }
    }


    private void navegar (String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

/*
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (aplicacion.estaBloqueado()) {
            Toast.makeText(MainActivity.this, R.string.txt_bloqueado, Toast.LENGTH_SHORT).show();
            return true;
        }
        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.menu_ayuda:
                i = new Intent(this, ActividadAyuda.class);
                startActivity(i);
                break;
            case R.id.menu_opciones:
                i = new Intent(this, ActividadOpciones.class);
                startActivity(i);
                break;

            case R.id.menu_registros:
                i = new Intent(this, ActividadRegistros.class);
                startActivity(i);
                break;


            case R.id.menu_salir:
                aplicacion.cerrarSesion();
                if (aplicacion.estaBloqueado())
                    return true;
                this.finish();
                break;
        }
        return true;
    }

*/



}
