package utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InfoInternet {


    private NetworkInfo infoRedes;


    public InfoInternet(Context contexto) {
        final ConnectivityManager conexion = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRedes = conexion.getActiveNetworkInfo();
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
        return infoRedes.getExtraInfo();
    }


}

