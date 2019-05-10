package utilidades;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Javier 2019.
 * Recibe mensajes de objetos de tipo Evento
 *
 */

public abstract class ReceptorEventos implements Handler.Callback {

    protected String clave = "generico";

    public ReceptorEventos(String clave) {
        this.clave = clave;
    }



    // recine los mensajes por parte de los eventos y obtiene el "dato" (bundle)
    @Override
    public boolean handleMessage(Message mensaje) {
        // extrae datos del msj
        Bundle bundle = mensaje.getData();
        clasificarDato (bundle);
        return false;
    }





    // filtra y obtiene los mensajes según la clave
    // se puede sobreescribir para clasificar otro tipo de mensajes
    protected void clasificarDato (Bundle bundle) {
        // verifica si es un string
        String dato = extraerString (bundle);
        if (dato != null)
            procesar(dato);

        // verifica si es una lista
        ArrayList lista = extraerLista(bundle);
        if (lista!= null)
            procesar(lista);

    }


    protected String extraerString (Bundle bundle) {
        try { return bundle.getString(clave); }
        catch (Exception e) { return  null; }
    }

    protected ArrayList extraerLista (Bundle bundle) {
        try { return (ArrayList) bundle.getSerializable(clave); }
        catch (Exception e) { return null; }
    }




    // metodos para procesar los datos recibidos (sobreescribir)
    public void procesar(String dato) { };
    public void procesar(ArrayList dato) { };



    // gets - sets
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }


}
