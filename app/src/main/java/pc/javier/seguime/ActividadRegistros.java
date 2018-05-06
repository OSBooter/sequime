package pc.javier.seguime;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.BD;
import pc.javier.seguime.interfaz.Coordenada;
import pc.javier.seguime.utilidades.FechaHora;
import pc.javier.seguime.utilidades.ItemRegistro;
import pc.javier.seguime.utilidades.Unidades;

import android.content.Context;
import android.database.Cursor;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


import java.util.ArrayList;

public class ActividadRegistros extends AppCompatActivity {

    private BD bd;
    private ListView lv;
    private GeoPoint punto;
    public static ArrayAdapter<ItemRegistro> adaptador;
    public static Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registros);


        cargarMapa();

        cargarVista();

        listener();

        // habilita eventos del handler
        Aplicacion.actividadRegistros = this;

        handler=Aplicacion.handler;

    }

    public void onResume () {
        super.onResume();
    }


    private MapView mapa;
    private MapController mapaControlador;

    private void cargarMapa() {
        // se obtiene el mapa creado en la vista
        mapa = (MapView) findViewById(R.id.registros_mapa);

        // se configura el tipo de mapa
        mapa.setTileSource(TileSourceFactory.MAPNIK);

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


    // definimos un marcador (usando osmdroid bonus pack)
    Marker marca;

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
    private void moverMarca (double latitud, double longitud,String titulo) {
        punto = new GeoPoint(latitud, longitud);
        mapaControlador.animateTo(punto);
        marca.setPosition(punto);
        marca.setTitle(titulo);
    }

    private void moverMarca (String latitud, String longitud,String titulo) {
        moverMarca(Double.parseDouble(latitud), Double.parseDouble(longitud), titulo);
    }


    // evento de click, cuando el usuario selecciona un elemento de la lista de coordenadas
    private void listener () {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemRegistro r = (ItemRegistro) adapterView.getItemAtPosition(i);
                moverMarca(r.latitud,r.longitud,FechaHora.hora(r.fecha) + " \r\n" + Unidades.velocidad(r.velocidad));

            }
        });
    }





    // carga el list view
    private void cargarVista () {
        lv = (ListView) findViewById(R.id.registros_lista);

        ItemRegistro[] listaItem = cargarRegistros();

        adaptador = new AdaptadorRegistro(this,  listaItem);


        lv.setAdapter(adaptador);

    }




    // carga un arreglo de datos (registros) que servira para el list view
    private ItemRegistro[] cargarRegistros () {
        // base de datos
        bd = new BD(this);

        // obtiene las coordenadas de la base de datos
        ArrayList<Coordenada> listaCoordenada = bd.coordenadaObtenerTodas();

        // crea un arreglo del tamaño del numero de coordenadas obtenidas
        ItemRegistro[] listaRegistro= new ItemRegistro[listaCoordenada.size()];


        // recorre la lista de coordenadas, creando un item con cada una y los vuelca en el arreglo
        int x = 0;

        for (Coordenada coordenada : listaCoordenada) {

            listaRegistro[x] = item(coordenada);
            x= x+1;
        }

        bd.cerrar();


        // inicialmente
        // mueve la marca a la posicion conocida

        if(listaCoordenada.size() >0)
            moverMarca(listaRegistro[x-1].latitud, listaRegistro[x-1].longitud, FechaHora.hora(listaRegistro[x-1].fecha) + " \r\n" + Unidades.velocidad(listaRegistro[x-1].velocidad));



        return listaRegistro;


    }


    // vuelca la informacion de una coordenada en un Item de registros
    public static ItemRegistro item (Coordenada coordenada) {

            ItemRegistro reg = new ItemRegistro();

            reg = new ItemRegistro();

            reg.latitud = coordenada.getLatitud();
            reg.longitud = coordenada.getLongitud();
            reg.fecha = coordenada.getFecha() ;
            reg.extra = coordenada.getExtra();
            reg.velocidad= coordenada.getVelocidad();
            reg.recibido = coordenada.getRecibido();
            reg.id = coordenada.getId();

        return reg;
    }



    /*
    public static void agregar (ItemRegistro item) {
        adaptador.add(item);
    }
    */

    // agrega una nueva coordenada a la lista (para uso externo)
    public static void actualizar () {
        // adaptador.add(item(coordenada));



        Message mensaje = new Message();
        Bundle dato = new Bundle();
        // comando conexion es para visualizacion (antes era solo para conexion)

        dato.putString("conexion", "actualizarRegistros");
        mensaje.setData(dato);
        // no funciona
        handler.sendMessage(mensaje);
    }




    public static ItemRegistro[] arregloRegistro;


    private class AdaptadorRegistro extends ArrayAdapter {

        private Context contexto;



        public AdaptadorRegistro(Context context,  ItemRegistro[] datos) {
            super(context, R.layout.registrositem,datos);
            contexto = context;
            arregloRegistro=datos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            super.getView(position, convertView, parent);

            View item = getLayoutInflater().inflate(R.layout.registrositem,null);

            TextView latitud = (TextView) item.findViewById(R.id.registros_latitud);
            latitud.setText(String.valueOf(arregloRegistro[position].latitud));
            TextView longitud = (TextView) item.findViewById(R.id.registros_longitud);
            longitud.setText(String.valueOf(arregloRegistro[position].longitud));

            TextView extra = (TextView) item.findViewById(R.id.registros_extra);
            extra.setText(String.valueOf(arregloRegistro[position].extra));

            TextView velocidad = (TextView) item.findViewById(R.id.registros_velocidad);
            velocidad.setText(String.valueOf(
                    Unidades.velocidad(arregloRegistro[position].velocidad))
            );
            mensajeLog("la velocidad es " + arregloRegistro[position].velocidad);

            TextView fecha = (TextView) item.findViewById(R.id.registros_fechahora);


            fecha.setText(
                    FechaHora.fechaHora(String.valueOf(arregloRegistro[position].fecha))
            );



            if ( arregloRegistro[position].recibido ==0 )
                item.setBackgroundColor(Color.GRAY);


            mensajeLog ( position + " " + fecha.getText() + " -> " +  arregloRegistro[position].recibido );

            return item;
        }
    }


    private void mensajeLog (String texto) {
        Log.d("Actividad Registro", texto);
    }


}
