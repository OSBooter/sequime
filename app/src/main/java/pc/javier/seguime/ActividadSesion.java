package pc.javier.seguime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import pc.javier.seguime.handler.Comandos;
import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.Internet;
import pc.javier.seguime.utilidades.Boton;

/**
 * Created by Javier on 26 dic 2017.
 */

public class ActividadSesion extends AppCompatActivity {
    SharedPreferences preferencias;

    EditText Tservidor;
    EditText Tusuario;
    EditText Tclave;
    TextView TclaveRepetidaTexto;
    EditText TclaveRepetida;
    TextView Testado;
    Button botonIniciarSesion;
    //CheckBox Cssl;


    String servidor ;
    String usuario ;
    String clave ;
    String claveRepetida;
    //Boolean ssl;

    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion);

        Tservidor = (EditText) findViewById(R.id.sesion_servidor);
        Tusuario = (EditText) findViewById(R.id.sesion_usuario);
        Tclave = (EditText) findViewById(R.id.sesion_clave);
        TclaveRepetida = (EditText) findViewById(R.id.sesion_claverepetida);
        TclaveRepetidaTexto = (TextView) findViewById(R.id.sesion_claverepetida_texto);
        Testado= (TextView) findViewById(R.id.sesion_estado);
        //Cssl = (CheckBox) findViewById(R.id.sesion_ssl);

        botonIniciarSesion = (Button) findViewById(R.id.session_iniciar);

        preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);
        cargarOpciones();

        Comandos comandos = new Comandos(this);
        handler = new Handler(comandos);

    }




    // carga y muestra las opciones guardadas en la pantalla de configuracion
    private void  cargarOpciones () {
        String servidor = preferencias.getString("servidor", "");
        String usuario = preferencias.getString("usuario", "");
        String clave = preferencias.getString("clave", "");
        Boolean ssl = preferencias.getBoolean("ssl", false);

        Tservidor.setText(servidor);
        Tusuario.setText(usuario);
        //Cssl.setChecked(ssl);

    }


    private void guardarOpciones () {
        servidor = Tservidor.getText().toString().trim();
        usuario = Tusuario.getText().toString().trim();
        clave = Tclave.getText().toString();
        //ssl = Cssl.isChecked();

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("servidor", servidor);
        editor.putString("usuario", usuario);
        editor.putString("clave", clave);
        //editor.putBoolean("ssl", ssl);
        //editor.putBoolean("sesion", true);
        editor.commit();
    }





    public void salir (View v) {
        // este codigo CIERRA LA APLICACION
        // fuente: https://jcristoballopez.wordpress.com/2015/03/20/anadir-boton-de-salida-con-android-studio/
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void iniciar (View v) {

        RadioButton radio = (RadioButton) findViewById(R.id.sesion_radio_registro);
        clave = Tclave.getText().toString();
        usuario = Tusuario.getText().toString().trim();
        servidor = Tservidor.getText().toString().trim();


        String parametros = "comando=sesion";


        if (radio.isChecked()) {
            claveRepetida = TclaveRepetida.getText().toString();
            if (!clave.equals(claveRepetida)) {
                //Testado.setText("Las claves NO coinciden");
                MostrarMensaje(getString(R.string.sesion_clavedesigual));
                return;
            }
            parametros = "comando=registro";
        }

        if (clave.length() < 4) {
            MostrarMensaje(getString(R.string.sesion_clavecorta));
            return;
        }

        if (usuario.length() < 4) {
            MostrarMensaje(getString(R.string.sesion_usuariocorto));
            return;
        }

        if (servidor.length() < 2) {
            MostrarMensaje(getString(R.string.sesion_sinservidor));
            return;
        }


        MostrarMensaje(getString(R.string.espere));

        // deshabilita el boton asi no se pulsa 90 veces
        Boton.Estado(botonIniciarSesion, false);
        guardarOpciones();


        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;




        mensajeLog ( "Enviando inicio de sesion  - Parametros: " + parametros);


        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();

    }




    public void cambiarRadio (View view) {
        RadioButton radio = (RadioButton) findViewById(R.id.sesion_radio_iniciar);
        if (radio.isChecked()) {
            TclaveRepetidaTexto.setVisibility(View.INVISIBLE);
            TclaveRepetida.setVisibility(View.INVISIBLE);
            botonIniciarSesion.setText(R.string.iniciarsesion);
        } else {
            TclaveRepetidaTexto.setVisibility(View.VISIBLE);
            TclaveRepetida.setVisibility(View.VISIBLE);
            botonIniciarSesion.setText(R.string.registrarse);
        }
    }

    @Override
    public void onBackPressed() {    }


    private void MostrarMensaje (String texto) {
        View view = getCurrentFocus();
        Snackbar.make(view , texto, Snackbar.LENGTH_LONG).show();

    }

    private void mensajeLog (String texto) {
        Log.d("Actividad Sesion", texto);
    }

}
