package utilidades.basico;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


/**
 * Javier 2019.
 * Abre enlaces
 * Usado para abrir páginas en navegadores
 */

public class EnlaceExterno {
    Activity activity;
    public EnlaceExterno(Activity activity) {
        this.activity = activity;
    }

    public void abrirEnlace (String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

}
