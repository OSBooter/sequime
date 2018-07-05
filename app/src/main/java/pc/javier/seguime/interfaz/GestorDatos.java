package pc.javier.seguime.interfaz;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Javier on 1 abr 2018.
 *  maneja datos que deben ser enviados al servidor
 */

public class GestorDatos {
    private SharedPreferences preferencias;
    private String usuario;
    private String clave;
    String servidor;
    private Handler handler;

    public GestorDatos () {
        this.preferencias = Aplicacion.Preferencias();
        if (preferencias == null)
            preferencias = Aplicacion.contexto.getSharedPreferences("preferencias",MODE_PRIVATE);

        usuario = preferencias.getString("usuario", "");
        clave = preferencias.getString("clave", "");
        servidor = preferencias.getString("servidor", "");
        handler = Aplicacion.handler;
    }

    public  void EnviarAlarma () {
        String parametros = "";
        parametros = "comando=alarma" ;
        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;
        String telegram = preferencias.getString("telegram", "");
        Temporizador temporizador = new Temporizador();
        temporizador.DefinirActivacion(Aplicacion.alarma());


        if (Aplicacion.alarmaExiste())
            if (telegram != "")
                parametros = parametros +"&" + "contacto=" + telegram + "&alarma=" + temporizador.FechaHoraServidorString() +  "&texto="+Aplicacion.preferenciaCadena("alarmatexto");

        if (Aplicacion.alarmaServidor().equals("eliminar"))
            parametros = parametros + "&alarma=0";


        mensajeLog ( "Enviando ALARMA: " + parametros);

        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();
    }


    public  void ActivarAlarma () {
        String parametros = "";
        parametros = "comando=alarma" ;
        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;
        String telegram = preferencias.getString("telegram", "");

        if (Aplicacion.alarmaExiste())
            if (telegram != "")
                parametros = parametros +"&" + "contacto=" + telegram + "&alarma=activar&texto="+Aplicacion.preferenciaCadena("alarmatexto");


        mensajeLog ( "Activando ALARMA: " + parametros);

        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();
    }


    public void EnviarNada () {
        String parametros = "";

        parametros = "comando=ping" ;
        parametros = "clave=" + clave + "&" + parametros;
        parametros = "usuario=" + usuario + "&" + parametros;



        mensajeLog ( "Enviando NADA: " + parametros);

        Internet conexion = new Internet(servidor, parametros, handler);
        conexion.conectar();
    }

    private void mensajeLog (String texto) {
        Log.d("Gestor de Datos", texto);
    }
}
