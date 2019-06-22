package pc.javier.seguime.adaptador;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Javier 2019.
 * Manejador de preferencias
 * almacena y recupera preferencias de la aplicación
 */

public class Preferencias extends utilidades.basico.Preferencias {


    public Preferencias(Context contexto) {
        super(contexto);
    }

    /*public Preferencias(Context contexto, String nombrePreferencias) {
        super(contexto, nombrePreferencias);
    }*/



    public void guardar(TipoPreferencia clave, String valor) {
        guardar(clave.string(), valor);
    }

    public void guardar(TipoPreferencia clave, int valor) {
        guardar(clave.string(), valor);
    }

    public void guardar(TipoPreferencia clave, boolean valor) {
        guardar(clave.string(), valor);
    }

    public void guardarLong(TipoPreferencia clave, long valor) {
        guardarLong(clave.string(), valor);
    }


    public int obtenerInt(TipoPreferencia clave) {
        return obtenerInt(clave.string());
    }

    public String obtenerString(TipoPreferencia clave) {
        return obtenerString(clave.string());
    }

    public boolean obtenerBoolean(TipoPreferencia clave) {
        return obtenerBoolean(clave.string());
    }

    public long obtenerLong(TipoPreferencia clave) {
        return obtenerLong(clave.string());
    }


    public void borrar(TipoPreferencia clave) {
        borrar(clave.string());
    }


    @Override
    public void configuracionInicial() {

        if (preferencias().getBoolean(TipoPreferencia.configuracionInicial.string(), true) == false)
            return;


        SharedPreferences.Editor editor = preferencias().edit();

        editor.putBoolean(TipoPreferencia.configuracionInicial.string(), false);


        editor.putString(TipoPreferencia.servidor.string(), Constante.urlServidor);
        editor.putString(TipoPreferencia.usuario.string(), "");
        editor.putString(TipoPreferencia.clave.string(), "");

        editor.putInt(TipoPreferencia.intervaloActividad.string(), 60);
        editor.putInt(TipoPreferencia.intervaloInactividad.string(), 600);
        editor.putString(TipoPreferencia.idTelegram.string(), "");
        editor.putString(TipoPreferencia.numeroSms.string(), "");
        editor.putInt(TipoPreferencia.intervaloInternet.string(), 600);

        editor.putBoolean(TipoPreferencia.sesionIniciada.string(), false);
        editor.commit();
    }





    public enum TipoPreferencia {


        usuario("usuario"),
        clave("clave"),
        servidor("urlServidor"),
        idTelegram("telegram"),
        sesionIniciada("sesionIniciada"),

        numeroSms("sms"),

        intervaloActividad("actvidad"),
        intervaloInactividad("inactividad"),
        mensaje("msj"),
        bloqueado("bloqueado"),
        rastreo("rastreo"),
        iniciarConSistema("iniciarConSistema"),
        configuracionInicial("configuracionInicial"),
        notificacion("notificacion"),
        servicioActivo("activo"),
        menuInicial("menuInicial"),
        versionRegistrada("versionRegistrada"),
        activarDesbloquear("activarDesbloquear"),
        presentacionInicial("presentacionInicial"),
        intervaloInternet("intervaloInternet"),

        alarmaServidor("alarmaServidor"),
        alarma("alarma"),
        alarmaMensaje("alarmaMensaje"),
        alarmaUltima("alarmaUltima"),

        permitirConfiguracionSMS("permitirConfiguracionSMS"),
        conectarRedesAbiertas("conectarRedesAbiertas"),

        conexionInfo ("conexionInfo"),
        conexionTipo("conexionTipo"),
        enviarDatosDeConexion ("enviarDatosDeConexion")

        ;


        private final String preferencia;

        TipoPreferencia(String preferencia) {
            this.preferencia = preferencia;
        }

        public String string() {
            return preferencia;
        }


    }














    public String getClave() { return obtenerString (TipoPreferencia.clave); }

    public void setClave(String clave) { guardar (TipoPreferencia.clave, clave); }

    public String getUsuario() { return obtenerString (TipoPreferencia.usuario); }

    public void setUsuario(String usuario) { guardar (TipoPreferencia.usuario, usuario); }

    public String getServidor() { return obtenerString (TipoPreferencia.servidor); }

    public void setServidor(String servidor) { guardar (TipoPreferencia.servidor, servidor); }

    public String getNumeroSms() { return obtenerString (TipoPreferencia.numeroSms); }

    public void setNumeroSms(String numeroSms) { guardar (TipoPreferencia.numeroSms, numeroSms); }

    public String getIdTelegram() { return obtenerString (TipoPreferencia.idTelegram); }

    public void setIdTelegram(String idTelegram) { guardar (TipoPreferencia.idTelegram, idTelegram); }

    public int getIntervaloActividad() { return obtenerInt (TipoPreferencia.intervaloActividad); }

    public void setIntervaloActividad(int intervaloActividad) { guardar (TipoPreferencia.intervaloActividad, intervaloActividad); }

    public int getIntervaloInactividad() { return obtenerInt (TipoPreferencia.intervaloInactividad); }

    public void setIntervaloInactividad(int intervaloInactividad) { guardar (TipoPreferencia.intervaloInactividad, intervaloInactividad); }

