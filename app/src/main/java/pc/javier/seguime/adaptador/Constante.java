package pc.javier.seguime.adaptador;

import pc.javier.seguime.BuildConfig;

/**
 * Javier 2019.
 *  configuraciones y parametros constantes
 */

public final class Constante {
    public final static String version = BuildConfig.VERSION_NAME;
    public final static String fechaVersion = "05 abril 2019";

    public final static String userAgent = "seguime 2";
    public final static String urlPaypal = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BDUHGWZKV2R8W";
    public final static String urlBot = "http://t.me/seguimebot";

    public final static String urlSitio = "http://seguime.000webhostapp.com/";
    public final static String urlRegistro = urlSitio + "registroversion.php";
    public final static String urlServidor = urlSitio + "servicio.php";
    public final static String urlEliminarCuenta = urlSitio + "eliminarcuenta.php";

    public final static int zonaHorariaServidor = -3; // Argentina

    public final static boolean versionCompleta = true;

}
