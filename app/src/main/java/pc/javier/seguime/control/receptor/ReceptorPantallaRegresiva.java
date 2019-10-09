package pc.javier.seguime.control.receptor;

import pc.javier.seguime.adaptador.Aplicacion;
import pc.javier.seguime.vista.PantallaRegresiva;
import utilidades.Alarma;
import utilidades.basico.MensajeRegistro;
import utilidades.eventos.ReceptorDeEventos;


/**
 * Javier 2019.
 *  actualiza la pantalla de la cuenta regresiva, muestra tiempo restante
 */

public class ReceptorPantallaRegresiva extends ReceptorDeEventos {

    public final static String CLAVE_EVENTO = "REGRESIVA";

    PantallaRegresiva pantalla;
    Alarma alarma;

    public ReceptorPantallaRegresiva(PantallaRegresiva pantalla) {
        this.pantalla = pantalla;
        alarma= new Alarma();
        clave = CLAVE_EVENTO;
        objetivo = Aplicacion.EV_REGRESIVA;
    }


    @Override
    public void procesar (String dato) {
            mostrarContador(Long.parseLong(dato));
    }


    private void mostrarContador (Long dato) {
        alarma.setFin(dato);
        MensajeRegistro.msj(alarma.getFin().toString());
        MensajeRegistro.msj(alarma.getFin().getTime());
        pantalla.contador(alarma.getHoras(), alarma.getMinutos(), alarma.getSegundos());
        // if (alarma.activada())  pantalla.dibujarBoton(false);
    }



}
