package pc.javier.seguime.control.receptor;

import android.content.Context;

import pc.javier.seguime.adaptador.Constante;
import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Servidor;
import pc.javier.seguime.control.EnlaceEventos;
import utilidades.FechaHora;

/**
 * Javier 2019.
 * Recibe coordenadas y las envia al Servidor
 */

public class ReceptorCoordenadasInternet extends ReceptorCoordenadas {


    private Context contexto;
    public ReceptorCoordenadasInternet(String clave, Context contexto) {
        super(clave);

        this.contexto = contexto;

    }


    @Override
    protected void procesarCoordenada(Coordenada coordenada) {
        Servidor servidor = new Servidor( contexto);


        // ver- / ésto debería estar en EnlaceEventos, y obtener el servidor por parametro
        EnlaceEventos enlaceEventos = new EnlaceEventos(contexto);
        servidor.setEvento(enlaceEventos.obtenerEventoConexionServidor());

        agregarParametro(servidor, coordenada);

        servidor.enviar();
    }



    private void agregarParametro (Servidor servidor, Coordenada coordenada) {
        servidor.agregarParametro (Servidor.Parametro.latitud, String.valueOf(coordenada.getLatitud()));
        servidor.agregarParametro (Servidor.Parametro.longitud, String.valueOf(coordenada.getLongitud()));
        servidor.agregarParametro (Servidor.Parametro.velocidad, String.valueOf(coordenada.getVelocidad()));
        servidor.agregarParametro (Servidor.Parametro.id, String.valueOf(coordenada.getId()));
        servidor.agregarParametro (Servidor.Parametro.proveedor, coordenada.getProveedor());
        servidor.agregarParametro (Servidor.Parametro.codigo, coordenada.getCodigo());
        FechaHora fechaHora = new FechaHora(coordenada.getFechaHora());
        servidor.agregarParametro (Servidor.Parametro.fecha, fechaHora.zonaEspecifica(Constante.zonaHorariaServidor).obtenerFechaHoraFormatoBD());
    }



/*

    private void agregarInformacionExtra () {
        Preferencias preferencias = new Preferencias(contexto);
        // si no hay datos de telegram, sale
        if (preferencias.getIdTelegram().isEmpty())
            return;

        // agregar dirección telegram
        servidor.agregarParametro(Servidor.Parametro.telegram, preferencias.getIdTelegram());

        Long alarma = preferencias.getAlarma();
        // envia datos de la alarma al servidor
        if (alarma != 0) {
            FechaHora fechaHoraAlarma = new FechaHora(alarma);
            servidor.agregarParametro(Servidor.Parametro.alarma, fechaHoraAlarma.zonaEspecifica(Constante.zonaHorariaServidor).obtenerFechaHoraFormatoBD());
            servidor.agregarParametro(Servidor.Parametro.texto, preferencias.getAlarmaMensaje());
        }

        // borra la alarma del servidor
        else
        if (preferencias.getAlarmaServidor())
            servidor.agregarParametro(Servidor.Parametro.alarma, "");

    }
*/

}
