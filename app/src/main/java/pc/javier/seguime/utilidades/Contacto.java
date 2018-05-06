package pc.javier.seguime.utilidades;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Javier
 * on 24 mar 2018.
 */




public class Contacto {

    public String nombre;
    public String telefono;
    public String telegram;
    public String id;
    public Bitmap foto;

    private ContentResolver contentResolver;


    public Contacto() {
        nombre="";
        telefono="";
        telegram="";
        id="";
        foto=null;
    }








    // crea listados de contactos


    public ArrayList<Contacto> todos (ContentResolver contentResolver) {
        // obtiene los contactos
        this.contentResolver = contentResolver;


        Uri contactos = Uri.parse("content://com.android.contacts/contacts");

        Cursor c = contentResolver.query(contactos, null, null, null, null);


        ArrayList<Contacto> lista = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount()>0)
            while (c.moveToNext()){
                Contacto contacto = new Contacto();
                contacto.id = c.getString(c.getColumnIndex("_ID"));
                contacto.nombre = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacto.telefono = numeroTelefono(contacto.id);
                if (foto(contacto.id) != null)
                    contacto.foto = foto(contacto.id);
                lista.add(contacto);
            }

        return lista;
    }



    private String numeroTelefono (String id) {
        String respuesta ="";

        Cursor telc = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                new String[] { id },
                null);
        if (telc.moveToFirst())
            respuesta = telc.getString(0);

        telc.close();
        return respuesta;
    }



    private Bitmap foto (String id) {
        Bitmap foto = null;
        try {
            InputStream input =
                    ContactsContract.Contacts.openContactPhotoInputStream(
                            contentResolver,
                            ContentUris.withAppendedId(
                                    ContactsContract.Contacts.CONTENT_URI,
                                    Long.parseLong(id))
                    );
            if (input != null) {
                /*
                Dar formato tipo Bitmap a los bytes del BLOB
                correspondiente a la foto
                 */
                foto = BitmapFactory.decodeStream(input);
                input.close();
            }

        } catch (Exception iox) {
            /* Manejo de errores */ }

        return foto;
    }




}
