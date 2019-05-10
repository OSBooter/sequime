package pc.javier.seguime.adaptador;

import android.content.Context;
import utilidades.ConexionHTTP;
import utilidades.Evento;
import utilidades.FechaHora;

/**
 * Javier 2019.
 * envia datos al urlServidor
 * las respuesta las recibe el objeto Evento
 */

public class Servidor {
    private String url;
    private String usuario;
    private String clave;
    private String parametros = "";
    private Evento evento;
    private Preferencias preferencias;


    public Servidor (String url, String usuario, String clave) {

        this.url = url;
        this.clave = clave;
        this.usuario = usuario;
        nuevoParametro();
    }

    public Servidor (Context contexto) {
        preferencias = new Preferencias(contexto);
        this.url = preferencias.getServidor();
        this.usuario = preferencias.getUsuario();
        this.clave = preferencias.getClave();
        nuevoParametro();
        agregarInformacionExtra();
    }






    public void enviar () {
        if (url.isEmpty())
            return;

        ConexionHTTP conexion = new ConexionHTTP(url, parametros, evento);
        conexion.setUserAgent(Constante.userAgent);
        conexion.ejecutarHilo();
    }







    private void agregarInformacionExtra () {

        // si no hay datos de telegram, sale
        if (preferencias.getIdTelegram().isEmpty())
            return;

        // agregar dirección telegram
        if (preferencias.getRastreo())
            agregarParametro(Servidor.Parametro.telegram, preferencias.getIdTelegram());

        Long alarma = preferencias.getAlarma();
        // envia datos de la alarma al servidor
        if (alarma != 0) {
            FechaHora fechaHoraAlarma = new FechaHora(alarma);
            agregarParametro(Servidor.Parametro.alarma, fechaHoraAlarma.zonaEspecifica(Constante.zonaHorariaServidor).obtenerFechaHoraFormatoBD());
            agregarParametro(Servidor.Parametro.texto, preferencias.getAlarmaMensaje());

            agregarParametro(Servidor.Parametro.telegram, preferencias.getIdTelegram());
        }

        // borra la alarma del servidor
        else
        if (preferencias.getAlarmaServidor())
            agregarParametro(Servidor.Parametro.alarma, "");

    }



// ----------


    public void nuevoParametro () {
        parametros = "usuario="+usuario;

        agregarParametro(Parametro.clave, clave);
    }

    public void agregarParametro (Parametro parametro, String valor) {
        parametros = parametros + "&" + parametro.toString() + "=" + valor;
    }



    public void agregarComando (Comando comando) {
        agregarParametro(Parametro.comando, comando.toString());
    }




    public void agregarTelegram (String id) {
        agregarParametro(Parametro.telegram, id);
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    public enum Parametro {
        comando,
        usuario,
        clave,

        latitud, longitud, fecha, id, velocidad, proveedor, codigo,

        telegram,
        alarma, texto,
    }

    public enum Comando {
        registro, sesion
    }
}
