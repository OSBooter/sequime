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
import pc.javier.seguime.utilidades.FechaHora;

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
    public static AppCompatActivity actividadRegistros;
    public static AppCompatActivity actividadAyuda;
    public static AppCompatActivity actividadRegresiva;

    public static Context contexto;
    public static Handler handler;
    public static BD basededatos;
    private static SharedPreferences preferencias;

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
        if (Preferencias().getBoolean("inicial", true) == false)
            return;

        mensajeLog ( "no hay configuracion previa, se cargaran valores predeterminados" );

        SharedPreferences.Editor editor = Preferencias().edit();

        editor.putString("servidor", "http://javierpc.esy.es/seguime/servicio.php");
        editor.putString("usuario", "");
        editor.putString("clave", "");

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

        if (!sesionIniciada()) {
            mensajeLog ( "Se intento iniciar la aplicacion sin sesion :D");
            return;
        }

        // Toast.makeText(contexto, contexto.getString(R.string.principal_aplicacion) + " \uD83D\uDCE1", Toast.LENGTH_LONG).show();
        preferenciaBooleano("activa", true);
        mensajeLog ( "Iniciando Servicio");
        if (servicioActivo())
            return;
        contexto.startService(servicio);
    }

    public void detenerServicio () {

        if (estaBloqueado()) {
            Toast.makeText(contexto, R.string.txt_bloqueado, Toast.LENGTH_LONG).show();
            return;
        }

        if (alarmaExiste()) {
            Toast.makeText(contexto, R.string.txt_bloqueado_alarma, Toast.LENGTH_LONG).show();
            return;
        }

        contexto.stopService(servicio);
        mensajeLog ("Deteniendo Servicio");

        preferenciaBooleano("activa", false);
    }


    public  void ReiniciarServicio () {
        if (servicioActivo())
            contexto.stopService(servicio);
        iniciarServicio();
        preferenciaBooleano("activa", true);
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
        return Preferencias().getBoolean("bloqueo", false);
    }


    // verifica si esta hay una sesion iniciada en la aplicacion
    public boolean sesionIniciada() {
        return Preferencias().getBoolean("sesion", false);
    }

    // mensajes de notificacion (toast)
    public String notificacion () {return Preferencias().getString("notificacion", ""); }

    // mensajes en pantalla
    public String mensaje () {return Preferencias().getString("mensaje", ""); }

    public void borrarNotificacion() {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.remove("notificacion");
        editor.commit();
    }
    public void borrarMensaje() {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.remove("mensaje");
        editor.commit();
    }


    public void cerrarSesion () {
        detenerServicio();
        if (estaBloqueado())
            return;
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.remove("servidor");
        editor.remove("sesion");
        editor.remove("usuario");
        editor.remove("clave");
        editor.remove("telegram");
        editor.remove("sms");
        editor.remove("presentacion");
        editor.remove("notificacion");
        editor.remove("mensaje");
        editor.remove("inicial");

        editor.commit();

        new BD(contexto).coordenadaEliminar();
    }



    public static void preferenciaCadena (String opcion, String valor) {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.putString(opcion, valor);
        editor.commit();
    }
    public static String preferenciaCadena (String opcion) {
        return Preferencias().getString(opcion,"");
    }


    public static void preferenciaBooleano (String opcion, boolean valor) {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.putBoolean(opcion, valor);
        editor.commit();
    }
    public static boolean preferenciaBooleano (String opcion) {
        return Preferencias().getBoolean(opcion,false);
    }



    // verifica si el temporizador existe y llego al final
    public static boolean alarmaLimite() {
        String temporizador = Preferencias().getString("alarma", "");
        if (temporizador == "")
            return false;
        long intervalo = FechaHora.intervalo(temporizador);
        if (intervalo <= 0)
            return true;
        return false;
    }

    public static String alarma() {
        return  Preferencias().getString("alarma", "");
    }
    public static void alarma(String valor) {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.putString("alarma", valor);
        editor.commit();
    }
    public static boolean alarmaExiste() {
        return (Preferencias().getString("alarma", "")!="");
    }


    // copia de la alarma en el servidor

    public static String alarmaServidor() {
        return  Preferencias().getString("alarmaservidor", "");
    }
    public static void alarmaServidor(String valor) {
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.putString("alarmaservidor", valor);
        editor.commit();
    }


    // preferencias

    public static boolean rastreo (){
        return Preferencias().getBoolean("rastreo", false);
    }
    public static void rastreo (boolean valor){
        SharedPreferences.Editor editor = Preferencias().edit();
        editor.putBoolean("rastreo", valor);
        editor.commit();
    }

    public static String Usuario () {
        return preferenciaCadena("usuario");
    }

    public static boolean Registrada () {
        return (preferenciaCadena("registrada")!="");
    }
    public static void Registrada (String nombre) {
        preferenciaCadena("registrada", nombre);
    }

    public static SharedPreferences Preferencias() {

        if (preferencias == null)
            preferencias = contexto.getSharedPreferences("preferencias",contexto.MODE_PRIVATE);
        return  preferencias;

    }







    private void mensajeLog (String texto) {
        Log.d("Aplicacion", texto);
    }








}
