package pc.javier.seguime;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pc.javier.seguime.handler.Comandos;
import pc.javier.seguime.interfaz.Aplicacion;

public class ActividadAyuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);

        //Comandos comandos = new Comandos(actividad);



    }


    private void navegar (String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void donar(View v) {
        navegar("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BDUHGWZKV2R8W");
    }

    public void sitio (View v) {
        navegar("http://seguime.000webhostapp.com/");
    }



}
