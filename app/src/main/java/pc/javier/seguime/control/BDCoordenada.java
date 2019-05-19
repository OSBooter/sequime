package pc.javier.seguime.control;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Javier 2016.
 *
 * Manejador de Base de datos de coordenadas (adaptador)
 *
 * editado 2019
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
            "velocidad text," +
            "proveedor text," +
            "envios int," +
            "recibido int," +
            "fechaEnviado text,"+
            "extra text," +
            "codigo text" +
            ");"
            ;

    public static String RENOMBRARTABLA = "ALTER TABLE " + nombre + " RENAME TO " + nombre + "copia ;";



    public BDCoordenada (SQLiteDatabase db) {
        this.sql = db;
    }



    public boolean insertar(String latitud, String longitud, String velocidad, String fecha, String proveedor, String codigo, String extra) {
        ContentValues valor = new ContentValues();
        valor.put("latitud",latitud);
        valor.put("longitud", longitud);
        valor.put("envios", 0);
        valor.put("recibido", 0);
        valor.put("velocidad", velocidad);
        valor.put("extra", extra);
        valor.put("fecha", fecha);
        valor.put("proveedor", proveedor);
        valor.put("codigo", codigo);

        int respuesta = (int) sql.insert(nombre,null, valor);

        valor.clear();
        return  (respuesta > 0);
    }

    public void marcar(int id, String fecha) {

        ContentValues valor = new ContentValues();
        valor.put("recibido", "1");
        valor.put("fechaEnviado", fecha);
        mensajeLog ("marcando como recibido id= " + id);
        sql.update(nombre,valor,"id = " + id, null);
    }

    public void marcar(String codigo, String fecha) {
        ContentValues valor = new ContentValues();
        valor.put("recibido", "1");
        valor.put("fechaEnviado", fecha);
        //valor.put("fechaEnviado", fecha);
        mensajeLog ("marcando como recibido codigo= " + codigo);
        sql.update(nombre,valor,"codigo = " + codigo, null);
    }


    public boolean eliminar (int id) {
        return (sql.delete(nombre,"id = " + id, null) > 0);
    }

    //elimina todo
    public boolean eliminar () {
        return (sql.delete(nombre,null, null) > 0);
    }


    public Cursor obtener () {
        //Cursor c = sql.rawQuery("*",null);
        //Cursor c= sql.query(nombre,new String[]{"*"},null,null,null,null, null);
        Cursor c= sql.query(nombre,new String[]{"*"},null,null,null,null,"fecha desc","1000");
        return c;
    }


    public Cursor obtenerNuevas () {
        return  obtenerNuevas(5);
    }

    public Cursor obtenerNuevas (int cantidad) {
        Cursor c= sql.query(nombre,new String[]{"*"},"recibido=0",null,null,null,"fecha desc",String.valueOf(cantidad));
        return c;
    }


    public Cursor obtenerUltimaNoEnviada () {
        Cursor c= sql.query(nombre,new String[]{"*"},"recibido=0",null,null,null,"fecha desc","1");
        return c;
    }

    public Cursor obtenerUltima () {
        Cursor c= sql.query(nombre,new String[]{"*"},null,null,null,null,"fecha desc","1");
        return c;
    }




    private void mensajeLog (String texto) {
        Log.d("Basededatos Coordenadas", texto);
    }

}
