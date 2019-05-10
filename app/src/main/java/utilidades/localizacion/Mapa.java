package utilidades.localizacion;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

/**
 * Javier 2019.
 */

public class Mapa {

    private GeoPoint punto;
    private MapView mapa;
    private MapController mapaControlador;
    private Marker marca;



    public Mapa (MapView mapa) {
        this.mapa = mapa;
        cargarMapa(mapa);
    }



    private void cargarMapa(MapView mapa) {

        // Evita bloueo
        Configuration.getInstance().setUserAgentValue("Seguime");

        // si el mapa sigue bloueado, usar....
        //Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        // se configura el tipo de mapa
        mapa.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        mapa.setMultiTouchControls(true);


        // se obtiene un controlador para el mapa creado
        mapaControlador = (MapController) mapa.getController();
        // se crea un punto de coordenadas (inicial)
        punto= new GeoPoint(-33.2786,-66.3183);
        // se mueve el mapa a una coordenada
        mapaControlador.animateTo(punto);
        // se establece un nivel de zoom
        mapaControlador.setZoom(16);
        // se limpia el mapa y se actualiza
        mapa.getOverlays().clear();
        mapa.invalidate();

        marcarMapa(punto.getLatitude(),punto.getLongitude());
    }


    // pone una marca en el mapa
    private void marcarMapa (double latitud, double longitud) {
        // creamos la coordenada
        punto = new GeoPoint(latitud, longitud);
        // creamos el marcador en el mapa
        marca = new Marker(mapa);
        // ubicamos el marcador en la coordenada
        marca.setPosition(punto);
        // movemos el mapa a la coordenada
        marca.setTitle("¡hola! \r\n todavia no hay puntos para mostrar");
        mapaControlador.animateTo(punto);
        // opcionalmente podemos limpiar el mapa
        mapa.getOverlays().clear();
        // insertamos el marcador
        mapa.getOverlays().add(marca);
        // refrescamos el mapa
        mapa.invalidate();
    }








    // mueve el marcador a la posicion indicada
    public void moverMarca (double latitud, double longitud,String titulo) {
        punto = new GeoPoint(latitud, longitud);
        mapaControlador.animateTo(punto);
        marca.setPosition(punto);
        marca.setTitle(titulo);
    }

    public void moverMarca (String latitud, String longitud,String titulo) {
        moverMarca(Double.parseDouble(latitud), Double.parseDouble(longitud), titulo);
    }




}
