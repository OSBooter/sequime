package pc.javier.seguime.adaptador;

import android.app.Activity;
import android.view.View;

import pc.javier.seguime.R;
import utilidades.vista.EditorVistas;

/**
 * Javier 2019.
 * adaptador de "editor de vistas"
 */

public class Pantalla extends EditorVistas {
    public Pantalla(Activity activity) {
        super(activity);
    }
    public Pantalla(View vista) {
        super(vista);
    }

    public String getTexto (int id) {
        return getEditText(id).getText().toString();
    }

    public int entero (String valor) {
        try {
            return Integer.parseInt(valor.trim());
        }
        catch (Exception e) {
            return 0;
        }
    }


    public int cuadroVerdeRedondeando () {
        return R.drawable.botonredondeado;
    }

    public int cuadroGrisRedondeando () {
        return R.drawable.botonredondeadogris;
    }

    public int cuadroRojoRedondeando () {
        return R.drawable.botonredondeadorojo;
    }
}
