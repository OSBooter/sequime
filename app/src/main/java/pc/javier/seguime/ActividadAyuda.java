package pc.javier.seguime;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pc.javier.seguime.adaptador.Constante;
import utilidades.basico.EnlaceExterno;


public class ActividadAyuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);
        ((TextView) findViewById(R.id.version)).setText(Constante.version);
    }



    public void donar(View v) {
        EnlaceExterno enlaceExterno = new EnlaceExterno(this);
        enlaceExterno.abrirEnlace(Constante.urlPaypal);
    }

    public void sitio (View v) {
        EnlaceExterno enlaceExterno = new EnlaceExterno(this);
        enlaceExterno.abrirEnlace(Constante.urlSitio);
    }



}
