package utilidades.basico;


/**
 * Javier 2019.
 */

public class TemporizadorEventos extends Temporizador {


    private Evento evento = null;
    private String parametroEmitir  = "temporizador";

    public void ejecutarTarea() {
        if (evento != null)
            evento.emitir(parametroEmitir);
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getParametroEmitir() {
        return parametroEmitir;
    }

    public void setParametroEmitir(String parametroEmitir) {
        this.parametroEmitir = parametroEmitir;
    }
}
