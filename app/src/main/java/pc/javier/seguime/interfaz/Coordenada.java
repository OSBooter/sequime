package pc.javier.seguime.interfaz;

import android.util.Log;

import java.util.Observable;

/**
 * Created by javier on 19/11/2016.
 */
public class Coordenada  {
    private String latitud;
    private String longitud;
    private String fecha;
    private String extra;
    private int id;
    private int recibido;

    public Coordenada() {
        id=-1;
        recibido=0;
    }

    public Coordenada (String fecha, String latitud, String longitud, String extra) {
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
        this.extra = extra;
        id = -1;
        recibido=0;
    }

    public Coordenada (String fecha, double latitud, double longitud, String extra) {
        this.fecha = fecha;
        this.latitud = String.valueOf(latitud);
        this.longitud = String.valueOf(longitud);
        this.extra = extra;
        id = -1;
        recibido=0;
    }




    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getExtra() {
        return extra;
    }

    public int getId() { return id; }

    public int getRecibido () { return recibido; }

    public void setId (int id) { this.id = id; }

    public void setRecibido (int valor) {recibido = valor; }

}
