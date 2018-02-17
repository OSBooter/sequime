package pc.javier.seguime.control;

import android.util.Log;

import pc.javier.seguime.interfaz.BD;
import pc.javier.seguime.interfaz.Coordenada;
import pc.javier.seguime.interfaz.GestorCoordenadas;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Javier on 27/11/2016.
 * se activa cuando se detectan nuevas coordenadas en el localizador
 */
public class ObservadorLocalizacion implements Observer {


    Coordenada coordenada;
    GestorCoordenadas gestor;

    public ObservadorLocalizacion(GestorCoordenadas gestor) {
        this.gestor = gestor;
    }

    @Override
    public void update(Observable observable, Object o) {
        mensajeLog ( "evento ejecutanose...");
        // si es una coordenada, asume que fue generada por el logalizador
        // la almacena en la base de datos;
        if  (o  instanceof Coordenada) {
            coordenada = (Coordenada) o;
        } else {
            return;
        }

        mensajeLog ( "es una coordenada");
        gestor.setCoordenada(coordenada);
        gestor.insertarBdD();
        gestor.enviarServidor();
        gestor.enviarNotificaciones();
    }



    private void mensajeLog (String texto) {
        Log.d("Observador localizacion", texto);
    }
}
