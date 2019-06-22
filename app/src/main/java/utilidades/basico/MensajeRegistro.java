package utilidades.basico;

import android.util.Log;

/**
 * Javier 2019.
 * Se usa para observar mensajes y encontrar errores en programas
 */

public class MensajeRegistro {
    private String clave = ":::: REGISTRO :::: ";

    public MensajeRegistro(String clave) {
        this.clave = clave;
    }

    public void mensaje(String texto) { msj (clave, texto);  }
    public void mensaje(String clave, String texto) { msj (clave, texto);  }




    public static void msj(String clave, double valor) {
        msj (clave, String.valueOf(valor));
    }
    public static void msj(String clave, long valor) { msj (clave, String.valueOf(valor)); }
    public static void msj(String texto) { msj("REGISTRO ::::: ", texto);  }
    public static void msj(long texto) { msj("REGISTRO::::: ", String.valueOf(texto));  }
    public static void msj(Object objecto,  String texto) { msj(objecto.getClass().getSimpleName(), String.valueOf(texto));  }

    public static void msj(String clave, String texto) {
        Log.d(clave, texto);
    }

}
