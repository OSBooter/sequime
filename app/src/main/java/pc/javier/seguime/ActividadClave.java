package pc.javier.seguime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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



    public void Registrar (View view) {
        String clave = Aplicacion.Usuario().toLowerCase();
        clave = clave + clave.length();

        if (tClave.getText().toString().equals(clave)) {
            Aplicacion.Registrada(tNombre.getText().toString());
            this.finish();
        } else {
            Toast.makeText(this, R.string.claveIncorrecta, Toast.LENGTH_SHORT).show();
        }
    }


}
