package pc.javier.seguime.vista;

import android.app.Activity;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Created by Javier on 21/04/2019.
 */

public class PantallaClave extends Pantalla {
    public PantallaClave(Activity activity) {
        super(activity);
    }

    public String getClave () {
        return getTexto(R.id.clave_clave);
    }

    public String getNombre () {
        return getTexto(R.id.clave_nombre);
    }
}
