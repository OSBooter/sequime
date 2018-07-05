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

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.utilidades.Parametro;

public class ActividadOpciones extends AppCompatActivity {

    SharedPreferences preferencias;

    EditText Tsms ;
    EditText Ttelegram ;
    EditText Tactividad ;
    EditText Tinactividad;
    Switch Srastreo;
    Switch Siniciar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        Tsms = (EditText) findViewById(R.id.opciones_sms);
        Ttelegram = (EditText) findViewById(R.id.opciones_telegram);
        Tactividad = (EditText) findViewById(R.id.opciones_actividad);
        Tinactividad = (EditText) findViewById(R.id.opciones_inactividad);
        Srastreo = (Switch) findViewById(R.id.opciones_rastreo);
        Siniciar = (Switch) findViewById(R.id.opciones_iniciarConElSistema);

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

        if (!Aplicacion.Registrada())
            if (Siniciar.isChecked()) {
                Toast.makeText(this, R.string.requiereregistro, Toast.LENGTH_LONG).show();
                Siniciar.setChecked(false);
                Siniciar.setEnabled(false);
                return ;
            }

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
        boolean iniciar = preferencias.getBoolean("iniciar", false);


        Tsms.setText(sms);
        Ttelegram.setText(telegram);
        Tactividad.setText(String.valueOf(actividad));
        Tinactividad.setText(String.valueOf(inactividad));
        Srastreo.setChecked(rastreo);
        Siniciar.setChecked(iniciar);

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
        boolean rastreo = Srastreo.isChecked();
        boolean iniciar = Siniciar.isChecked();





        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("actividad", actividad);
        editor.putInt("inactividad", inactividad);

        editor.putString("sms", sms);
        editor.putString("telegram", telegram);

        editor.putBoolean("rastreo", rastreo);
        editor.putBoolean("iniciar", iniciar);
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



    public void IniciarConElSistema (View v) {
        if (!Aplicacion.Registrada()) {
            Intent i = new Intent (this, ActividadClave.class);
            startActivity(i);
        }

        if (!Aplicacion.Registrada())
            Siniciar.setChecked(false);

    }

}
