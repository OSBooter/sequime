package utilidades.basico;


import utilidades.eventos.BolaDeEventos;

/**
 * Javier 2019.
 */

public class TemporizadorEventos extends Temporizador {



    private String parametroEmitir  = "temporizador";

    public void ejecutarTarea() {
        BolaDeEventos bola = new BolaDeEventos();
        bola.agregarDato("TEMPORIZADOR", parametroEmitir);
        bola.lanzar();
    }


    public String getParametroEmitir() {
        return parametroEmitir;
    }

    public void setParametroEmitir(String parametroEmitir) {
        this.parametroEmitir = parametroEmitir;
    }
}
