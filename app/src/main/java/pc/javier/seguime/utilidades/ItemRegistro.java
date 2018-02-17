package pc.javier.seguime.utilidades;

/**
 * Created by javier on 20/11/2016.
 */
public class ItemRegistro {
    public String latitud;
    public String longitud;
    public String fecha;
    public String extra;
    public int enviado;
    public int recibido;
    public int id;

    public ItemRegistro (String registros_latitud, String registros_longitud, String fecha, String extra) {
        this.latitud = registros_latitud;
        this.longitud = registros_longitud;
        this.fecha= fecha;
        this.extra = extra;
        enviado = 0;
        recibido =0;
    }


    public ItemRegistro (String registros_latitud, String registros_longitud) {
        this.latitud = registros_latitud;
        this.longitud = registros_longitud;
        fecha= "sin fecha";
        this.extra = "sin informacion extra";
        enviado = 0;
        recibido=0;
    }






    public  ItemRegistro () {
        latitud="0";
        longitud="0";
        fecha= "sin fecha";
        this.extra = "sin informacion extra";
        enviado = 0;
        recibido=0;
    }

}
