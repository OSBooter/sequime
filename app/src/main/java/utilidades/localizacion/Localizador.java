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

import java.util.ArrayList;

import utilidades.Evento;


/**
 * Javier 2019.
 *
 */
public class Localizador implements LocationListener {

    private LocationManager localizadorADM;
    private Context contexto;

    private Evento evento = null;

    // constructor
    public Localizador (Context contexto) {
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





    private void actualizar(Location posicion) {
        if (posicion == null)
            return;

        Coordenada coordenada = new Coordenada();

        coordenada.setLatitud( posicion.getLatitude());
        coordenada.setLongitud(posicion.getLongitude());
        if (posicion.hasSpeed())
            coordenada.setVelocidad( posicion.getSpeed());

        coordenada.setProveedor(posicion.getProvider());

        // lanza una nueva coordenada


        mensajeLog("nuevas coordenadas - latitud:" + coordenada.getLatitud() + ", longitud:" + coordenada.getLongitud() + "velocidad: " + coordenada.getVelocidad());

        if (evento != null)
            emitirNuevaCoordenada(coordenada);
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
        localizadorADM.removeUpdates(this);
    }







    private void emitirNuevaCoordenada (Coordenada coordenada) {
        ArrayList<Coordenada> listaCoordenada = new ArrayList<>();
        listaCoordenada.add(coordenada);

        evento.agregarDato(listaCoordenada);
        evento.emitir();
    }






    @Override
    public void onLocationChanged(Location location) {
        actualizar(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {    }

    @Override
    public void onProviderEnabled(String s) {    }

    @Override
    public void onProviderDisabled(String s) {    }


    private void mensajeLog(String texto) {
        Log.d("Localizacion", texto);
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

}