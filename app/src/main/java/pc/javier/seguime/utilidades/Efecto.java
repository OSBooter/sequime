package pc.javier.seguime.utilidades;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import pc.javier.seguime.R;

/**
 * Created by Usuario NoTeBooK on 8 jul 2018.
 */

public abstract class Efecto {

    public static void AnimarIcono (ImageView imagen, int visibilidad) {
            AnimarIcono(imagen, (visibilidad == View.VISIBLE));
    }

    public static void AnimarIcono (ImageView imagen, boolean visible) {
        int visibilidad= 0;
        if (visible)
            visibilidad = View.VISIBLE;
        else
            visibilidad = View.INVISIBLE;

        if (imagen.getVisibility() == visibilidad)
            return;


        imagen.clearAnimation();

        if (imagen.getResources().getConfiguration().orientation == imagen.getResources().getConfiguration().ORIENTATION_LANDSCAPE)
            if (visibilidad == View.VISIBLE)
                imagen.startAnimation(AnimationUtils.loadAnimation(imagen.getContext(), R.anim.abajo_entrada));
            else
                imagen.startAnimation(AnimationUtils.loadAnimation(imagen.getContext(),R.anim.abajo_salida));

        else
            if (visibilidad == View.VISIBLE)
                imagen.startAnimation(AnimationUtils.loadAnimation(imagen.getContext(), R.anim.zoom_atras_entrada));
            else
                imagen.startAnimation(AnimationUtils.loadAnimation(imagen.getContext(),R.anim.zoom_atras_salida));


        imagen.setVisibility(visibilidad);

    }




    public static void AnimarBoton (Button boton) {
        boton.clearAnimation();
        boton.startAnimation(AnimationUtils.loadAnimation(boton.getContext(),R.anim.desvanecer_entrada));

    }


}
