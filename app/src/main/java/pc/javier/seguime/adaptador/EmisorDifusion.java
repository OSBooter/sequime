package pc.javier.seguime.adaptador;

import android.content.Context;

/**
 * Javier 2019.
 */

public class EmisorDifusion extends utilidades.basico.EmisorDifusion {

    public EmisorDifusion(Context contexto) {
        super(contexto);
        setPrefijo("seguime");
    }



    public void emitirCamara () { emitir( "CAMARA_ESPIA"); }
    public void emitirWifi   () { emitir( "WIFI_AUTOMAGICO"); }
}
