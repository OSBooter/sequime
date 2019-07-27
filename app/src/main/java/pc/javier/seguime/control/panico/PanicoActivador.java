package pc.javier.seguime.control.panico;

import pc.javier.seguime.adaptador.Preferencias;
import pc.javier.seguime.control.Control;
import pc.javier.seguime.control.ServicioAplicacion;
import utilidades.basico.MensajeRegistro;
import utilidades.basico.Panico;


/**
 * Created by Javier on 25/7/2019.
 *
 * Botón de pánico
 * Activa la aplicación
 */

public class PanicoActivador extends Panico {


    Control control;
    Class servicio = ServicioAplicacion.class;


    @Override
    public void activar() {

        control = new Control(this);
        iniciar();
    }


    public void iniciar () {

        MensajeRegistro.msj(this, "Botón activado");

        Preferencias preferencias = new Preferencias(this);

        if (preferencias.getPanicoPermitir() == false)
            return;




        if (control.servicioActivo(servicio))
            control.detenerServicio(servicio);

        preferencias.setRastreo(true);



        control.iniciarServicio(servicio);

        if (preferencias.getPanicoBloquear())
            if (preferencias.getSesionIniciada())
                preferencias.setBloqueado(true);




        MensajeRegistro.msj(this, "ACTIVADO");

    }


}
