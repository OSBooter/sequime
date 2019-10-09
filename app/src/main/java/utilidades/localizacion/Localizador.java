package utilidades.localizacion;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import utilidades.eventos.BolaDeEventos;


/**
 * Javier 2019.
 * Actualización Septiembre 2019
 */
public class Localizador {

    // public final static String EVENTO_CLAVE = "LOCALIZADOR";
    public final static int EVENTO_ID = -1200;

    private LocationManager localizadorADM;
    private LocalizadorReceptor localizadorGPS;
    private LocalizadorReceptor localizadorRed;
    private Context contexto;


    // constructor
    public Localizador(Context contexto) {
        this.contexto = contexto;

        // PERMISOS
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;


        // servicio de localizacion
        localizadorADM = (LocationManager) contexto.getSystemService(contexto.LOCATION_SERVICE);

        // obtiene la ultima posicion conocida
        Location posicion = localizadorADM.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        // prepara la escucha para emitir coordenadas

        //actualizar(posicion);
        if (posicion != null)
            actualizar(posicion);

    }


    private void actualizar (Location posicion) {
        if (posicion == null)
            return;

        Coordenada coordenada = new Coordenada();

        coordenada.setLatitud(posicion.getLatitude());
        coordenada.setLongitud(posicion.getLongitude());
        if (posicion.hasSpeed())
            coordenada.setVelocidad(posicion.getSpeed());

        coordenada.setProveedor(posicion.getProvider());

        // lanza una nueva coordenada


        mensajeLog("nuevas coordenadas - latitud: " + coordenada.getLatitud() + ", longitud: " + coordenada.getLongitud() + "velocidad: " + coordenada.getVelocidad());


        emitirNuevaCoordenada(coordenada);
    }


    // activa la escucha
    public void activar() {
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        if (localizadorGPS == null)
            localizadorGPS = new LocalizadorReceptor();

        if (localizadorRed == null)
            localizadorRed = new LocalizadorReceptor();

        localizadorADM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 5, localizadorRed);
        localizadorADM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 5, localizadorGPS);
    }

    // desactiva la escucha
    public void desactivar() {
        localizadorADM.removeUpdates(localizadorGPS);
        localizadorADM.removeUpdates(localizadorRed);
    }


    private void emitirNuevaCoordenada(Coordenada coordenada) {
        BolaDeEventos bola = new BolaDeEventos();
        bola.setIdentificador(EVENTO_ID);
        bola.agregarDato("latitud", coordenada.getLatitud());
        bola.agregarDato("longitud", coordenada.getLongitud());
        bola.agregarDato("velocidad", coordenada.getVelocidad());
        bola.agregarDato("proveedor", coordenada.getProveedor());
        bola.agregarDato("codigo", coordenada.getCodigo());
        bola.lanzar();
    }


    public class LocalizadorReceptor implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            actualizar(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}

    }

    private void mensajeLog(String texto) {
        Log.d("Localizacion", texto);
    }


}