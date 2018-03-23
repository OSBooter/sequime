package pc.javier.seguime.utilidades;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import pc.javier.seguime.R;

/**
 * Created by Usuario NoTeBooK on 21 mar 2018.
 */

public abstract class Boton {
    //cambia el estado del boton
    public static void Estado(Button boton, boolean estado) {
        boton.setEnabled(estado);
        if (estado)
            boton.setBackgroundResource(R.drawable.botonredondeado);
        else
            boton.setBackgroundResource(R.drawable.botonredondeadogris);

    }
}
