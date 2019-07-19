package pc.javier.seguime.control;

import java.util.Date;

import utilidades.basico.MensajeRegistro;

/**
 * Created by Javier on 17/07/2019.
 */


public class Imagen {
    private String imagen;
    private Date fechaHora;
    private boolean enviado;
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    private void mensajeLog(String texto) {
        MensajeRegistro.msj("Control pantalla Registro", texto);
    }

}