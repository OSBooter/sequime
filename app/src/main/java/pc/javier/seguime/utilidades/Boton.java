package pc.javier.seguime.utilidades;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import pc.javier.seguime.R;

/**
 * Created by Javier on 21 mar 2018.
 *
 * cambia colores y estado de botones
 */

public abstract class Boton {
    //habilita o deshabilita un boton
    public static void Estado(Button boton, boolean estado) {
        boton.setEnabled(estado);
        if (estado)
            color(boton,"verde");
        else
            color(boton,"gris");;
    }


    public static void color(Button boton, String color) {
        if (color == "rojo")
            boton.setBackgroundResource(R.drawable.botonredondeadorojo);
        if (color == "verde")
            boton.setBackgroundResource(R.drawable.botonredondeado);
        if (color == "gris")
            boton.setBackgroundResource(R.drawable.botonredondeadogris);

        Efecto.AnimarBoton(boton);
    }
}
