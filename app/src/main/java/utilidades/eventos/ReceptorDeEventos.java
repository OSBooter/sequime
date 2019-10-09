package utilidades.eventos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by Javier on 13/9/2019.
 * Objeto que se acopla al Tunel de Eventos para interceptar datos arrojados por Bolas de Eventos.
 */

public abstract class ReceptorDeEventos extends Handler {

    protected int id = 0; // Para identificarse entre Receptores

    protected int objetivo = TunelDeEventos.IDENTIFICADOR_GENERICO; // recibirá eventos únicamente de quienes tengan este "identificador"    

    protected String clave = "";
    protected TipoDato tipoDato = TipoDato.CADENA;

    
    
    public void suscribir () {
        TunelDeEventos.suscribir (this);
    }
    
    public void suscribir (int objetivo) {
        this.objetivo = objetivo;
        suscribir ();
    }
    
    public void desuscribir () {
        TunelDeEventos.desuscribir (this);
    }


    // recibe los mensajes por parte de los eventos y obtiene el "dato" (bundle)
    @Override
    public void handleMessage(Message mensaje) {
        // extrae datos del msj
        Bundle bundle = mensaje.getData();
        procesar (bundle);
        procesar ();

        // si no hay clave
        if (clave.isEmpty())
            return;

        switch (tipoDato) {
            case CADENA:
                procesar(bundle.getString(clave));
                break;

            case ENTERO:
                procesar(bundle.getInt(clave));
                break;

            case LISTA:
                procesar((ArrayList) bundle.getSerializable(clave));
                break;

            case PAQUETE:
                procesar(bundle.getBundle(clave));
                break;
        }

    }




    // metodos para procesar los datos recibidos (sobreescribir)
    public void procesar(Bundle dato) {  }
    public void procesar () { }


    // metodos de procesado rápido
    public void procesar (String valor) { }
    public void procesar (int valor) { }
    public void procesar (ArrayList valor) { }




    // gets - sets

    public int getObjetivo () { return  objetivo; }
    public void setObjetivo (int valor) { objetivo = valor; }

    public String getClave () { return  clave; }
    public void setClave (String valor) { clave = valor; }


    public int getId () { return id; }
    public void setId (int valor) { id = valor; }

    public  enum TipoDato { ENTERO, CADENA, PAQUETE, LISTA }




}
