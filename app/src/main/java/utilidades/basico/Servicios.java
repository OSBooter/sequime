package utilidades.basico;

import android.app.ActivityManager;
import android.content.Context;


import java.util.List;

/**
 * Created by kebukai on 08/04/2019.
 */

public class Servicios {

    private Context contexto;

    // verifica si el servicio esta activo
    public Servicios (Context contexto) {
        this.contexto = contexto;
    }



    public List<ActivityManager.RunningServiceInfo> listaServicios () {
        // obtiene una lista de servicios
        return ((ActivityManager) contexto.getSystemService(contexto.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE);
    }

    public boolean activo(Class servicio) {
        // recorre la lista buscando la clase
        for (ActivityManager.RunningServiceInfo s: listaServicios())
            if(s.service.getClassName().equals(servicio.getName()))
                return true;

        return false;
    }



}
