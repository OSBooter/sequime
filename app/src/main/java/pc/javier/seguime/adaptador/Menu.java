package pc.javier.seguime.adaptador;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import pc.javier.seguime.R;
import utilidades.vista.PanelLateral;

/**
 * Javier 2019.
 */

public class Menu extends PanelLateral {
    public Menu(Activity activity) {
        super(activity, R.id.drawer_layout);
    }


    public void versionRegistrada () {
        setOpcionVisible(R.id.menu_registraraplicacion, false);
    }

    public void sesionIniciada (String usuario) {
        setOpcionVisible(R.id.menu_sesion, false);
        setOpcionVisible(R.id.menu_cerrarsesion, true);
        setOpcionHabilitada(R.id.menu_bloquear, true);
        setTextUsuario(usuario);
    }

    public void aplicacionBloqueada (boolean bloqueado) {
        setOpcionHabilitada(R.id.menu_sesion, !bloqueado);
        setOpcionHabilitada(R.id.menu_cerrarsesion, !bloqueado);
        setOpcionHabilitada(R.id.menu_registros, !bloqueado);
        setOpcionHabilitada(R.id.menu_cuentaregresiva, !bloqueado);
        setOpcionHabilitada(R.id.menu_opciones, !bloqueado);
        if (bloqueado)  setTextUsuario("*** oculto ***");
        if (bloqueado) {
            getItem(R.id.menu_bloquear).setTitle(R.string.desbloquear);
            getItem(R.id.menu_bloquear).setIcon(R.drawable.unlock_alt);
        }
        else {
            getItem(R.id.menu_bloquear).setTitle(R.string.bloquear);
            getItem(R.id.menu_bloquear).setIcon(R.drawable.locked);
        }

    }

    public void aplicacionActiva (boolean estado) {
        setOpcionHabilitada(R.id.menu_opciones, !estado);
    }



    private void setTextView (int id, String texto) {
        TextView textView = getTextView(id);
        if (textView != null)
            textView.setText(texto);
    }

    private void setOpcionVisible(int id, boolean valor) {
        MenuItem menuItem = getItem(id);
        if (menuItem != null)
            menuItem.setVisible(valor);
    }
    private void setOpcionHabilitada (int id, boolean valor) {
        MenuItem menuItem = getItem(id);
        if (menuItem != null)
            menuItem.setEnabled(valor);
    }

    private MenuItem getItem (int id) {
        return ((NavigationView) drawer.findViewById(R.id.nav_view)).getMenu().findItem(id);
    }

    private TextView getTextView (int id) {
        return ((TextView) activity.findViewById(id));
    }

    private void setTextUsuario (String texto) {
        TextView textView = getTextView(R.id.menu_texto_usuario);
        if (textView != null)
            textView.setText(texto);
    }

}

