package pc.javier.seguime.control;

import pc.javier.seguime.interfaz.Coordenada;
import pc.javier.seguime.utilidades.FechaHora;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Observable;

/**
 * Created by javier on 17/11/2016.
 *
 */
public class Localizacion extends Observable implements LocationListener {

    LocationManager localizadorADM;
    Location posicion;
    Context contexto;

    double latitud;
    double longitud;
    String informacionExtra;
    String velocidad;

    private Coordenada coordenada;


    // constructor
    public Localizacion(Context c) {
        contexto = c;

        // PERMISOS
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;


        // servicio de localizacion
        localizadorADM = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);

        // obtiene la ultima posicion conocida
        posicion = localizadorADM.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        //actualizar(posicion);
        if (posicion != null) {
            latitud = posicion.getLatitude();
            longitud = posicion.getLongitude();
        }

    }

    private void actualizar(Location posicion) {
        if (posicion == null)
            return;
        this.posicion = posicion;
        latitud = posicion.getLatitude();
        longitud = posicion.getLongitude();

        if (posicion.hasSpeed())
            velocidad =  String.valueOf(posicion.getSpeed());
        else
            velocidad ="0";

        informacionExtra = "";

        // informacionExtra = informacionExtra +"\n (Prov: " + posicion.getProvider() +")";

        // lanza una nueva coordenada
        coordenada = new Coordenada(FechaHora.complacto(),latitud, longitud, velocidad, informacionExtra);



        // Lanza el evento;
        setChanged();
        notifyObservers(coordenada);

        mensajeLog ( "nuevas coordenadas - latitud:" + latitud + ", longitud:" + longitud + "velocidad: "+ velocidad);


    }


    // activa la escucha
    public void activar() {
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        localizadorADM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 5, this);
        localizadorADM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 5, this);

    }

    // desactiva la escucha
    public void desactivar() {
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        localizadorADM.removeUpdates(this);
    }






    @Override
    public void onLocationChanged(Location location) {
        actualizar(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private void mensajeLog (String texto) {
        Log.d("Localizacion", texto);
    }
}
