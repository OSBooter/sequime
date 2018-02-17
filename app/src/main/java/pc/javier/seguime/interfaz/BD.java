
package pc.javier.seguime.interfaz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import pc.javier.seguime.control.BDConexion;
import pc.javier.seguime.control.BDCoordenada;


/**
 * Created by javier on 18/11/2016.
 * maneja la base de datos, interface
 */
public class BD  {

    private BDConexion conexion;
    private BDCoordenada bdCoordenada;
    private SQLiteDatabase sql;

    public BD (Context contexto) {

        conexion = new BDConexion(contexto);
        abrir();
    }

    public void abrir () {
        sql = conexion.getWritableDatabase();

        // inicia cada uno de los manejadores de la base de datos
        bdCoordenada = new BDCoordenada(sql);
    }

    public void cerrar () {
        sql.close();
    }


    // Coordenadas -----------------------------------------

    public boolean coordenadaInsertar (String latitud, String longitud,String extra) {
        boolean respuesta = bdCoordenada.insertar(latitud, longitud,extra);

        return respuesta;
    }

    // marca una coordenada como "leida" o "enviada"
    public void coordenadaMarcar (int id) {
        bdCoordenada.marcar(id);
    }

    // elimina una coordenada
    public boolean coordenadaEliminar (int id) {
        return bdCoordenada.eliminar(id);
    }

    // elimina TODAS las coordenadas
    public boolean coordenadaEliminar () {
        return bdCoordenada.eliminar();
    }


    public Cursor coordenadaObtenerCursorTodas () {
        return bdCoordenada.obtener();
    }


    public ArrayList<Coordenada> coordenadaObtenerNuevas () {
        return Listar(bdCoordenada.obtenerNuevas());
    }

    public ArrayList<Coordenada> coordenadaObtenerNuevas (String cantidad) {
        return Listar(bdCoordenada.obtenerNuevas(cantidad));
    }

    public ArrayList<Coordenada> coordenadaObtenerTodas () {
        return Listar(bdCoordenada.obtener());
    }

    public Coordenada coordenadaObtenerUltima () {
        Cursor cursor = bdCoordenada.obtenerUltima();

        if ( cursor.getCount()>0) {
            cursor.moveToFirst();
            latitud = cursor.getFloat(2);
            longitud = cursor.getFloat(3);
            fecha = cursor.getString(1);
            extra = cursor.getString(6);
            recibido = cursor.getInt(5);
            id = cursor.getInt(0);


            coordenada = new Coordenada(fecha, latitud, longitud, extra);
            coordenada.setRecibido(recibido);
            coordenada.setId(id);
            return coordenada;
        } else
        return null;
    }




    private double latitud;
    private double longitud;
    private String extra;
    private String fecha;
    private int recibido;
    private int id;
    private Coordenada coordenada;

    private ArrayList<Coordenada> Listar (Cursor cursor) {
        ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

        mensajeLog("Listando..." + cursor.getCount());
        cursor.moveToFirst();
        for (int x=0; x < cursor.getCount(); x++) {

            mensajeLog("Listando en bucle..." + x);
            latitud = cursor.getFloat(2);
            longitud = cursor.getFloat(3);
            fecha = cursor.getString(1);
            extra = cursor.getString(6);
            recibido = cursor.getInt(5);
            id = cursor.getInt(0);

            cursor.moveToNext();

            coordenada = new Coordenada(fecha, latitud, longitud, extra);
            coordenada.setRecibido(recibido);
            coordenada.setId(id);
            lista.add(coordenada);
        }

        return lista;
    }



    private void mensajeLog (String texto) {
        Log.d("BD", texto);
    }
}
