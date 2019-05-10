package utilidades.localizacion;

/**
 * Javier 2019
 *  crea un enlace corto de open street map
 */

public class EnlaceOSM {

    private int acercamiento = 19;
    private double latitud;
    private double longitud;
    private String url = "";

    public EnlaceOSM (double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void establecerAcercamiento (int acercamiento) { this.acercamiento = acercamiento; }

    public String url () {
        if (url.equals(""))
            url = createShortLinkString(latitud, longitud, acercamiento);
        return url;
    }

    public static String createShortLinkString(String latitude, String longitude, int zoom) {
        double lat = 0;
        double lon = 0;
        try {
            lat = Double.parseDouble(latitude);
            lon = Double.parseDouble(longitude);
        } catch (Exception e) {}
        return  createShortLinkString(lat, lon, zoom);
    }

    // obtenido de https://github.com/osmandapp/Osmand/blob/master/OsmAnd-java/src/main/java/net/osmand/util/MapUtils.java#L310

    public static String createShortLinkString(double latitude, double longitude, int zoom) {
        long lat = (long) (((latitude + 90d)/180d)*(1L << 32));
        long lon = (long) (((longitude + 180d)/360d)*(1L << 32));
        long code = interleaveBits(lon, lat);
        String str = "";
        // add eight to the zoom level, which approximates an accuracy of one pixel in a tile.
        for (int i = 0; i < Math.ceil((zoom + 8) / 3d); i++) {
            str += intToBase64[(int) ((code >> (58 - 6 * i)) & 0x3f)];
        }
        // append characters onto the end of the string to represent
        // partial zoom levels (characters themselves have a granularity of 3 zoom levels).
        for (int j = 0; j < (zoom + 8) % 3; j++) {
            str += '-';
        }
        return "https://osm.org/go/"+str+"?m=";
    }

    private static final char intToBase64[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '~'
    };

    private static long interleaveBits(long x, long y) {
        long c = 0;
        for (byte b = 31; b >= 0; b--) {
            c = (c << 1) | ((x >> b) & 1);
            c = (c << 1) | ((y >> b) & 1);
        }
        return c;
    }

}