    public String getMensaje() { return obtenerString (TipoPreferencia.mensaje); }

    public void setMensaje(String mensaje) { guardar (TipoPreferencia.mensaje, mensaje); }

    public String getNotificacion() { return obtenerString (TipoPreferencia.notificacion); }

    public void setNotificacion(String notificacion) { guardar (TipoPreferencia.notificacion, notificacion); }

    public boolean getBloqueado() { return obtenerBoolean (TipoPreferencia.bloqueado); }

    public void setBloqueado(boolean bloqueado) { guardar (TipoPreferencia.bloqueado, bloqueado); }

    public boolean getRastreo() { return obtenerBoolean (TipoPreferencia.rastreo); }

    public void setRastreo(boolean rastreo) { guardar (TipoPreferencia.rastreo, rastreo); }

    public boolean getIniciarConSistema() { return obtenerBoolean (TipoPreferencia.iniciarConSistema); }

    public void setIniciarConSistema(boolean iniciarConSistema) { guardar (TipoPreferencia.iniciarConSistema, iniciarConSistema); }

    public boolean getConfiguracionInicial() { return obtenerBoolean (TipoPreferencia.configuracionInicial); }

    public void setConfiguracionInicial(boolean configuracionInicial) { guardar (TipoPreferencia.configuracionInicial, configuracionInicial); }

    public boolean getServicioActivo() { return obtenerBoolean (TipoPreferencia.servicioActivo); }

    public void setServicioActivo(boolean servicioActivo) { guardar (TipoPreferencia.servicioActivo, servicioActivo); }

    public boolean getSesionIniciada() { return obtenerBoolean (TipoPreferencia.sesionIniciada); }

    public void setSesionIniciada(boolean sesionIniciada) { guardar (TipoPreferencia.sesionIniciada, sesionIniciada); }

    public boolean getMenuInicial() { return obtenerBoolean (TipoPreferencia.menuInicial); }

    public void setMenuInicial(boolean menuInicial) { guardar (TipoPreferencia.menuInicial, menuInicial); }

    public boolean getVersionRegistrada() { return obtenerBoolean (TipoPreferencia.versionRegistrada); }

    public void setVersionRegistrada(boolean versionRegistrada) { guardar (TipoPreferencia.versionRegistrada, versionRegistrada); }

    public boolean getActivarConPantalla() { return obtenerBoolean (TipoPreferencia.activarDesbloquear); }

    public void setActivarConPantalla(boolean activarConPantalla) { guardar (TipoPreferencia.activarDesbloquear, activarConPantalla); }

    public boolean getAlarmaServidor() { return obtenerBoolean (TipoPreferencia.alarmaServidor); }

    public void setAlarmaServidor(boolean alarmaServidor) { guardar (TipoPreferencia.alarmaServidor, alarmaServidor); }

    public boolean getPresentacionInicial() { return obtenerBoolean (TipoPreferencia.presentacionInicial); }

    public void setPresentacionInicial(boolean presentacionInicial) { guardar (TipoPreferencia.presentacionInicial, presentacionInicial); }

    public long getAlarma() { return obtenerLong (TipoPreferencia.alarma); }

    public void setAlarma(long alarma) { guardarLong (TipoPreferencia.alarma, alarma); }

    public String getAlarmaMensaje() { return obtenerString (TipoPreferencia.alarmaMensaje); }

    public void setAlarmaMensaje(String mensaje) { guardar (TipoPreferencia.alarmaMensaje, mensaje); }

    public long getAlarmaUltima() { return obtenerLong (TipoPreferencia.alarmaUltima); }

    public void setAlarmaUltima(long alarma) { guardarLong (TipoPreferencia.alarmaUltima, alarma); }

    public int getIntervaloInternet () { return obtenerInt (TipoPreferencia.intervaloInternet); }

    public void setIntervaloInternet (int intervalo) { guardar (TipoPreferencia.intervaloInternet, intervalo); }


    /*public void setConexionInfo (boolean estado) { guardar (TipoPreferencia.conexionInfo, estado); }

    public String getConexionInfo () { return obtenerString(TipoPreferencia.conexionInfo); }


    public void setConexionTipo (String tipo) { guardar(TipoPreferencia.conexionTipo, tipo); }

    public String getConexionTipo () { return obtenerString (TipoPreferencia.conexionTipo); }
    */


    public boolean getEnviarDatosDeConexion () { return obtenerBoolean(TipoPreferencia.enviarDatosDeConexion); }
    public void setEnviarDatosDeConexion (boolean estado) { guardar (TipoPreferencia.enviarDatosDeConexion, estado); }


    public boolean getPermitirConfigurarSMS () { return obtenerBoolean(TipoPreferencia.permitirConfiguracionSMS); }
    public void setPermitirConfigurarSMS (boolean estado) { guardar (TipoPreferencia.permitirConfiguracionSMS, estado); }

    public boolean getConectarRedesAbiertas () { return obtenerBoolean(TipoPreferencia.conectarRedesAbiertas); }
    public void setConectarRedesAbiertas (boolean estado) { guardar (TipoPreferencia.conectarRedesAbiertas, estado); }

}
