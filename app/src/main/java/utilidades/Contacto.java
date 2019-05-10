package utilidades;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Javier 2018.
 */



public class Contacto {

    public static final int CODIGO=0;
    private Activity activity;

    public Contacto (Activity activity) {
        this.activity = activity;
    }


    public void abrirPantalla () {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        activity.startActivityForResult(pickContactIntent, CODIGO);
    }


    public String getNumero (Intent dato) {

        Uri contactUri = dato.getData();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = activity.getContentResolver()
                .query(contactUri, projection, null, null, null);
        cursor.moveToFirst();

        // Retrieve the phone number from the NUMBER column
        int columna = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String numbero = cursor.getString(columna);

        // Do something with the phone number...
        mensajeLog(numbero);

        return  numbero;
    }



    private void mensajeLog (String texto) {
        MensajeRegistro.msj(this, texto);
    }






}
