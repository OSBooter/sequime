package pc.javier.seguime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import pc.javier.seguime.interfaz.Aplicacion;

/**
 * Created by Usuario NoTeBooK on 1 jul 2018.
 */

public class ActividadClave extends AppCompatActivity {


    EditText tClave ;
    EditText tNombre ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clave);

        tClave = (EditText) findViewById(R.id.clave_clave);
        tNombre= (EditText) findViewById(R.id.clave_nombre);


    }



    public void RegistrarVersion (View view) {
        String clave = Aplicacion.Usuario().toLowerCase();
        clave = clave + clave.length();


        if (tClave.getText().toString().equals(clave)) {
            Aplicacion.Registrada(tNombre.getText().toString().trim());
            this.finish();
        } else {
            MostrarMensaje(getString(R.string.claveIncorrecta));
        }
    }





    private void MostrarMensaje (String texto) {
        View view = getCurrentFocus();
        Snackbar.make(view , texto, Snackbar.LENGTH_LONG).show();

    }


    public void ObtenerClave (View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://seguime.000webhostapp.com/registroversion.php"));
        startActivity(i);
    }



    }
