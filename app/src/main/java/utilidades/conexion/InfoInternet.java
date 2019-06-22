package utilidades.conexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/*
    Javier
    20 05 2019
 */

public class InfoInternet {


    private NetworkInfo infoRedes;


    public InfoInternet(Context contexto) {
        final ConnectivityManager conexion = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        infoRedes = conexion.getActiveNetworkInfo();
    }



    public boolean conectado () {
        if (infoRedes == null)
            return false;
        return infoRedes.isConnected();
    }



    public String getTipo () {
        if (infoRedes == null)
            return "";
        return infoRedes.getTypeName();
    }
    public String getInfo () {
        if (infoRedes == null)
            return "";
        return infoRedes.getExtraInfo().replace("\"", "");
    }


}

