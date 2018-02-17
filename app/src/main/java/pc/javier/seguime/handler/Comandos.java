package pc.javier.seguime.handler;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pc.javier.seguime.ActividadRegistros;
import pc.javier.seguime.ActividadSesion;
import pc.javier.seguime.R;
import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.BD;
import pc.javier.seguime.utilidades.ItemRegistro;

/**
 * Created by Javier on 14 feb 2018.
 *
 * Recibe comandos y realiza acciones
 */

public class Comandos implements Handler.Callback {

    private final AppCompatActivity activityHandler;

    public Comandos(AppCompatActivity activityHandler) {
        this.activityHandler = activityHandler;
    }


    private BD basededatos;
    private SharedPreferences preferencias;

    private TextView textView;
    private Button button;

    @Override
    public boolean handleMessage(Message mensaje) {

        Bundle bundle = mensaje.getData();
        String dato = bundle.getString("dato");
        String conexion = bundle.getString("conexion");


        mensajeLog("recibido: " + dato + "-" + conexion );

        if (dato != null)
            comando(dato);

        if (conexion != null)
            vista(conexion);

        return false;
    }




    private void vista (String conexion) {

        switch (conexion) {
            case "conectando":
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.conectando);

                break;

            case "finalizado":
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.conexionfinalizada);
                button = (Button) activityHandler.findViewById(R.id.session_iniciar);
                if (button != null)
                    button.setEnabled(true);
                break;

            case "error":
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.errorconexion);

                button = (Button) activityHandler.findViewById(R.id.session_iniciar);
                if (button != null)
                    button.setEnabled(true);

                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.errorconexion);
                break;


            case "enviando":
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.enviando);


            case "recibiendo":
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
                if (textView != null)
                    textView.setText(R.string.recibiendo);




        }




    }






    private void comando (String dato) {
        basededatos = Aplicacion.basededatos;
        preferencias = Aplicacion.preferencias;

        String comando;
        String parametro;

        int indice = dato.indexOf(" ");
        if (indice < 2)
            return;


        comando = dato.substring(0, indice);
        parametro = dato.substring(indice + 1);
        parametro = parametro.trim();


        mensajeLog("comando: (" + comando + ") parametro: (" + parametro + ")");

        if (comando.equals("marcar")) {
            basededatos.coordenadaMarcar(Integer.parseInt(parametro));

            if (ActividadRegistros.arregloRegistro != null)
                for (ItemRegistro x : ActividadRegistros.arregloRegistro)
                    if (x.id == Integer.parseInt(parametro)) {
                        x.recibido=1;
                        break;
                    }


            return;
        }


        if (comando.equals("eliminartodo")) {
            basededatos.coordenadaEliminar();
            return;
        }




        if (comando.equals("mensajeestado")) {
            textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);
            if (textView != null)
                textView.setText(parametro);

        }



                if (comando.equals("preferencia") || comando.equals("preferencias")) {
            // al parametro lo "subdivide" para encontrar
            // la clave y el valor de la preferencia
            comando = parametro;
            dato = parametro;
            parametro = "";

            indice = dato.indexOf(" ");
            if (indice >= 2) {
                comando = dato.substring(0, indice);
                comando = comando.toString().trim().toLowerCase();
                parametro = dato.substring(indice + 1);
                parametro = parametro.trim().toString();
            }


            SharedPreferences.Editor editor = preferencias.edit();

            mensajeLog("PREFERENCIAS: (" + comando + ") parametro: (" + parametro + ")");

            if (comando.equals("actividad") || (comando.equals("inactividad"))) {

                try {
                    editor.putInt(comando, Integer.parseInt(parametro));
                } catch (Exception e) {
                }
                editor.commit();
                return;
            }


            if (comando.equals("telegram")) {

                //case "servidor":
                editor.putString(comando, parametro);
                editor.commit();
                return;

            }

            // por SEGURIDAD impide modificar SMS, solo lo elimina
            if (comando.equals("sms")) {
                editor.putString(comando, "");
                editor.commit();
                return;
            }

            if (comando.equals("rastreo")) {
                if (parametro.equals( "0"))
                    editor.remove("rastreo");
                else
                    editor.putBoolean("rastreo", true);
                editor.commit();
                return;
            }


            if (comando.equals("bloqueo") || comando.equals("bloquea") || comando.equals("bloquear") || comando.equals("bloqueado")) {

                if (parametro.equals( "0"))
                    editor.remove("bloque");
                else
                    editor.putBoolean("bloqueo", true);
                editor.commit();
                return;
            }

            if (comando.equals("desbloqueo") || comando.equals("desbloquear") || comando.equals("desbloqueado")) {

                editor.remove("bloqueo");
                editor.commit();
                return;
            }

            if (comando.equals("sesion")) {
                mensajeLog("procesando la sesion ->" + parametro);
                textView = (TextView) activityHandler.findViewById(R.id.sesion_estado);

                if (parametro.equals("permitir")) {
                    mensajeLog("la sesion fue permitida");
                    editor.putBoolean("sesion", true);


                    if (textView != null)
                        textView.setText("¡HOLA!");
                    Toast.makeText(activityHandler, "¡HOLA! ;)", Toast.LENGTH_SHORT).show();

                    // cierra la pantalla de inicio de sesion
                    activityHandler.finish();


                } else {
                    editor.remove("sesion");
                    Toast.makeText(activityHandler, "Algo salio mal :O", Toast.LENGTH_SHORT).show();
                }

                editor.commit();
                return;
            }


            if (comando.equals("notificacion ")) {
                editor.putString(comando, parametro);
                editor.commit();
                return;
            }
            if (comando.equals("mensaje")) {
                mensajeLog("saludo recibido");
                textView = activityHandler.findViewById(R.id.princ_mensaje);
                if (textView != null)
                    textView.setText(parametro);
                editor.putString(comando, parametro);
                editor.commit();
                return;
            }


            editor.commit();
            return;
        }


    }





    private void mensajeLog (String texto) {
        Log.d("Comandos (Handler): ", texto);
    }
}
