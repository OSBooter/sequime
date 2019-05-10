package utilidades;

import android.telephony.SmsManager;

import java.util.Date;

/**
 * Javier 2019.
 *
 * Envia un SMS
 */


public class SMS {
    private String numero;
    private String mensaje;
	private boolean enviado = false;
    private String mensajeError;
    private Date fechaCreacion;
    private Date fechaEnviado;

    public SMS(String numero, String mensaje) {
        this.mensaje = mensaje;
        this.numero = numero;
        fechaCreacion = new Date();
    }

    public void enviar () {
        fechaEnviado = new Date();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, mensaje, null, null);
            // msj enviado
			enviado = true;
            mensajeError = "";
            return;
        } catch (Exception e) {
            // error al enviar msj
			enviado = false;
            mensajeError = e.getMessage();
            return;
        }
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public String getMensajeEstado() {
        return mensajeError;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaEnviado() {
        return fechaEnviado;
    }


}
