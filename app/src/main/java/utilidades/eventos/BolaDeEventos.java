package utilidades.eventos;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Javier on 13/9/2019.
 * Objeto que se arroja al Tunel de Eventos para ser interceptado por Receptores
 */

public class BolaDeEventos {
    private int identificador = TunelDeEventos.IDENTIFICADOR_GENERICO;
    private Bundle datos = new Bundle();
    

    // agregado de datos  -----------------------

    public void agregarDato (String clave, String valor) {
        datos.putString(clave, valor);
    }

    public void agregarDato (String clave, double valor) {
        datos.putDouble(clave, valor);
    }

    public void agregarDato (String clave, float valor) {
        datos.putFloat(clave, valor);
    }

    public void agregarDato (String clave, int valor) {
        datos.putInt (clave, valor);
    }

    public void agregarDato (String clave, ArrayList valor) {
        datos.putSerializable(clave, valor);
    }


    public Bundle getBundle () {
        return datos;
    }
    
    public void setBundle (Bundle valor) {
        datos = valor;
    }

    
    
    public int getIdentificador () { return identificador; }
    public void setIdentificador (int valor) { identificador = valor; }


    public void lanzar () {
        TunelDeEventos.lanzar(this);
    }
    
    
}
