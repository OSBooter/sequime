package pc.javier.seguime;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import pc.javier.seguime.adaptador.Pantalla;
import pc.javier.seguime.adaptador.Preferencias;



public class ActividadDesbloqueo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desbloqueo);
    }




    public void clickDesbloquear (View v) {
        Preferencias preferencias = new Preferencias(this);
        Pantalla pantalla = new Pantalla(this);
        String clave = pantalla.getTexto(R.id.desbloqueo_clave);

        if (clave.equals(preferencias.getClave())) {
            preferencias.setBloqueado(false);
            finish();
        } else {
            pantalla.snack(R.string.claveIncorrecta);
        }

    }


}
