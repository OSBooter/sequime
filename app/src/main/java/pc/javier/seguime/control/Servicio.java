package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.BD;
import pc.javier.seguime.interfaz.Coordenada;
import pc.javier.seguime.interfaz.GestorCoordenadas;


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

        mensajeLog ( "INICIADO");
    }

    public void detener() {
        // destruye todo
        posicion.desactivar();
        posicion.deleteObservers();
        basededatos.cerrar();
        temporizadorInternetDetener();
        gestor = null;
        mensajeLog ( "DETENIDO");
    }








    private void temporizadorInternetIniciar () {

        temporizadorInternet.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {

                        mensajeLog ( " Enviando coordenadas almacenadas" );
                        ArrayList<Coordenada> lista = basededatos.coordenadaObtenerNuevas("5");
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

    private void mensajeLog (String texto) {
        Log.d("Servicio Principal", texto);
    }

}
