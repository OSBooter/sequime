package pc.javier.seguime;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import java.io.InputStream;
import java.util.ArrayList;


import pc.javier.seguime.utilidades.Contacto;
import pc.javier.seguime.utilidades.ItemContacto;
import pc.javier.seguime.utilidades.Parametro;

public class ActividadContactos extends AppCompatActivity {

    private ListView lv;
    public static ArrayAdapter<ItemContacto> adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactos);

        cargarVista();
        listener();
    }

    public void onResume () {
        super.onResume();
    }






    // evento de click, cuando el usuario selecciona un elemento de la lista
    private void listener () {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemContacto r = (ItemContacto) adapterView.getItemAtPosition(i);


                // cerrar y devolver r;
                /*SharedPreferences preferencias = Aplicacion.preferencias;
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("sms",r.telefono);
                editor.commit();
                */
                Parametro.telefono = r.telefono;
                finish();

            }
        });
    }





    // carga el list view
    private void cargarVista () {
        lv = (ListView) findViewById(R.id.contactos_lista);

        ItemContacto[] listaItem = cargarContactos();

        adaptador = new AdaptadorContacto(this,  listaItem);


        lv.setAdapter(adaptador);

    }




    // REVISAR

    // carga un arreglo de datos (contactos) que servira para el list view
    private ItemContacto[] cargarContactos () {
        Contacto contacto = new Contacto();
        ArrayList<Contacto> lista = contacto.todos(getContentResolver());

        ItemContacto[] arregloitem = new ItemContacto[lista.size()];

        for (int x = 0; x< lista.size(); x++) {
            ItemContacto item = new ItemContacto();
            mensajeLog( lista.get(x).nombre + x);
            item.nombre =  lista.get(x).nombre;
            item.telefono = lista.get(x).telefono;
            item.foto = lista.get(x).foto;
            item.id = lista.get(x).id;
            arregloitem[x] = item;
        }
        return arregloitem;
    }





    public static ItemContacto[] arregloContacto;


    private class AdaptadorContacto extends ArrayAdapter {

        private Context contexto;



        public AdaptadorContacto(Context context, ItemContacto[] datos) {
            super(context, R.layout.registrositem,datos);
            contexto = context;
            arregloContacto=datos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            super.getView(position, convertView, parent);
            mensajeLog("view - " + position + " -> " + arregloContacto[position].nombre);

            View item = getLayoutInflater().inflate(R.layout.contactositem,null);

            TextView nombre = (TextView) item.findViewById(R.id.contactos_nombre);
            nombre.setText(String.valueOf(arregloContacto[position].nombre));

            TextView telefono = (TextView) item.findViewById(R.id.contactos_telefono);
            telefono.setText(String.valueOf(arregloContacto[position].telefono));

            ImageView foto = (ImageView) item.findViewById(R.id.contactos_foto);
            if (arregloContacto[position].foto != null)
                foto.setImageBitmap(arregloContacto[position].foto);


            return item;
        }
    }


    private void mensajeLog (String texto) {
        Log.d("Actividad Contacto", texto);
    }


}
