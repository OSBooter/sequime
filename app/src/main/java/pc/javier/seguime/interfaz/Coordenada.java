package pc.javier.seguime.interfaz;

import android.util.Log;

import java.util.Observable;

/**
 * Created by javier on 19/11/2016.
 */
public class Coordenada  {
    private String latitud;
    private String longitud;
    private String fecha;
    private String extra;
    private String velocidad;
    private int id;
    private int recibido;

    public Coordenada() {
        id=-1;
        recibido=0;
    }

    public Coordenada (String fecha, String latitud, String longitud, String velocidad, String extra) {
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
        this.extra = extra;
        this.velocidad = velocidad;
        id = -1;
        recibido=0;
    }

    public Coordenada (String fecha, double latitud, double longitud, String velocidad, String extra) {
        this.fecha = fecha;
        this.latitud = String.valueOf(latitud);
        this.longitud = String.valueOf(longitud);
        this.extra = extra;
        this.velocidad=velocidad;
        id = -1;
        recibido=0;
    }




    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getExtra() {
        return extra;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public int getId() { return id; }

    public int getRecibido () { return recibido; }

    public void setId (int id) { this.id = id; }

    public void setVelocidad (String velocidad) { this.velocidad = velocidad; }

    public void setRecibido (int valor) {recibido = valor; }


    public double getLatitudDouble () {
        try {
            return Double.parseDouble(latitud);
        } catch (Exception e){};
        return 0;
    }
    public double getLongitudDouble () {
        try {
            return Double.parseDouble(longitud);
        } catch (Exception e){};
        return 0;
    }

    public String Enlace (int zoom) {
        double lat = getLatitudDouble();
        double lon = getLongitudDouble();
        return createShortLinkString(lat, lon, zoom);
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
