package utilidades;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Javier 2019.
 * Facilita el manejo de cuadro de dialogo y su respuesta
 */

public class Dialogo {

    private Context contexto;
    private AlertDialog.Builder ventana;
    private Respuesta respuesta = Respuesta.negativa;

    public Dialogo(Context contexto) {
        this.contexto = contexto;
        ventana = new AlertDialog.Builder(contexto);
        botonNegativo(android.R.string.no);
        botonPositivo(android.R.string.yes);
    }

    public void mostrar () {
        ventana.show();
    }

    public void  setTitulo (String texto) {
        ventana.setTitle(texto);
    }

    public void setMensaje (int idTexto){
        setMensaje(contexto.getString(idTexto));
    }

    public void  setTitulo (int idTexto) {
        setTitulo(contexto.getString(idTexto));
    }

    public void setMensaje (String texto){
        ventana.setMessage(texto);
    }

    public void botonNegativo (int idTexto) {
        botonNegativo(contexto.getString(idTexto));
    }

    public void botonPositivo (int idTexto) {
        botonPositivo(contexto.getString(idTexto));
    }

    public void botonNegativo(String texto) {
        ventana.setNegativeButton(texto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respuesta = Respuesta.negativa;
                respuestaNegativa();
            }
        });

    }

    public void botonPositivo (String texto) {
        ventana.setPositiveButton(texto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respuesta = Respuesta.positiva;
                respuestaPositiva();
            }
        });
    }

    public Respuesta obtenerRespuesta () {
        return respuesta;
    }

    public void respuestaPositiva () {

    }

    public void respuestaNegativa () {

    }

    public enum Respuesta { positiva, negativa  }
}
