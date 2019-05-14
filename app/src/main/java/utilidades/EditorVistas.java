package utilidades;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Javier 2019.
 * Facilita el control de objetos de pantalla
 * Es para utilizar principalmente por receptores de eventos
 *
 */

public class EditorVistas {

    protected Activity activity;
    protected View cache;
    private int idCache = -1;


    protected View vistaPrincipal;

    // permite control total
    public EditorVistas (Activity activity) {
        this.activity  =activity;
    }

    // permite manejo de elementos dentro de una vista especifica (ej: listas),
    // findViewById se ejecuta detro de una vista (ej: elemento)
    public EditorVistas (View  vista) {
        this.vistaPrincipal = vista;
    }





    // gets

    public ToggleButton getToggle (int id) { return (ToggleButton) getView(id); }
    public RadioButton getRadio (int id) { return (RadioButton) getView(id); }
    public EditText getEditText (int id) { return (EditText) getView(id); }
    public TextView getTextView (int id) { return (TextView) getView(id); }
    public ImageView getImageView (int id) { return (ImageView) getView(id); }
    public Button getButton (int id) { return (Button) getView(id); }
    public NumberPicker getNumberPicker (int id) { return (NumberPicker) getView(id); }
    public ListView getListView (int id) { return (ListView) getView(id); }


    public View getView (int id) {
        if (id == idCache)
            return cache;
        idCache = id;
        cache = obtenerView(id);
        return cache;
    }

    private View obtenerView (int id) {
        if (vistaPrincipal != null)
            return vistaPrincipal.findViewById(id);
        if (activity != null)
            return activity.findViewById(id);

        // evita el error "null"
        // if (vistaPrincipal != null) return new View(vistaPrincipal.getContext());
        // if (activity != null) return new View(activity);

        return null;

    }


    // set ( id - valor )

    public void setToggle (int id, boolean chequeado) {
        ToggleButton toggleButton = getToggle(id);
        if (toggleButton!= null)
            toggleButton.setChecked(chequeado);
    }

    public void setRadio (int id, boolean chequeado) {
        RadioButton radioButton = getRadio(id);
        if (radioButton!= null)
            radioButton.setChecked(chequeado);
    }

    public void setEditText (int id, String texto) {
        EditText editText = getEditText(id);
        if (editText != null)
            editText.setText(texto);
    }

    public void setTextView (int id, String texto) {
        TextView textView = getTextView(id);
        if (textView != null)
            textView.setText(texto);
    }

    public void setTextView (int id, int idTexto) {
        TextView textView = getTextView(id);
        if (textView != null)
            textView.setText(getString(idTexto));
    }

    public void setNumberPicker (int id, int valor) {
        NumberPicker numberPicker = getNumberPicker(id);
        if (numberPicker != null)
            numberPicker.setValue(valor);
    }


    public void setButtonText (int id, String texto) {
        Button boton = getButton(id);
        if (boton != null)
            boton.setText(texto);
    }


    public void setButtonText (int id, int idTexto) {
        Button boton = getButton(id);
        if (boton != null)
            boton.setText(getString(idTexto));
    }



    // setview ( view, valor )

    public void setView (EditText view, String texto) {
        if (view != null)
            view.setText(texto);
    }

    public void setView (TextView view, String texto) {
        if (view != null)
            view.setText(texto);
    }

    public void setView (NumberPicker view, int valor) {
        if (view != null)
            view.setValue(valor);
    }

    public void setView (View view, int visibilidad) {
        if (view != null)
            view.setVisibility(visibilidad);
    }




    // vista principal ------------
    public View getVistaPrincipal() {
        return vistaPrincipal;
    }

    public void setVistaPrincipal(View vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
    }
    public void setVistaPrincipal(int id) {
        this.vistaPrincipal = getView(id);
    }


    // visibilidad

    public void setVisibilidad (int id, int visibilidad) {
        View view = getView(id);
        if (view != null)
            view.setVisibility(visibilidad);
    }

    public int visible () {
        return View.VISIBLE;
    }

    public int invisible () {
        return View.INVISIBLE;
    }

    public int visibilidad (boolean visible ) {
        if (visible)
            return View.VISIBLE;
        else
            return View.INVISIBLE;
    }

    public void setVisible (int id) {
        setVisibilidad(id, visible());
    }

    public void setInvisible (int id) {
        setVisibilidad(id, invisible());
    }



    public void setHabilitado (int id, boolean visibilidad) {
        View view = getView(id);
        if (view != null)
            view.setEnabled(visibilidad);
    }





    // rotación - inclinación

    public boolean estaModoPaisaje (View view) {
        if (view != null)
            return (view.getResources().getConfiguration().ORIENTATION_LANDSCAPE == inclinacionDePantalla(view));
        return false;
    }

    public boolean estaModoPortarretrato (View view) {
        if (view != null)
            return (view.getResources().getConfiguration().ORIENTATION_LANDSCAPE == inclinacionDePantalla(view));
        return true;
    }

    public int inclinacionDePantalla (View view) {
        if (view != null)
            return view.getResources().getConfiguration().orientation;
        return 0;
    }



    // bocadillo

    public void snack(int idString) {
        snack(getString(idString));
    }

    public void snack(String mensaje) {

        if (vistaPrincipal != null) {
            Snackbar.make(vistaPrincipal, mensaje, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (activity != null)
            if (activity.getCurrentFocus() != null) {
                Snackbar.make(activity.getCurrentFocus(), mensaje, Snackbar.LENGTH_LONG).show();
                return;
            }




        toast (mensaje);
    }

    public void  toast (String mensaje) {
        if (activity != null) {
            Toast.makeText(activity, mensaje, Toast.LENGTH_LONG).show();
            return;
        }

        if (vistaPrincipal != null) {
            Toast.makeText(vistaPrincipal.getContext(), mensaje, Toast.LENGTH_LONG).show();
            return;
        }
    }






    public Activity getActivity () {
        return activity;
    }

    public String getString (int id) {
        if (activity != null)
            return activity.getString(id);
        return "";
    }




    public TextView getTextView () { return (TextView) cache; }
    public ImageView getImageView () { return (ImageView) cache; }
    public Button getButton () { return (Button) cache; }
    public NumberPicker getNumberPicker () { return (NumberPicker) cache; }
    private View getView () { return cache; }


    /*
    public void salir (View v) {
        // este codigo CIERRA LA APLICACION
        // fuente: https://jcristoballopez.wordpress.com/2015/03/20/anadir-boton-de-salida-con-android-studio/
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    */


    public void finalizarActividad () {
        if (activity != null)
            activity.finish();
    }


    // oolores
    public int colorGris () { return Color.GRAY; }
    public int colorGrisOscuro () { return Color.DKGRAY; }
    public int colorVerde () { return Color.GREEN; }
    public int colorRojo () { return Color.RED; }

    public void setColor (View view, int color) { view.setBackgroundColor(color); }
    public void setColor (int id, int color) { setColor (getView(id), color); }

    public void setTextColor (TextView view, int color) { view.setTextColor(color); }
    public void setTextColor (int id, int color) { setTextColor (getTextView(id), color); }

    public void setFondo (View view, int fondo) { view.setBackgroundResource(fondo); }
    public void setFondo (int id, int fondo) { setFondo (getView(id), fondo); }
}
