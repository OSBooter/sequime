package pc.javier.seguime.control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import pc.javier.seguime.adaptador.BD;
import pc.javier.seguime.adaptador.Coordenada;
import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.adaptador.Servidor;
import utilidades.Alarma;
import utilidades.basico.Evento;
import utilidades.basico.MensajeRegistro;
import utilidades.basico.Temporizador;


/*
    Controla el servicio de localizador
    Controla alarma

 */





public class ServicioAplicacion extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        iniciarServicio();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        detenerServicio();
        super.onDestroy();
    }






    // -------------------------------------------------------------------



    private Preferencias preferencias;
    private Intent servicioLocalizador;
    private Temporizador temporizadorLocalizacion;
    private Temporizador temporizadorAlarma;
    private Temporizador temporizadorInternet;



    // inicia este servicio
    private void iniciarServicio () {
        // configuración del temporizadorLocalizacion de localización
        preferencias = new Preferencias(this);


        // conexión a internet
        if (eventoInternet  == null)
            eventoInternet = (new EnlaceEventos(this)).obtenerEventoConexionServidor();

        iniciarTemporizadorLocalización();
        iniciarTemporizadorAlarma();
        iniciarTemporizadorInternet();
    }


    // finaliza este servicio
    private void detenerServicio () {
        detenerTemporizadorLocalizacion();
        detenetTemporizadorInternet ();
        detenerTemporizadorAlarma();
    }







    // ----------------- localización ----------------------------------

    private void iniciarTemporizadorLocalización () {

        // se lee al revés por la lógica del temporizadorLocalizacion (periodo / duración -> actividad / inactividad)
        int intervaloInactivo = preferencias.getIntervaloActividad();
        int intervaloActivo = preferencias.getIntervaloInactividad();

        // modo siempre activo
        // si uno de los valores del temporizadorLocalizacion es 0 (cero) se activa el localizador directamente, sin temporizadorLocalizacion
        if ((intervaloActivo <= 0) || (intervaloInactivo <= 0)) {
            mensajeLog("modo siempre activo");
            iniciarLocalizacion();
            return;
        }

        if (preferencias.getRastreo()) {
            mensajeLog("modo siempre activo [rastreo]");
            iniciarLocalizacion();
            return;
        }



        // temporizadorLocalizacion
        temporizadorLocalizacion = new Temporizador() {
            @Override
            public void ejecutarTarea() { iniciarLocalizacion(); }
            @Override
            public void ejecutarTareaAlFinalizar () {
                if (preferencias.getRastreo()) {
                    mensajeLog("temporizador no puede detener la localización [rastreo activo]");
                    return;
                }
                detenerLocalizacion(); }
        };
        temporizadorLocalizacion.setIntervalo(intervaloActivo);
        temporizadorLocalizacion.setDuracion(intervaloInactivo);
        temporizadorLocalizacion.iniciar();
        mensajeLog("temporizadorLocalizacion de localización iniciado  ");
    }


    private void detenerTemporizadorLocalizacion () {
        if (temporizadorLocalizacion != null)
            temporizadorLocalizacion.detener();
        detenerLocalizacion();
    }



    /// uso del temporizador ------------

    // inicia el servicio secundario (localización)
    private void iniciarLocalizacion () {
        mensajeLog("temporizadorLocalizacion intenta activar el localizador ");
        servicioLocalizador = new Intent(this, ServicioLocalizador.class);
        startService(servicioLocalizador);
        mensajeLog(">> activa el localizador ");

        // realiza una conexión a internet
        // conectarInternet();
    }

    // detiene el servicio de localización
    private void detenerLocalizacion () {
        mensajeLog(">> DESactiva el localizador ");
        stopService(servicioLocalizador);
    }

    // -------------------------------------------






















    // ----------------- alarma ----------------------------------


    Evento eventoAlarma;
    private void iniciarTemporizadorAlarma () {

        long activacion = preferencias.getAlarma();

        if (activacion == 0) return;

        eventoAlarma = (new EnlaceEventos(this)).obtenerEventoAlarma ();

        Alarma alarma = new Alarma();
        alarma.setFin(activacion);
        if (alarma.activada()) {
            mensajeLog("------ activa ALARMA");
            activarAlarma();

            return;
        }

        mensajeLog( "temporizador configurado en " + alarma.getSegundosRestantes());
        mensajeLog( "alarma configurado en " + alarma.getFin().toString());



        temporizadorAlarma = new Temporizador() {
            @Override
            public void ejecutarTarea () {
                mensajeLog("temporizador activa ALARMA --------------");
                activarAlarma ();
            }
        };

        temporizadorAlarma.setIntervalo(alarma.getSegundosRestantes());
        temporizadorAlarma.setRetraso(alarma.getSegundosRestantes());
        temporizadorAlarma.iniciar();



    }





    private void activarAlarma () {
        mensajeLog("********* ALARMA ACTIVADA *********");
        if (temporizadorAlarma != null)
            temporizadorAlarma.detener();
        preferencias.setRastreo(true);
        preferencias.borrar(Preferencias.TipoPreferencia.alarma);
        //preferencias.setUltimaAlarmaActivada (alarma.ahora());

        if (temporizadorLocalizacion != null)
            temporizadorLocalizacion.detener();

        // reactiva el servicio - para que tome la configuración de rastreo
        detenerLocalizacion();
        iniciarLocalizacion();

        eventoAlarma.emitir("activar");
    }




    private void detenerTemporizadorAlarma () {
        if (temporizadorAlarma != null)
            temporizadorAlarma.detener();
        if (eventoAlarma != null)
            eventoAlarma.quitarHandlers();
    }







    // ---------------------



    // internet

    Evento eventoInternet;
    private void iniciarTemporizadorInternet () {

        int intervalo = preferencias.getIntervaloInternet();

        if (intervalo <= 0)
            return;

        if (eventoInternet  == null)
            eventoInternet = (new EnlaceEventos(this)).obtenerEventoConexionServidor();

        temporizadorInternet = new Temporizador() {
            @Override
            public void ejecutarTarea() { conectarInternet (); }
        };
        temporizadorInternet.setIntervalo(intervalo);
        temporizadorInternet.iniciar();
        mensajeLog("temporizadorInternet activado ");

    }


    private void conectarInternet () {
        if (!preferencias.getSesionIniciada())
            return;
        Servidor servidor = new Servidor(this);
        servidor.setEvento(eventoInternet);

        // obtiene la ultima coordenada no enviada
        BD bd = new BD(this);
        Coordenada coordenadaUltima = bd.coordenadaObtenerUltima();
        Coordenada coordenadaNoEnviada = bd.coordenadaObtenerUltimaNoEnviada();
        bd.cerrar();

        servidor.agregarCoordenada(coordenadaNoEnviada);

        // verifica si la coordenada no enviada es la misma que la ultima coordenada
        // para saber si es una posición actual
        if (coordenadaNoEnviada != null)
            if (coordenadaUltima != null)
                if (coordenadaUltima.getId() != coordenadaNoEnviada.getId())
                    servidor.agregarParametro(Servidor.Parametro.posicionHistorial, "true");
        servidor.enviar();
    }

    private void detenetTemporizadorInternet () {
        if (temporizadorInternet != null)
            temporizadorInternet.detener();
    }


    private void mensajeLog (String texto) {
        MensajeRegistro.msj("Servicio APLICACION", texto);
    }


}
