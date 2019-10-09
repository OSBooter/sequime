package utilidades.eventos;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by Javier on 13/9/2019.
 * Contiene una lista de Receptores suscriptos a quienes emitirá uno por uno Eventos sucedidos (Bolas de Eventos)
 */

public class TunelDeEventos {        
    public final static int IDENTIFICADOR_GENERICO = 0;                // para ahorrar envio de mensajes
    public final static String CLAVE_GENERICA = "EVENTO";                // clave predeterminada, para cuando no es necesario una clave (recupera el bundle)




    private static ArrayList<Handler> listaHandlers = new ArrayList<Handler>();
    private static ArrayList<ReceptorDeEventos> listaReceptores = new ArrayList<ReceptorDeEventos>();





    // suscriptores

    public static void suscribir (ReceptorDeEventos receptor) {

        // impide la suscripción de Receptores ya presentes
        if (listaReceptores.contains(receptor) == true)
            return;

        if (receptor.getId() != 0)
            for (ReceptorDeEventos r: listaReceptores)
                if (r.getId() == receptor.getId())
                    if (r.getObjetivo() == receptor.getObjetivo())
                        if (r.getClave() == receptor.getClave())
                            return;
        listaReceptores.add(receptor);
    }


    public static void suscribir(Handler.Callback receptor) {
        Handler handler = new Handler(receptor);
        suscribir (handler);
    }

    public static void suscribir (Handler handler) {
        if (listaHandlers.contains(handler))
            return;
        listaHandlers.add(handler);
    }


    public static void borrarTodo () {
        listaHandlers.clear();
        listaReceptores.clear();
    }

    public static void desuscribir (ReceptorDeEventos receptor) {
        listaReceptores.remove(receptor);
    }

    public static void desuscribir (Handler handler) {
        listaHandlers.remove(handler);
    }







    // Emisión de eventos

    private static void emitir (Handler receptor, BolaDeEventos bola) {
        Message mensaje = new Message();
        mensaje.setData(bola.getBundle());
        receptor.sendMessage(mensaje);
    }


    public static void lanzar (BolaDeEventos bola) {
        // recorre la lista de receptores y emite un mensaje para cada receptor
        // El receptor recibe el mensaje si es genérico o si coincide con el identificador del evento
        for (ReceptorDeEventos receptor : listaReceptores)
            if ((receptor != null))
                if (receptor.getObjetivo() == bola.getIdentificador()
                || (receptor.getObjetivo() == IDENTIFICADOR_GENERICO))
                    emitir (receptor, bola);



        // recorre la lista de handlers y emite un mensaje para cada receptor
        for (Handler handler: listaHandlers)
            if (handler != null)
                emitir(handler, bola);


    }









    // Emisiones rápidas.

    public static void lanzar (String dato) {
        lanzar(IDENTIFICADOR_GENERICO, CLAVE_GENERICA, dato);
    }

    public static void lanzar (String clave, String dato) {
        lanzar(IDENTIFICADOR_GENERICO, clave, dato);
    }

    public static void lanzar (int identificador, String clave, String dato) {
        BolaDeEventos bola = new BolaDeEventos();
        bola.agregarDato(clave, dato);
        bola.setIdentificador(identificador);
        lanzar(bola);
    }

    public static void lanzar (int identificador, String clave, int dato) {
        BolaDeEventos bola = new BolaDeEventos();
        bola.agregarDato(clave, dato);
        bola.setIdentificador(identificador);
        lanzar(bola);
    }



}
