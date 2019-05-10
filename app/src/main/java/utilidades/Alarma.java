package utilidades;

import java.util.Date;

import static java.lang.Math.round;

/**
 * Javier 2019.
 */

public class Alarma {
    private Date inicio;
    private Date fin;

    private String mensaje = "Alarma activada";


    public Alarma () {
        inicio = new Date();
    }



    private long diferencia () {
        Date ahora = new Date();
        return  fin.getTime() - ahora.getTime();
    }

    public long getSegundosRestantes() {
        return getMilisegundosRestantes() / 1000;
    }

    public long getMilisegundosRestantes() {
        return diferencia();
    }

    public long getMilisegundosTranscurridos() {
        return (new Date()).getTime() - inicio.getTime();
    }

    public boolean activada () {
        return diferencia() <= 0;
    }




    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setFin(long milisegundos) {
        setFin(new Date(milisegundos));
    }


    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }




    public void setDuracion (long milisegundos) {
        this.fin = new Date (new Date().getTime() + milisegundos);
    }

    public void setDuracion (int horas, int minutos, int segundos) {
        setDuracion(obtenerMilisegundos(horas, minutos, segundos));
    }

    private long obtenerSegundos (int horas, int minutos, int segundos) {
        return (minutos*60) + (horas*60*60) + segundos;
    }

    private long obtenerMilisegundos (int horas, int minutos, int segundos) {
        return obtenerSegundos(horas, minutos, segundos) * 1000;
    }


    public int getHoras () { return round(getSegundosRestantes() / 60 / 60); }
    public int getMinutos () { return round(getSegundosRestantes()/60)%60; }
    public int getSegundos () { return (int) getSegundosRestantes()%60; }



}
