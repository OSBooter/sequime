package utilidades.basico;

import android.content.Context;
import android.content.Intent;
/**
 * Javier 2019.
 * Editado 13 / 07 / 19
 */

public class EmisorDifusion {

    protected Context contexto;
    protected String firma = "pc.javier";
    protected String prefijo = "aplicacion";
    protected Intent intent = new Intent ();

    public EmisorDifusion (Context contexto) {
        this.contexto = contexto;
    }

    public void emitir (String nombre) {
        intent.setAction(firma + "." + prefijo + "." + nombre);
        contexto.sendBroadcast(intent);
    }

    public void extra (String nombre, String dato) {
        intent.putExtra (nombre, dato);
    }

    public void setPrefijo (String prefijo) {
        this.prefijo =  prefijo ;
    }
}
