package pc.javier.seguime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.ControlPantallaImagenes;
import pc.javier.seguime.vista.PantallaRegistros;

/**
 * Created by Javier on 16/07/2019.
 */

public class ActividadImagenes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registros_imagenes);

        Preferencias preferencias = new Preferencias(this);
        PantallaRegistros pantallaRegistros = new PantallaRegistros(this);

        ControlPantallaImagenes controlPantallaImagenes;
        controlPantallaImagenes = new ControlPantallaImagenes ( pantallaRegistros, preferencias);

    }







}
