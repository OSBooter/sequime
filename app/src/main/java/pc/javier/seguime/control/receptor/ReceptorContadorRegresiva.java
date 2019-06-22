package pc.javier.seguime.control.receptor;

import pc.javier.seguime.vista.PantallaRegresiva;
import utilidades.Alarma;
import utilidades.basico.MensajeRegistro;
import utilidades.basico.ReceptorEventos;

/**
 * Javier 2019.
 *  actualiza la pantalla de la cuenta regresiva, muestra tiempo restante
 */

public class ReceptorContadorRegresiva extends ReceptorEventos {
    PantallaRegresiva pantalla;
    Alarma alarma;

    public ReceptorContadorRegresiva(String clave, PantallaRegresiva pantalla) {
        super(clave);
        this.pantalla = pantalla;
        alarma= new Alarma();

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
