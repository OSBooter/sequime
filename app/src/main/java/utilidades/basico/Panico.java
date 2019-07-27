package utilidades.basico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Javier on 26/7/2019.
 */



    public abstract class Panico extends Activity {



        public static final String GATILLO_PANICO = "info.guardianproject.panic.action.TRIGGER";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



            Intent intent = getIntent();

            Log.d ("Pánico Recibido", intent.getAction());

            if (intent != null && GATILLO_PANICO.equals(intent.getAction())) {
                Log.d ("Pánico Activado", intent.getAction());
                activar();
            }

			finish ();
        }


        public abstract void activar () ;

    }







