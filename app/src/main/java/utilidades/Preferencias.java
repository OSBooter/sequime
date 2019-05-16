package utilidades;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Javier
 * 15 05 2019
 */

public class Preferencias {

    private SharedPreferences preferencias = null;
    private String nombrePreferencias = "preferencias";
    private Context contexto;

    public Preferencias (Context contexto) {
        this.contexto = contexto;
        preferencias = preferencias();
    }

    public Preferencias (Context contexto, String nombrePreferencias) {
        this.contexto = contexto;
        preferencias = preferencias();
        this.nombrePreferencias = nombrePreferencias;
    }



    public void guardar (String clave, String valor) {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.putString(clave, valor);
        editor.commit();
    }

    public void guardar (String clave, int valor) {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.putInt(clave, valor);
        editor.commit();
    }

    public void guardar (String clave, boolean valor) {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.putBoolean(clave, valor);
        editor.commit();
    }

    public void guardarLong (String clave, long valor) {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.putLong(clave, valor);
        editor.commit();
    }



    public int obtenerInt (String clave) {
        return preferencias().getInt(clave, 0);
    }
    public String obtenerString (String clave) {
        return preferencias().getString(clave, "");
    }
    public boolean obtenerBoolean (String clave) {
        return preferencias().getBoolean(clave, false);
    }
    public long obtenerLong (String clave) {
        return preferencias().getLong(clave, 0);
    }

    public void eliminarTodo() {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.clear();
        editor.commit();
    }


    public void borrar (String clave) {
        SharedPreferences.Editor editor = preferencias().edit();
        editor.remove(clave);
        editor.commit();
    }


    public SharedPreferences preferencias() {
        if (preferencias == null)
            return contexto.getSharedPreferences(nombrePreferencias,Context.MODE_PRIVATE);
        return  preferencias;
    }





	
	
    public void configuracionInicial() {

        if (preferencias().getBoolean("configuracioninicial", true) == false)
            return;
        guardar("configuracionInicial", true);

    }


    public SharedPreferences getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(SharedPreferences preferencias) {
        this.preferencias = preferencias;
    }

    public String getNombrePreferencias() {
        return nombrePreferencias;
    }

    public void setNombrePreferencias(String nombrePreferencias) {
        this.nombrePreferencias = nombrePreferencias;
        preferencias = preferencias();
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
        preferencias = preferencias();
    }
}
