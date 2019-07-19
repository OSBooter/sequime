package pc.javier.seguime.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Pantalla;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.vista.PantallaRegistros;
import utilidades.basico.FechaHora;

/**
 * Created by Javier on 16/07/2019.
 */

public class ControlPantallaImagenes extends Control {


    private PantallaRegistros pantalla;
    private Context contexto;

    public ControlPantallaImagenes(PantallaRegistros pantalla, Preferencias preferencias) {
        super(pantalla, preferencias);

        this.preferencias = preferencias;
        this.pantalla = pantalla;
        this.contexto = pantalla.getActivity();

        
    }


    // evento de click, cuando el usuario selecciona un elemento de la lista de coordenadas
    private void cargarListener() {
        pantalla.lista().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptador, View view, int posicion, long id) {

                Imagen imagen = (Imagen) adaptador.getItemAtPosition(posicion);
                pantalla.imagen().setImageBitmap(decodificar(imagen.getImagen()));

            }
        });
    }

    private Bitmap decodificar(String codigoB64) {
        byte bytes[] = Base64.decode(codigoB64, Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bm;
    }




    // carga el list view
    private void cargarVista() {
        ArrayList<Imagen> listaImagenes = obtenerImagenes();
        AdaptadorRegistro adaptador = new AdaptadorRegistro(contexto, listaImagenes);
        pantalla.lista().setAdapter(adaptador);
    }


    // carga una lista de datos (registros) que servira para el list view
    // obtiene coordenadas de la base de datos
    private ArrayList<Imagen> obtenerImagenes () {
        // base de datos
        BD bd = new BD(contexto);

        // obtiene las coordenadas de la base de datos
        ArrayList<Imagen> listaImagenes = bd.fotoObtenerTodas();

        bd.cerrar();

        // inicialmente
        // mueve la marca a la posicion conocida


        return listaImagenes;
    }


    private class AdaptadorRegistro extends ArrayAdapter {
        private ArrayList<Imagen> listaImagenes;

        public AdaptadorRegistro(Context context, ArrayList<Imagen> listaImagenes) {
            super(context, R.layout.registros_imagenesitem, listaImagenes);
            this.listaImagenes= listaImagenes;
        }

        @Override
        public View getView(int posicion, View convertView, ViewGroup parent) {

            View elemento = pantalla.getActivity().getLayoutInflater().inflate(R.layout.registros_imagenesitem, null);

            Pantalla e = new Pantalla(elemento);

            Imagen imagen = listaImagenes.get(posicion);

            e.getImageView(R.id.registros_imagen_foto).setImageBitmap(decodificar(imagen.getImagen()));


            FechaHora fechaHora = new FechaHora(imagen.getFechaHora());
            e.setTextView(R.id.registros_fechahora, fechaHora.obtenerFechaHoraNormal());



            if ( imagen.getEnviado() )
                e.setVisible(R.id.registros_imagenestado);
            else
                e.setInvisible(R.id.registros_imagenestado);

            return elemento;
        }



    }


}
