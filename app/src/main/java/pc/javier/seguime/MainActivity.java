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


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.utilidades.Efecto;
import pc.javier.seguime.utilidades.Parametro;

public class MainActivity extends AppCompatActivity {


    Aplicacion aplicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarAplicacion();

        // muesta el menu por primera vez
        if (Aplicacion.preferenciaBooleano("ayudamostrarmenu") == false) {
            Aplicacion.preferenciaBooleano("ayudamostrarmenu", true);
            AbrirMenu();
        }
    }


    private void AbrirMenu () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
        if (!drawer.isDrawerOpen(GravityCompat.START))
            drawer.openDrawer(GravityCompat.START);

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


    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {



        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        TextView textViewUsuario = (TextView) findViewById(R.id.menu_usuario);
        if (textViewUsuario != null)
            textViewUsuario.setText(Aplicacion.Usuario());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        return true;
    }

    public void PrincipalMenuClick (MenuItem item) {
        AbrirCerrarMenuLateral();
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

        Button boton = (Button) findViewById(R.id.princ_boton);
        boton.clearAnimation();
        boton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_atras_entrada));

    }





    // redibuja el boton principal
    private void mostrarBoton () {
        Button boton = (Button) findViewById(R.id.princ_boton);
        TextView estado = (TextView) findViewById(R.id.princ_estado);
        ConstraintLayout pantalla = (ConstraintLayout) findViewById(R.id.princ_pantalla);


        // ((Button) boton).setBackgroundColor(Color.TRANSPARENT);

        int botonActivado = 0;
        int botonDesactivado = 0;
        if (boton.getResources().getConfiguration().orientation == boton.getResources().getConfiguration().ORIENTATION_LANDSCAPE){
            botonActivado = R.drawable.botonprincipalcuadradoverde;
            botonDesactivado =R.drawable.botonprincipalcuadradogris;
        } else {
            botonActivado = R.drawable.anilloverde;
            botonDesactivado =R.drawable.botonprincipalredondogris;
        }

        if (aplicacion.servicioActivo()) {
            ((Button) boton).setText(getText(R.string.desactivar_aplicacion));
            ((Button) boton).setTextColor(Color.BLACK);
            estado.setText(R.string.txt_servicio_activo);
            estado.setTextColor(Color.BLACK);
            boton.setBackgroundResource(botonActivado);
            // pantalla.setBackgroundResource(R.drawable.camino);
        } else {
            ((Button) boton).setText(getText(R.string.activar_aplicacion));
            ((Button) boton).setTextColor(Color.DKGRAY);
            estado.setText(R.string.txt_servicio_inactivo);
            estado.setTextColor(Color.RED);
            boton.setBackgroundResource(botonDesactivado);
            // pantalla.setBackgroundResource(R.drawable.caminoazul);

        }



    }










    // al iniciar la aplicacion (onCreate)
    private void iniciarAplicacion () {
        aplicacion = new Aplicacion(getApplicationContext(), this);

        Parametro.aplicacion = aplicacion;



    }


    // cuando vuelve la aplicacion (onResume)
    // refresca la pantalla
    private void regresarAplicacion () {
        //aplicacion.cargarConfiguracion();


        if (Aplicacion.preferenciaBooleano("presentacion") == false){
            startActivity(new Intent(this, ActividadPresentacion.class));
            overridePendingTransition(R.anim.zoom_entrada, R.anim.zoom_salida);
            return;
        }


        mensajeLog ("verificando sesion");

        // si no hay una sesion -> va a la pantalla de inicio
        if (aplicacion.sesionIniciada() == false) {
            aplicacion.detenerServicio();
            mensajeLog ( "no hay sesion");
            Intent i;
            i = new Intent(this, ActividadSesion.class);
            startActivity(i);
            return;
        }


        if(aplicacion.notificacion() != ""){
            MostrarMensaje(aplicacion.notificacion().toString());
            aplicacion.borrarNotificacion();
        }

        if (aplicacion.mensaje() != "") {
            TextView t;
            t = (TextView) findViewById(R.id.princ_mensaje);
            t.setText(aplicacion.mensaje().toString());
            t.setVisibility(View.VISIBLE);
        }


        if (Aplicacion.Registrada())
            ((TextView)findViewById(R.id.princ_registrada)).setText(getString(R.string.versionRegistrada) + " ["+ Aplicacion.preferenciaCadena("registrada") +"] :-)");


        ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.menu_registraraplicacion).setEnabled(!Aplicacion.Registrada());


        if(findViewById(R.id.menu_usuario) != null)
            ((TextView) findViewById(R.id.menu_usuario)).setText(Aplicacion.Usuario());




    }

    // cierra el mensaje de la pantalla
    public void cerrarMensaje (View v) {
        aplicacion.borrarMensaje();
        TextView t;
        t = (TextView) v.findViewById(R.id.princ_mensaje);
        t.setText("");
        t.setVisibility(View.INVISIBLE);
    }






    private void mostrarIconos () {
        // muestra icono de rastreo activo
        Efecto.AnimarIcono((ImageView)findViewById(R.id.princ_iconorastreo) ,  (Aplicacion.rastreo()));

        Efecto.AnimarIcono((ImageView)findViewById(R.id.princ_iconotemporizador), (Aplicacion.alarmaExiste()));

        Efecto.AnimarIcono((ImageView)findViewById(R.id.princ_iconoseguime), (aplicacion.servicioActivo()));

        Efecto.AnimarIcono((ImageView) findViewById(R.id.princ_iconotemporizadorservidor), (!Aplicacion.alarmaServidor().equals("")));

    }




    // clicks en iconos

    public void clickrastreo (View v) {
        MostrarMensaje(getString(R.string.principal_rastreo));
    }
    public void clickseguime (View v) {
        MostrarMensaje(getString(R.string.principal_aplicacion));
    }
    public void clickalarma (View v) {
        MostrarMensaje(getString(R.string.principal_alarma));
    }
    public void clickinternet (View v) {
        MostrarMensaje(getString( R.string.principal_internet));
    }
    public void clickalarmaservidor (View v) {
        MostrarMensaje(getString(R.string.principal_alarmaservidor));
    }







    private void mensajeLog (String texto) {
        Log.d("Actividad Principal", texto);
    }






















    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    public boolean MenuClick (MenuItem item) {

        if (aplicacion.estaBloqueado()) {
            Toast.makeText(MainActivity.this, R.string.txt_bloqueado, Toast.LENGTH_LONG).show();
            return true;
        }

        Intent i;
        switch (item.getItemId()) {
            case R.id.menu_ayuda:
                i = new Intent(this, ActividadAyuda.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_entrada, R.anim.zoom_salida);
                break;
            case R.id.menu_opciones:
                i = new Intent(this, ActividadOpciones.class);
                startActivity(i);
                overridePendingTransition(R.anim.desvanecer_entrada, R.anim.desvanecer_salida);
                break;

            case R.id.menu_registros:
                i = new Intent(this, ActividadRegistros.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_entrada,R.anim.zoom_salida);
                break;

            case R.id.menu_cuentaregresiva:
                i = new Intent(this, ActividadRegresiva.class);
                startActivity(i);
                overridePendingTransition(R.anim.izquierda_entrada, R.anim.izquierda_salida);
                break;

            case R.id.menu_registraraplicacion:
                i = new Intent(this, ActividadClave.class);
                startActivity(i);
                overridePendingTransition(R.anim.zoom_entrada, R.anim.zoom_salida);
                break;

            case R.id.menu_donar:
                donar();
                break;


            case R.id.menu_cerrarsesion:

               CerrarSesion();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    private void CerrarSesion() {
        aplicacion.detenerServicio();
        if (aplicacion.estaBloqueado())
            return ;
        if (aplicacion.alarmaExiste())
            return ;



        new AlertDialog.Builder(this)
            .setTitle(R.string.cerrar_sesion)
            .setMessage(R.string.cerrarsesion_mensajeadvertencia)
            .setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aplicacion.cerrarSesion();
                            finish();
                        }
                    }
                )
            .setNegativeButton(android.R.string.no,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }
                )
            .show();

    }


    private void MostrarMensaje (String texto) {
        View view = getCurrentFocus();
        Snackbar.make(view , texto, Snackbar.LENGTH_LONG).show();

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU)
            AbrirCerrarMenuLateral();



        return super.onKeyUp(keyCode, event);
    }


    private void AbrirCerrarMenuLateral () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            drawer.openDrawer(GravityCompat.START);
    }


    private void navegar (String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void donar() {
        navegar("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BDUHGWZKV2R8W");
    }
}
