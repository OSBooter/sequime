package pc.javier.seguime.control;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import pc.javier.seguime.utilidades.FechaHora;


/**
 * Created by javier on 18/11/2016.
 *
 * // Manejador de Base de datos de coordenadas (adaptador)
 *
 */
public class BDCoordenada {



    private SQLiteDatabase sql;
    private static String nombre = "coordenadas";
    public static String TABLA = "" +
            "Create Table if not exists " + nombre + "(" +
            "ID integer primary key autoincrement," +
            "fecha text," +
            "latitud text, " +
            "longitud text," +
            "envios int," +
            "recibido int," +
            "velocidad text," +
            "extra text" +
            ");"
            ;


    public BDCoordenada (SQLiteDatabase db) {
        this.sql = db;
    }

    public void insertar (String latitud, String longitud, String velocidad) {
        insertar(latitud, longitud, velocidad,"");
    }

    public boolean insertar(String latitud, String longitud, String velocidad, String extra) {
        ContentValues valor = new ContentValues();
        valor.put("latitud",latitud);
        valor.put("longitud", longitud);
        valor.put ("envios", 0);
        valor.put("recibido", 0);
        valor.put("velocidad", velocidad);
        valor.put("extra", extra);
        valor.put("fecha", FechaHora.complacto());

        int respuesta = (int) sql.insert(nombre,null, valor);

        valor.clear();
        return  (respuesta > 0);
    }

    public int marcar(int id) {
        ContentValues valor = new ContentValues();
        valor.put("recibido", "1");
        mensajeLog ("marcando como recibido id= " + id);
        return sql.update(nombre,valor,"id = " + id, null);
    }

    public boolean eliminar (int id) {
        return (sql.delete(nombre,"id = " + id, null) > 0);
    }

    //elimina todo
    public boolean eliminar () {
        return (sql.delete(nombre,"*", null) > 0);
    }


    public Cursor obtener () {
        //Cursor c = sql.rawQuery("*",null);
        //Cursor c= sql.query(nombre,new String[]{"*"},null,null,null,null, null);
        Cursor c= sql.query(nombre,new String[]{"*"},null,null,null,null,"fecha desc","1000");
        return c;
    }


    public Cursor obtenerNuevas () {
        Cursor c= sql.query(nombre,new String[]{"*"},"recibido=0",null,null,null,"fecha desc","5");
        return c;
    }

    public Cursor obtenerNuevas (String cantidad) {
        Cursor c= sql.query(nombre,new String[]{"*"},"recibido=0",null,null,null,"fecha desc",cantidad);
        return c;
    }


    public Cursor obtenerUltima () {

        Cursor c= sql.query(nombre,new String[]{"*"},"recibido=0",null,null,null,"fecha desc","1");

        return c;
    }




    private void mensajeLog (String texto) {
        Log.d("Basededatos Coordenadas", texto);
    }

}
