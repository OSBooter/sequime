package pc.javier.seguime.control;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pc.javier.seguime.R;
import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Pantalla;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.vista.PantallaRegistros;

import utilidades.FechaHora;
import utilidades.localizacion.Mapa;
import utilidades.MensajeRegistro;
import utilidades.Velocidad;

/**
 * Javier 2019.
 */

public class ControlPantallaRegistros extends Control {


    private PantallaRegistros pantalla;
    private Mapa mapa;
    private Context contexto;

    public ControlPantallaRegistros(PantallaRegistros pantalla, Preferencias preferencias) {
        super(pantalla, preferencias);

        this.preferencias = preferencias;
        this.pantalla = pantalla;
        this.contexto = pantalla.getActivity();

        // Evita bloueo
        //Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        mapa = new Mapa(pantalla.mapa());

        cargarVista();
        cargarListener () ;
    }






    // evento de click, cuando el usuario selecciona un elemento de la lista de coordenadas
    private void cargarListener () {
        pantalla.lista().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptador, View view, int posicion, long id) {

                Coordenada coordenada = (Coordenada) adaptador.getItemAtPosition(posicion);
                mapa.moverMarca(
                        coordenada.getLatitud(),
                        coordenada.getLongitud(),
                        coordenada.getFechaHora()
                               + " \r\n" +
                                coordenada.getVelocidad());

        }});
    }












    // carga el list view
    private void cargarVista () {
        ArrayList<Coordenada> listaCoordenadas = obtenerCoordenadas();
        AdaptadorRegistro adaptador = new AdaptadorRegistro(contexto,  listaCoordenadas);
        pantalla.lista().setAdapter(adaptador);
    }








    // carga una lista de datos (registros) que servira para el list view
    // obtiene coordenadas de la base de datos
    private ArrayList<Coordenada> obtenerCoordenadas () {
        // base de datos
        BD bd = new BD(contexto);

        // obtiene las coordenadas de la base de datos
        ArrayList<Coordenada> listaCoordenada = bd.coordenadaObtenerTodas();

        bd.cerrar();

        // inicialmente
        // mueve la marca a la posicion conocida


        return listaCoordenada;
    }










    private class AdaptadorRegistro extends ArrayAdapter {
        private ArrayList<Coordenada> listaCoordenadas;

        public AdaptadorRegistro(Context context, ArrayList<Coordenada> listaCoordenadas) {
            super(context, R.layout.registrositem, listaCoordenadas);
            this.listaCoordenadas = listaCoordenadas;
        }

        @Override
        public View getView(int posicion, View convertView, ViewGroup parent) {

            View elemento = pantalla.getActivity().getLayoutInflater().inflate(R.layout.registrositem,null);

            Pantalla e = new Pantalla(elemento);

            Coordenada coordenada = listaCoordenadas.get(posicion);

            e.setTextView(R.id.registros_latitud, string(coordenada.getLatitud()));
            e.setTextView(R.id.registros_longitud, string(coordenada.getLongitud()));
            e.setTextView(R.id.registros_extra, coordenada.getCodigo() + " " + coordenada.getRecibido());

            FechaHora fechaHora = new FechaHora(coordenada.getFechaHora());
            e.setTextView(R.id.registros_fechahora, fechaHora.obtenerFechaHoraNormal());

            Velocidad velocidad = new Velocidad(coordenada.getVelocidad());
            e.setTextView(R.id.registros_velocidad, velocidad.kmhString());


            if ( coordenada.getRecibido() )
                e.setVisible(R.id.registros_imagenestado);
            else
                e.setInvisible(R.id.registros_imagenestado);
                //e.getImageView(R.id.registros_imagenestado).setVisibility(View.VISIBLE);
            // else elemento.setBackgroundColor(e.colorGris());




            return elemento;
        }

        private String string (double valor) {
            return String.valueOf(valor);
        }

    }



    private void mensajeLog (String texto) {
        MensajeRegistro.msj("Control pantalla Registro", texto);
    }


}
