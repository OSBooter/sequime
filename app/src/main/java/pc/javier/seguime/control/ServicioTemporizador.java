package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import pc.javier.seguime.interfaz.Aplicacion;


// este sevicio controla el tiempo de actividad e inactividad
// activa y desactiva el serivio "principal"

public class ServicioTemporizador extends Service {

    private Intent servicio;

    private Timer temporizador;

    // lleva la cuenta del temporizador (minutos)
    private int temporizadorContador;

    // fija el valor MAXIMO que debera alcanzar temporizadorContador antes de invertir el estado del servicio
    // tomara valores de actividad/inactividad segun corresponda en el momento que corresponda
    private int temporizadorLimite;

    // configuraciones
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
        detenerServicio();
        detenerTemporizador();
        super.onDestroy();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        iniciarServicio();
        iniciarTemporizador();
        return START_STICKY;
    }




    // reloj interno de este servicio --------------------------------------

    private void iniciarTemporizador () {
        // ESTO DEBERA QUITARSE PARA QUE FUNCIONE ALERTAS
        if (temporizadorLimite <= 0 || actividad <= 0 || inactividad <= 0) {
            mensajeLog ( " no se activara el temporizador (unicamente el servicio principal) " );
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
                            invertirServicio();
                        }
                    }
                }, 000,1000 * 60 );
    }

    private void detenerTemporizador() {
        temporizador.cancel();
    }




    // cambia de estado entre actividad e inactividad
    public void invertirServicio() {

        if (activo == true) {
            if (temporizadorLimite <= 0 || actividad <= 0 || inactividad <= 0 || Aplicacion.alarmaLimite()) {
                mensajeLog ( " temporizador sin efecto - el servicio continuara activo");
                return;
            }
            detenerServicio();

        } else {
            iniciarServicio();
        }
    }



    // inicia y detiene el servicio Principal (Aplicacion) ------------------------
    public void iniciarServicio() {

        temporizadorContador = 0;
        actividad = preferencias.getInt("actividad", 0);
        inactividad = preferencias.getInt("inactividad", 0);
        temporizadorLimite = actividad;
        activo = true;
        startService(servicio);
        mensajeLog ( "INICIADO");
    }

    public void detenerServicio () {
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


