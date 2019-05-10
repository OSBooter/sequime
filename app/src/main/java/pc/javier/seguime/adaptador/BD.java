
package pc.javier.seguime.adaptador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import pc.javier.seguime.control.BDConexion;
import pc.javier.seguime.control.BDCoordenada;



/**
 * Javier 2016.
 * maneja la base de datos, interface
 *  versión 2019
 */
public class BD  {

    private BDConexion conexion;
    private BDCoordenada bdCoordenada;
    private SQLiteDatabase sql;

    public BD (Context contexto) {
        conexion = new BDConexion(contexto);
        abrir();
    }



    // Base de datos  -----------------------------------------


    public void abrir () {
        sql = conexion.getWritableDatabase();

        // inicia cada uno de los manejadores de la base de datos
        bdCoordenada = new BDCoordenada(sql);
    }

    public void cerrar () {
        sql.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
    // Coordenadas -----------------------------------------

    public boolean coordenadaInsertar (Coordenada coordenada) {
        return coordenadaInsertar(
                coordenada.getLatitud(),
                coordenada.getLongitud(),
                coordenada.getVelocidad(),
                coordenada.getFechaHora(),
                coordenada.getProveedor(),
                coordenada.getCodigo(),
                ""
                );
    }

    public boolean coordenadaInsertar (double latitud, double longitud, float velocidad, Date fecha, String proveedor, String codigo, String extra) {

        return coordenadaInsertar(
                String.valueOf(latitud),
                String.valueOf(longitud),
                String.valueOf(velocidad),
                String.valueOf(fecha.getTime()),
                proveedor,
                codigo,
                ""
        );
    }


    public boolean coordenadaInsertar (String latitud, String longitud,String velocidad, String fecha, String proveedor, String codigo, String extra) {
        boolean respuesta = bdCoordenada.insertar(latitud, longitud,velocidad, fecha, proveedor, codigo, extra);
        return respuesta;
    }

    // marca una coordenada como "leida" o "enviada"
    public void coordenadaMarcar (int id) {
        bdCoordenada.marcar(id, String.valueOf(new Date().getTime()));
    }

    // marca una coordenada como "leida" o "enviada"
    public void coordenadaMarcar (String codigo) {
        bdCoordenada.marcar(codigo, String.valueOf(new Date().getTime()));
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

    public ArrayList<Coordenada> coordenadaObtenerNuevas (int cantidad) {
        return Listar(bdCoordenada.obtenerNuevas(cantidad));
    }

    public ArrayList<Coordenada> coordenadaObtenerTodas () {
        return Listar(bdCoordenada.obtener());
    }

    public Coordenada coordenadaObtenerUltima () {
        Cursor cursor = bdCoordenada.obtenerUltima();

        if ( cursor.getCount()>0) {
            cursor.moveToFirst();
            return crearCoordenada(cursor);

        } else
        return null;
    }





    private ArrayList<Coordenada> Listar (Cursor cursor) {
        ArrayList<Coordenada> lista = new ArrayList<Coordenada>();
        int total = cursor.getCount();

        mensajeLog("Listando..." + total);
        cursor.moveToFirst();

        if (total > 0)
        while (cursor.isAfterLast() == false) {

            Coordenada coordenada = crearCoordenada(cursor);
            lista.add(coordenada);
            cursor.moveToNext();

        } ;

            return lista;
    }



    private Coordenada crearCoordenada (Cursor cursor) {

        double latitud;
        double longitud;
        String extra;
        Date fecha = new Date();
        boolean recibido = false;
        int id;
        int envios;
        float velocidad;
        String proveedor;
        Date fechaRecibido = new Date();
        String codigo;


        fecha.setTime(Long.parseLong( cursor.getString(1)));
        latitud = Double.parseDouble(cursor.getString(2));
        longitud = Double.parseDouble(cursor.getString(3));
        velocidad = Float.parseFloat(cursor.getString(4));
        proveedor = cursor.getString(5);
        envios = cursor.getInt(6);
        if (cursor.getInt(7) > 0)
            recibido = true;
        // if (recibido == true)  fechaRecibido.setTime(Long.parseLong( cursor.getString(8)));
        extra = cursor.getString(9);
        id = cursor.getInt(0);
        codigo = cursor.getString(10);

        return crearCoordenada(
                latitud,
                longitud,
                velocidad,
                fecha,
                proveedor,
                extra,
                id,
                recibido,
                codigo
        );

    }



    private Coordenada crearCoordenada (double latitud, double longitud, float velocidad, Date fecha, String proveedor, String extra, int id, boolean recibido, String codigo) {
        Coordenada coordenada = new Coordenada(latitud,longitud);
        coordenada.setVelocidad(velocidad);
        coordenada.setProveedor(proveedor);
        coordenada.setFechaHora(fecha);
        coordenada.setRecibido(recibido);
        coordenada.setId(id);
        coordenada.setCodigo(codigo);
        return coordenada;
    }





    // elimina la información de la base de datos y cierra
    public void eliminarTodoYCerrar () {
        coordenadaEliminar();
        cerrar();
    }

    private void mensajeLog (String texto) {
        Log.d("BD", texto);
    }
}
