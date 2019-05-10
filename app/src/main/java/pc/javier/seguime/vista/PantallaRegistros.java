package pc.javier.seguime.vista;

import android.app.Activity;
import android.widget.ListView;

import org.osmdroid.views.MapView;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.Pantalla;

/**
 * Javier 2019.
 */

public class PantallaRegistros extends Pantalla {
    public PantallaRegistros(Activity activity) {
        super(activity);
    }



    public MapView mapa() {
        return (MapView) getView(R.id.registros_mapa);
    }


    public ListView lista () {
        return (ListView) getView(R.id.registros_lista);
    }

}
