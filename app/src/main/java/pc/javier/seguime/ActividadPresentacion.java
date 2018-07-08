package pc.javier.seguime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewAnimator;

import pc.javier.seguime.interfaz.Aplicacion;

/**
 * Created by Usuario NoTeBooK on 7 jul 2018.
 */

public class ActividadPresentacion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentacion);

        Aplicacion.preferenciaBooleano("presentacion", true);
    }



    public void ClickPresentacion (View view) {
        ViewAnimator animacion = ((ViewAnimator)findViewById(R.id.presentacion_contenido));


        int total = animacion.getChildCount();
        int actual = animacion.getDisplayedChild()+1;
        Log.d("Actual:  " , ""+ actual);
        Log.d("total:  " , ""+ total);


        if ( view.getId() == R.id.presentacion_boton_anterior) {
            animacion.setOutAnimation(this,R.anim.derecha_salida);
            animacion.setInAnimation(this,R.anim.derecha_entrada);
            if (actual > 1)
                animacion.showPrevious();
        } else {
            animacion.setOutAnimation(this,R.anim.izquierda_salida);
            animacion.setInAnimation(this,R.anim.izquierda_entrada);
            if (actual < total)
                animacion.showNext();
            else
                this.finish();
        }





    }



}
