package pc.javier.seguime.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Javier 18/11/2016.
 * conexión a la base de datos
 */
public class BDConexion extends SQLiteOpenHelper {


    // conexión a la base de datos de la aplicación

    public BDConexion(Context context) {
        super(context, "BaseDeDatos", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // crea las tablas usada para almacenar los datos recolectados,
        // para luego hacer uso de ellas (enviarla a servidores)
        sqLiteDatabase.execSQL(BDCoordenada.TABLA);
        sqLiteDatabase.execSQL(BDFoto.TABLA);
        //agregar Wifi y fotos


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // la tabla no se borra, así se puede recuperar la información
        //sqLiteDatabase.execSQL(BDCoordenada.RENOMBRARTABLA);
        sqLiteDatabase.execSQL(BDCoordenada.TABLA);


        sqLiteDatabase.execSQL(BDFoto.TABLA);
    }
}
