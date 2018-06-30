package pc.javier.seguime.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pc.javier.seguime.handler.Comandos;
import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.BD;
import pc.javier.seguime.interfaz.Coordenada;
import pc.javier.seguime.interfaz.GestorCoordenadas;
import pc.javier.seguime.interfaz.GestorDatos;
import pc.javier.seguime.utilidades.FechaHora;

import static java.lang.Math.round;


/**
 * Created by javier on 17/11/2016.
 * este es el servicio principal, se ejecuta de fondo
 */

public class Servicio extends   Service  {


    private Localizacion posicion;
    private ObservadorLocalizacion observadorLocalizacion;
    private BD basededatos;
    private SharedPreferences preferencias;
    private Timer temporizadorInternet;
    private  int actualizacion;
    private GestorCoordenadas gestor;
    private GestorCoordenadas gestorTemporizador;

    private Timer temporizadorAlarma;
    private GestorDatos alarma;

    private  String alarmaLimite;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        iniciar();
        mensajeLog ("Iniciado");

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        detener();
        mensajeLog ( "DESTRUIDO");
    }




    @Override
    public void onCreate() {
        super.onCreate();

        // carga la base de datos
        basededatos = new BD(this);

        // localizador
        posicion = new Localizacion(this);

        // reloj que envia datos por internet
        temporizadorInternet = new Timer();

        // reloj que comprueba alarmas
        temporizadorAlarma = new Timer();

        mensajeLog ( "Servicio Creado");

        Aplicacion.basededatos = basededatos;
        return;
    }






    public void iniciar() {

        // base de datos
        basededatos.abrir();

        // obtiene configuracion de preferencias
        preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);

        // intervalo de tiempo en el que se conecta a internet
        actualizacion = preferencias.getInt("actualizacion", 5);

        // crea un manejador de coordenadas
        gestor = new GestorCoordenadas(basededatos, preferencias);

        // crea un manejador de coordenadas para el temporizador
        gestorTemporizador = new GestorCoordenadas(basededatos,preferencias);

        // carga los observadores
        observadorLocalizacion = new ObservadorLocalizacion(gestor);

        // carga el localizador
        posicion.addObserver(observadorLocalizacion);
        posicion.activar();

        // inicia el temporizador que envia datos por internet
        temporizadorInternetIniciar();

        // inicia temporizador de alarmas
        temporizadorAlarmaIniciar();




        mensajeLog ( "INICIADO");
    }

    public void detener() {
        // destruye todo
        posicion.desactivar();
        posicion.deleteObservers();
        basededatos.cerrar();
        temporizadorInternetDetener();
        temporizadorAlarmaDetener();
        gestor = null;
        mensajeLog ( "DETENIDO");
    }







    // ALARMA ---------------------------------

    private void temporizadorInternetIniciar () {

        temporizadorInternet.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        if(Aplicacion.contexto == null) {
                            Aplicacion.contexto = getApplicationContext();
                            mensajeLog("aplicacion sin contexto");
                        }

                        mensajeLog ( " Enviando coordenadas almacenadas" );
                        ArrayList<Coordenada> lista = basededatos.coordenadaObtenerNuevas("5");
                        alarma = new GestorDatos();
                        alarma.enviarAlarma();
                        if (lista.size()>0)
                            for (Coordenada coordenada : lista) {
                                gestorTemporizador.setCoordenada(coordenada);
                                gestorTemporizador.enviarServidor();
                            }
                        else
                            gestorTemporizador.enviarNada();

                    }
                }, 3000, 1000 * 60 * actualizacion);
    }

    private void temporizadorInternetDetener () {
        temporizadorInternet.cancel();
    }






    private void temporizadorAlarmaIniciar () {
        // intervalo de tiempo en el que se activa la alarma
        alarmaLimite = preferencias.getString("alarma", "");
        temporizadorAlarma = new Timer();

        long intervalo = FechaHora.intervalo(alarmaLimite);
        int hora = (int) round(intervalo/60/60);
        int minuto = (int) round(intervalo/60)%60;
        int segundo = (int) intervalo%60;


        int limite =(int) intervalo;
        if (alarmaLimite.equals(""))
            return;
        try {
            limite = Integer.parseInt(alarmaLimite);
        } catch (Exception e) {}

        if (limite <= 0)
            limite = 1;

        mensajeLog("SERVICIO - ALARMA limite -> " + alarmaLimite);
        mensajeLog("SERVICIO - ALARMA limite -> " + limite);
        temporizadorAlarma.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        comprobarAlarma();


                    }
                }, 1, 1000 * limite);
    }

    private void temporizadorAlarmaDetener () {
        temporizadorAlarma.cancel();
    }


    // oomprueba si la alarma esta *ACTIVADA*
    private void comprobarAlarma() {
        if(Aplicacion.contexto == null) {
            Aplicacion.contexto = getApplicationContext();
            mensajeLog("aplicacion sin contexto");
        }



            mensajeLog("comprobando alarma");
        if (Aplicacion.alarmaExiste())
            mensaje("vista", "alarma");

        // comprueba si la alarma llego al limite
        if (Aplicacion.alarmaLimite()) {

            mensajeLog("ALARMA ACTIVADA");
            // envia mensaje de arlerta
            String numero = Aplicacion.preferenciaCadena("sms");
            String texto = Aplicacion.preferenciaCadena("alarmatexto");
            Sms sms = new Sms(numero, texto);
            sms.enviar();

            // pone el sistema en modo "rastreo"
            // esto hace que envie mensajes y quede permanentemente activo
            Aplicacion.preferenciaBooleano("rastreo", true);

            // bloquea el sistema¿?

            // QUITA LA ALARMA (asi no se vuelve a repetir esto)
            Aplicacion.preferenciaCadena("alarma", "");
            //
            mensaje("vista", "actualizar");
            mensaje("vista", "alarma");

        }

    }





    // comunica a la aplicacion eventos ocurridos
    private void mensaje (String clave, String texto) {
        Handler handler = Aplicacion.handler;

        if (handler == null) {
            mensajeLog("SIN HANDLER");
            return;
        }

        mensajeLog("(handler) " + clave +"-"+ texto);

        Message mensaje = new Message();
        Bundle dato = new Bundle();
        dato.putString(clave, texto);
        mensaje.setData(dato);

        handler.sendMessage(mensaje);

    }


    private void mensajeLog (String texto) {
        Log.d("Servicio Principal", texto);
    }

}
