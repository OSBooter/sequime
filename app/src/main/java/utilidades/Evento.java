package utilidades;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;


/**
 * Javier 2019.
 */

public class Evento {


    private String clave = "evento";      // hace metodos más sencillos
    private Bundle datos = new Bundle();
    private ArrayList<Handler> listaHandlers = new ArrayList<Handler>();


    public  Evento (Handler handler, String clave) {
        this.clave = clave;
        agregarHandler(handler);
    }

    public  Evento (Handler.Callback receptor, String clave) {
        this.clave = clave;
        agregarReceptor(receptor);
    }

    public  Evento (ReceptorEventos receptor) {
        agregarReceptor(receptor);
        this.clave = receptor.getClave();
    }

    public  Evento (Handler handler) {
        agregarHandler(handler);
    }

    public  Evento (String clave) { this.clave = clave; }

    public Evento () {}



    // receptores  -----------------------

    public void agregarReceptor (Handler.Callback receptor) {
        Handler handler = new Handler(receptor);
        agregarHandler(handler);
    }
    public void agregarHandler (Handler handler) {
        listaHandlers.add(handler);
    }
    public void quitarHandlers () {
        listaHandlers.clear();
    }



    // emisores -----------------------

    public void emitir (String clave, String valor) {
        mensajeLog("emitiendo | clave: " + clave  + " valor: "+ valor);
        agregarDato(clave, valor);
        emitir();
    }


    public void emitir (String valor) {
        emitir(clave, valor);
    }

    public void emitir () {
        // recorre la lista de Receptores y emite un mensaje para cada receptor
        // (si se emite un mismo mensaje para varios receptores, se cuelga sin dar error)
        for (Handler handler: listaHandlers) {
            Message mensaje = new Message();
            mensaje.setData(datos);
            handler.sendMessage(mensaje);
        }

        // crea un nuevo bundle para próximo uso
        datos = new Bundle();
        mensajeLog("emitido: " + clave);
    }



    // agregado de datos  -----------------------

    public void agregarDato (String clave, String valor) {
        datos.putString(clave, valor);
    }

    public void agregarDato (String clave, Double valor) {
        datos.putDouble(clave, valor);
    }

    public void agregarDato (String clave, ArrayList valor) {
        datos.putSerializable(clave, valor);
    }

    public void agregarDato (ArrayList valor) {
        datos.putSerializable(clave, valor);
    }







    public String clave() {return  clave; }
    public void setClave (String clave) { this.clave = clave; }


    private void mensajeLog (String texto) {
        Log.d("Emisor de Eventos -> ", texto);
    }

}
