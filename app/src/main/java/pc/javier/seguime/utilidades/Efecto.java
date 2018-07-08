package pc.javier.seguime.utilidades;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
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
                imagen.setAnimation(AnimationUtils.loadAnimation(imagen.getContext(), R.anim.abajo_entrada));
            else
                imagen.setAnimation(AnimationUtils.loadAnimation(imagen.getContext(),R.anim.abajo_salida));

        else
            if (visibilidad == View.VISIBLE)
                imagen.setAnimation(AnimationUtils.loadAnimation(imagen.getContext(), R.anim.izquierda_entrada));
            else
                imagen.setAnimation(AnimationUtils.loadAnimation(imagen.getContext(),R.anim.izquierda_salida));

        imagen.animate();
        imagen.setVisibility(visibilidad);

    }




    public static void AnimarBoton (View view, int visibilidad) {

    }


}
