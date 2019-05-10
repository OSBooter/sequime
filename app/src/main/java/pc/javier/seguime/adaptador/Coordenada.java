package pc.javier.seguime.adaptador;


import java.util.Date;

import utilidades.localizacion.EnlaceOSM;

/**
 * Javier 2019.
 */
public class Coordenada  extends utilidades.localizacion.Coordenada {

    private int id = -1;
    private boolean recibido = false;
    private Date fechaHoraEnviado;
    private EnlaceOSM enlaceOSM = null;


    public Coordenada() {

    }



    public Coordenada (double latitud, double longitud) {
        super(latitud, longitud);

    }



    public String obtenerEnlaceGPS () {
        String enlace = "geo:"
                +latitud
                +","
                +longitud
                +"?z=19";
        return enlace;
    }

    public String obtenerEnlaceOSM () {
        if (enlaceOSM == null)
            generarEnlaceOSM();
        return enlaceOSM.url();
    }

    public String obtenerEnlaceOSM (int acercamiento) {
        if (enlaceOSM == null)
            generarEnlaceOSM();
        enlaceOSM.establecerAcercamiento(acercamiento);
        return enlaceOSM.url();
    }

    public  void generarEnlaceOSM () {
        enlaceOSM = new EnlaceOSM(latitud, longitud);
    }


    public int getId() { return id; }

    public boolean getRecibido () { return recibido; }

    public void setId (int id) { this.id = id; }

    public void setRecibido (boolean valor) {recibido = valor; }

    public Date getFechaHoraEnviado() { return fechaHoraEnviado; }

    public void setFechaHoraEnviado(Date fechaHoraEnviado) {  this.fechaHoraEnviado = fechaHoraEnviado;    }



}
