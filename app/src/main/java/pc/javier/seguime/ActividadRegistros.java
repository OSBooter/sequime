package pc.javier.seguime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.ControlPantallaRegistros;
import pc.javier.seguime.vista.PantallaRegistros;

public class ActividadRegistros extends AppCompatActivity {



    ControlPantallaRegistros controlPantallaRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registros);


        Preferencias preferencias = new Preferencias(this);
        PantallaRegistros pantallaRegistros = new PantallaRegistros(this);
        controlPantallaRegistros = new ControlPantallaRegistros(pantallaRegistros, preferencias);

    }













    private void mensajeLog (String texto) {
        Log.d("Actividad Registro", texto);
    }


}
