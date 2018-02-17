package pc.javier.seguime.control;

import android.telephony.SmsManager;

/**
 * Created by Javier on 24 ene 2018.
 *
 * Envia un SMS
 */


public class Sms {
    private String numero;
    private String mensaje;

    public Sms (String numero, String mensaje) {
        this.mensaje = mensaje;
        this.numero = numero;
    }

    public boolean enviar () {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, mensaje, null, null);
            // mensaje enviado
            return true;
        } catch (Exception e) {
            // error al enviar mensaje
            return false;
        }
    }






}
