package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


// este sevicio controla el tiempo de actividad e inactividad
// activa y desactiva el serivio "principal"

public class ServicioTemporizador extends Service {

    private Intent servicio;

    private Timer temporizador;
    private int temporizadorContador;
    private int temporizadorLimite;

    private SharedPreferences preferencias;

    private int actividad;
    private int inactividad;


    boolean activo;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        servicio = new Intent(this,Servicio.class);

        preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);

        // crea el temporizador
        temporizador = new Timer();
        temporizadorLimite = 0;


    }


    @Override
    public void onDestroy() {
        detener();
        temporizadorDetener();
        super.onDestroy();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        iniciar();
        temporizadorIniciar();
        return START_STICKY;
    }




    // reloj interno de este servicio --------------------------------------

    private void temporizadorIniciar () {
        if (temporizadorLimite <= 0 || actividad <= 0 || inactividad <= 0) {
            mensajeLog ( " no se activara " );
            return;
        }
        temporizador.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {

                        mensajeLog ( "contador" + temporizadorContador);
                        temporizadorContador = temporizadorContador + 1;
                        if (temporizadorContador > temporizadorLimite) {
                            temporizadorContador = 0;
                            invertirEstado();
                        }
                    }
                }, 000,1000 * 60 );
    }

    private void temporizadorDetener() {
        temporizador.cancel();
    }




    // cambia de estado entre actividad e inactividad
    public void invertirEstado() {

        if (activo == true) {
            if (temporizadorLimite <= 0 || actividad <= 0 || inactividad <= 0) {
                mensajeLog ( " temporizador sin efecto - el servicio continuara");
                //temporizadorDetener();
                return;
            }
            detener();

        } else {
            iniciar();
        }
    }



    // inicia y detiene el servicio Principal (Aplicacion) ------------------------
    public void iniciar() {

        temporizadorContador = 0;
        actividad = preferencias.getInt("actividad", 0);
        inactividad = preferencias.getInt("inactividad", 0);
        temporizadorLimite = actividad;
        activo = true;
        startService(servicio);
        mensajeLog ( "INICIADO");
    }

    public void detener () {
        temporizadorContador = 0;
        temporizadorLimite = inactividad;
        stopService(servicio);
        activo = false;
        mensajeLog ( "DETENIDO");
    }





    private void mensajeLog (String texto) {
        Log.d("Servicio Temporizador", texto);
    }
}


