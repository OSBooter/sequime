package pc.javier.seguime.interfaz;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import pc.javier.seguime.R;
import pc.javier.seguime.control.ServicioTemporizador;
import pc.javier.seguime.handler.Comandos;

import java.util.List;



/**
 * metodos que tienen que ver con la configuracion de la aplicacion
 * y el estado en el que se encuentra
 */

public class Aplicacion {


    public static String version = "0.1";



    // las propiedades estaticas son para poder hacer funcionar Handlers en los Servicios
    public static AppCompatActivity actividadPrincipal;
    public static AppCompatActivity actividadSesion;
    public static AppCompatActivity actividadRegistro;
    public static AppCompatActivity actividadAyuda;

    public static Context contexto;
    public static Handler handler;
    public static BD basededatos;
    public static SharedPreferences preferencias;

    private  Intent servicio;


    // constructor
    public Aplicacion (Context contexto, AppCompatActivity actividad) {
        this.contexto = contexto;
        this.actividadPrincipal = actividad;


        //handler
        Comandos comandos = new Comandos(actividad);
        handler = new Handler(comandos);

        // servicio y preferencias
        servicio = new Intent(contexto,ServicioTemporizador.class);
        preferencias = contexto.getSharedPreferences("preferencias",contexto.MODE_PRIVATE);
        cargarConfiguracionInicial();
     }



     // genera una configuracion inicial (Predeterminada)
    private void cargarConfiguracionInicial() {
        mensajeLog ( "veriticando si se carga configuracion inicial....: "  );
        if (preferencias.getBoolean("inicial", true) == false)
            return;

        mensajeLog ( "no hay configuracion previa, se cargaran valores predeterminados" );

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("servidor", "http://javierpc.esy.es/seguime/servicio.php");
        editor.putString("usuario", "demo");
        editor.putString("clave", "1234");

        editor.putInt("actividad", 5);
        editor.putInt("inactividad", 30);
        editor.putString("telegram", "");
        editor.putString("sms", "");

        editor.putBoolean("inicial", false);
        editor.putBoolean("sesion", false);
        editor.commit();
    }





    // Pone en marcha la aplicacion (Servicios)
    public void iniciarServicio() {
        mensajeLog ( "Iniciando Servicio");
        contexto.startService(servicio);
    }

    public void detenerServicio () {

        if (estaBloqueado()) {
            Toast.makeText(contexto, R.string.txt_bloqueado, Toast.LENGTH_LONG).show();
            return;
        }

        contexto.stopService(servicio);
        mensajeLog ("Deteniendo Servicio");
    }



    // verifica si el servicio esta activo
    // recorre la lista de servicios en el sistema en busca de "ServicioTemporizador.class"
    List<ActivityManager.RunningServiceInfo> listaServicios;
    public boolean servicioActivo () {
        // obtiene una lista de servicios
        listaServicios = ((ActivityManager) contexto.getSystemService(contexto.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE);
        // recorre la lista
        for (ActivityManager.RunningServiceInfo s: listaServicios) {
            if(s.service.getClassName().equals(ServicioTemporizador.class.getName()))
                return true;
        }
        return false;
    }





    // verifica si la aplicacion esta configurada como bloqueada o no
    public boolean estaBloqueado() {
        return preferencias.getBoolean("bloqueo", false);
    }


    // verifica si esta hay una sesion iniciada en la aplicacion
    public boolean sesionIniciada() {
        return preferencias.getBoolean("sesion", false);
    }

    // mensajes de notificacion (toast)
    public String notificacion () {return preferencias.getString("notificacion", ""); }

    // mensajes en pantalla
    public String mensaje () {return preferencias.getString("mensaje", ""); }

    public void borrarNotificacion() {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("notificacion");
        editor.commit();
    }
    public void borrarMensaje() {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("mensaje");
        editor.commit();
    }


    public void cerrarSesion () {
        detenerServicio();
        if (estaBloqueado())
            return;
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("sesion");
        editor.commit();
    }





    private void mensajeLog (String texto) {
        Log.d("Aplicacion", texto);
    }

}
