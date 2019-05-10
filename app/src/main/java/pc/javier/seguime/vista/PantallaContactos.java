package pc.javier.seguime.vista;

import android.app.Activity;
import android.widget.ListView;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Javier 2019.
 */

public class PantallaContactos extends Pantalla {
    public PantallaContactos(Activity activity) {
        super(activity);
    }



    public ListView getLista () {
        return  getListView( R.id.contactos_lista);
    }



}
