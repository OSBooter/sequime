package pc.javier.seguime.control;

import android.app.Activity;
import android.content.Intent;
import pc.javier.seguime.adaptador.Pantalla;
import pc.javier.seguime.adaptador.Preferencias;
import utilidades.Servicios;


/**
 * Javier 2019.
 */
public class Control {
    protected Pantalla pantalla;
    protected Preferencias preferencias;
    protected Activity activity;

    public Control (Pantalla pantalla, Preferencias preferencias) {
        this.setPantalla(pantalla);
        this.setPreferencias(preferencias);
        activity = pantalla.getActivity();
    }

    public Control (Activity activity) {
        this.activity = activity;
        pantalla = new Pantalla(activity);
        preferencias = new Preferencias(activity);
    }





    // actividades --------------------------------------------------------



    public Intent iniciarActividad(Class clase) {
        Intent intent = new Intent(activity, clase);
        activity.startActivity(intent);
        // contexto.overridePendingTransition(R.anim.zoom_entrada, R.anim.zoom_salida);
        return intent;
    }



    // servicios ------------------------------------------------------

    public void iniciarServicio (Class clase) {
        if (servicioActivo(clase))
            return;
        Intent servicio = new Intent(activity, clase);
        activity.startService(servicio);
    }


    /*
    public void iniciarServicio (Class clase, Activity activity) {
        if (servicioActivo(clase))
            return;
        Bundle bundle = new Bundle();

        Intent servicio = new Intent(activity, clase);
        ArrayList<Activity> listaActivity = new ArrayList<Activity>();
        listaActivity.add(activity);
        bundle.putSerializable("activity",listaActivity);
        servicio.putExtras(bundle);
        activity.startService(servicio);
    }

    public void iniciarServicio (Class clase, ArrayList arrayList) {
        if (servicioActivo(clase))
            return;
        Intent servicio = new Intent(pantalla.getActivity(), clase);
        servicio.putExtra("arrayList", arrayList);
        pantalla.getActivity().startService(servicio);
    }
    */

    public boolean servicioActivo (Class servicio) {
        return (new Servicios(activity).activo(servicio));
    }


    public void detenerServicio (Intent intent) {
        activity.stopService(intent);
    }

    public void detenerServicio (Class clase) {
        if (servicioActivo(clase) == false)
            return;
        Intent servicio = new Intent(activity, clase);
        activity.stopService(servicio);
    }


    // --------------------------------

    public void cerrarAplicacion () {
        // este codigo CIERRA LA APLICACION
        // fuente: https://jcristoballopez.wordpress.com/2015/03/20/anadir-boton-de-salida-con-android-studio/
        activity.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    // gets / sets --------------------

    public Pantalla getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    public Preferencias getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Preferencias preferencias) {
        this.preferencias = preferencias;
    }





    // ver- ... esto debe quitarse para generalizar esta clase y enviarla a utilidades
    // la clase de preferencias está mejorada y no necesita ésto
    public void guardarPreferencia (Preferencias.TipoPreferencia tipo, String valor) {
        preferencias.guardar(tipo, valor);
    }

    public void guardarPreferencia (Preferencias.TipoPreferencia tipo, int valor) {
        preferencias.guardar(tipo, valor);
    }

    public void guardarPreferencia (Preferencias.TipoPreferencia tipo, boolean valor) {
        preferencias.guardar(tipo, valor);
    }
}
