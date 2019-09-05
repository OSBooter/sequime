package pc.javier.seguime.adaptador;

import pc.javier.seguime.BuildConfig;

/**
 * Javier 2019.
 *  configuraciones y parametros constantes
 */

public final class Constante {
    public final static String version = BuildConfig.VERSION_NAME ;

    public final static String userAgent = "seguime 4";   // esto es usado para la versión del servidor (no identifica la versión del sistema)
    public final static String urlDonacion = "http://javim.000webhostapp.com/donacion/";
    public final static String urlBot = "http://t.me/seguimebot";

    public final static String urlSitio = "http://seguime.000webhostapp.com/";
    public final static String urlRegistro = urlSitio + "registroversion.php";
    public final static String urlServidor = urlSitio + "servicio.php";
    public final static String urlEliminarCuenta = urlSitio + "eliminarcuenta.php";




    public final static boolean versionConSMS = true;
    public final static boolean versionCompleta = false;

}
