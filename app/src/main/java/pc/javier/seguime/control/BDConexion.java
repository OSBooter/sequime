package pc.javier.seguime.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javier on 18/11/2016.
 * conexion a la base de datos
 */
public class BDConexion extends SQLiteOpenHelper {


    // conexion a la base de datos de la aplicacion

    public BDConexion(Context context) {
        super(context, "BaseDeDatos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // crea las tablas usada para almacenar los datos recolectados,
        // para luego hacer uso de ellas (enviarla a servidores)
        sqLiteDatabase.execSQL(BDCoordenada.TABLA);
        //agregar Wifi y fotos


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
