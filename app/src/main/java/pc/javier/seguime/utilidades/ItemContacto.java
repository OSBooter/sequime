package pc.javier.seguime.utilidades;

import android.database.Cursor;
import android.graphics.Bitmap;

/**
 * Created by Javier on 23 mar 2018.
 */

public class ItemContacto {
    public String nombre;
    public String telefono;
    public String telegram;
    public String id;
    public Bitmap foto;


    public ItemContacto () {
        nombre="";
        telefono="";
        telegram="";
    }

    public ItemContacto(String nombre) {
        this.nombre = nombre;
        telefono="";
        telegram="";
    }



}



