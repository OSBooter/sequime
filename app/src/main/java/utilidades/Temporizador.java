package utilidades;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Javier 2019.
 *
 * Temporizador de una o dos tareas
 * ejecuta una tarea por intervalos de tiempo EN SEGUNDOS
 * también puede ejecutar una segunda tarea al finalizar el intervalo (si se establece una duración)
 *
 *  ---- LA OPCIÓN DE RETRASO PUEDE NO FUNCIONAR CON EL TEMPORIZADOR DOBLE (con duración).
 */

public class Temporizador {

    private Timer temporizador;
    private Timer temporizadorDuracion;

    private long retraso  = 0;   // ESTA OPCIÓN PUEDE NO FUNCIONAR CON "DURACIÓN"
    private long intervalo = 60;
    private long duracion = 0;

    public Temporizador () {
        temporizador = new Timer();
        temporizadorDuracion = new Timer();
    }

    public void iniciar () {
        temporizador.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        ejecutarTarea ();
                        ActivarDuracion();
                    }
                }, 1000 * retraso, 1000 * (intervalo + duracion));
    }

    private void ActivarDuracion () {
        if (duracion > 0)
            temporizadorDuracion.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        ejecutarTareaAlFinalizar ();
                    }
                }, 1000* duracion);
    }



    public void detener () {
        temporizador.cancel();
        temporizadorDuracion.cancel();
    }


    public void ejecutarTarea() {

    }

    public void ejecutarTareaAlFinalizar () {

    }


    public long getRetraso() {
        return retraso;
    }

    public void setRetraso(long segundos) {
        this.retraso = segundos;
    }

    public long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(long intervaloSegundos) {
        this.intervalo = intervaloSegundos;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracionSegundos) {
            this.duracion = duracionSegundos;
    }
}
