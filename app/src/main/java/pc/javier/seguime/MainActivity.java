package pc.javier.seguime;


/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.utilidades.FechaHora;
import pc.javier.seguime.utilidades.Parametro;


public class MainActivity extends AppCompatActivity {

    Aplicacion aplicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarAplicacion();

    }



    @Override
    protected void onResume () {
        super.onResume();
        regresarAplicacion();
        mostrarBoton();
        mostrarIconos();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Aplicacion.contexto = getApplicationContext();
        mensajeLog("DESTRUYENDO MAIN ACTIVITY");
    }



// menu

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);

        if (Aplicacion.Registrada())
            menu.findItem(R.id.menu_registraraplicacion).setEnabled(false).setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (aplicacion.estaBloqueado()) {
            Toast.makeText(MainActivity.this, R.string.txt_bloqueado, Toast.LENGTH_LONG).show();
            return true;
        }

        Intent i;
        switch (item.getItemId()) {
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

            case R.id.menu_cuentaregresiva:
                i = new Intent(this, ActividadRegresiva.class);
                startActivity(i);
                break;

            case R.id.menu_registraraplicacion:
                i = new Intent(this, ActividadClave.class);
                startActivity(i);
                break;


            case R.id.menu_salir:

                aplicacion.detenerServicio();
                if (aplicacion.estaBloqueado())
                    return true;
                if (aplicacion.alarmaExiste())
                    return true;
                aplicacion.cerrarSesion();
                this.finish();
                break;
        }
        return true;
    }





    // pulsa el boton de iniciar / detener la aplicacion
    public void iniciar (View v) {
        if (aplicacion.servicioActivo())  {
            aplicacion.detenerServicio();
            mensajeLog ("Deteniendo Servicio");
        }
        else {
            aplicacion.iniciarServicio();
            mensajeLog ( "Iniciando Servicio");
        }

        mostrarBoton();
        mostrarIconos();
    }





    // redibuja el boton principal
    private void mostrarBoton () {
        Button boton = (Button) findViewById(R.id.princ_boton);
        TextView estado = (TextView) findViewById(R.id.princ_estado);
        ConstraintLayout pantalla = (ConstraintLayout) findViewById(R.id.princ_pantalla);

        if (aplicacion.servicioActivo()) {
            ((Button) boton).setText(getText(R.string.desactivar_aplicacion));
            ((Button) boton).setBackgroundColor(Color.TRANSPARENT);
            ((Button) boton).setTextColor(Color.GRAY);
            estado.setText(R.string.txt_servicio_activo);
            estado.setTextColor(Color.GREEN);
            // pantalla.setBackgroundResource(R.drawable.camino);
        } else {
            ((Button) boton).setText(getText(R.string.activar_aplicacion));
            ((Button) boton).setBackgroundColor(Color.TRANSPARENT);
            ((Button) boton).setTextColor(Color.BLACK);

            estado.setText(R.string.txt_servicio_inactivo);
            estado.setTextColor(Color.RED);
            // pantalla.setBackgroundResource(R.drawable.caminoazul);

        }

    }










    // al iniciar la aplicacion (onCreate)
    private void iniciarAplicacion () {
        aplicacion = new Aplicacion(getApplicationContext(), this);

        Parametro.aplicacion = aplicacion;

         //PRUEBA        pruebaInternet ();

        //pruebaStrings();

    }


    // cuando vuelve la aplicacion (onResume)
    // refresca la pantalla
    private void regresarAplicacion () {
        //aplicacion.cargarConfiguracion();

        mensajeLog ("verificando sesion");

        // si no hay una sesion -> va a la pantalla de inicio
        if (aplicacion.sesionIniciada() == false) {
            aplicacion.detenerServicio();
            mensajeLog ( "no hay sesion");
            Intent i;
            i = new Intent(this, ActividadSesion.class);
            startActivity(i);

        }


        if(aplicacion.notificacion() != ""){
            Toast.makeText(MainActivity.this, aplicacion.notificacion().toString(), Toast.LENGTH_LONG).show();
            aplicacion.borrarNotificacion();
        }

        if (aplicacion.mensaje() != "") {
            TextView t;
            t = findViewById(R.id.princ_mensaje);
            t.setText(aplicacion.mensaje().toString());
        }


        if (Aplicacion.Registrada())
            ((TextView)findViewById(R.id.princ_registrada)).setText(getString(R.string.versionRegistrada) + " ["+ Aplicacion.preferenciaCadena("registrada") +"] :-)");

    }

    // cierra el mensaje de la pantalla
    public void cerrarMensaje (View v) {
        aplicacion.borrarMensaje();
        TextView t;
        t = v.findViewById(R.id.princ_mensaje);
        t.setText("");
    }





    // muestra iconos
    private void mostrarIconos () {
        // muestra icono de rastreo activo
        ImageView rastreo = (ImageView)findViewById(R.id.princ_iconorastreo);
        if (Aplicacion.rastreo())
            rastreo.setVisibility(View.VISIBLE);
        else
            rastreo.setVisibility(View.INVISIBLE);

        ImageView alarma = (ImageView)findViewById(R.id.princ_iconotemporizador);
        if (Aplicacion.alarmaExiste())
            alarma.setVisibility(View.VISIBLE);
        else
            alarma.setVisibility(View.INVISIBLE);

        ImageView seguime = (ImageView)findViewById(R.id.princ_iconoseguime);
        if (aplicacion.servicioActivo())
            seguime.setVisibility(View.VISIBLE);
        else
            seguime.setVisibility(View.INVISIBLE);

        ImageView alarmaServidor = (ImageView) findViewById(R.id.princ_iconotemporizadorservidor);
        if (Aplicacion.alarmaServidor().equals(""))
            alarmaServidor.setVisibility(View.INVISIBLE);
        else
            alarmaServidor.setVisibility(View.VISIBLE);

    }

    // clicks en iconos

    public void clickrastreo (View v) {
        Toast.makeText(MainActivity.this, R.string.principal_rastreo, Toast.LENGTH_LONG).show();
    }
    public void clickseguime (View v) {
        Toast.makeText(MainActivity.this, R.string.principal_aplicacion, Toast.LENGTH_LONG).show();
    }
    public void clickalarma (View v) {
        Toast.makeText(MainActivity.this, R.string.principal_alarma, Toast.LENGTH_LONG).show();
    }
    public void clickinternet (View v) {
        Toast.makeText(MainActivity.this, R.string.principal_internet, Toast.LENGTH_LONG).show();
    }
    public void clickalarmaservidor (View v) {
        Toast.makeText(MainActivity.this, R.string.principal_alarmaservidor, Toast.LENGTH_LONG).show();
    }







    private void mensajeLog (String texto) {
        Log.d("Actividad Principal", texto);
    }

}


