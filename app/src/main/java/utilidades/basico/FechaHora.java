package utilidades.basico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Javier 01/04/2019.
 *
 * Se asigna un momento espefico y realiza operaciones con ese instante de tiempo
 */



/**
 * Notas
 *
 * Si falla, revisar objetos DATE . momento y momentoOriginal deben ser dos objetos disintos, no uno solo.
 */



public class FechaHora {
    private Date momento;             // almacena la fecha y hora


    // constructor

    public FechaHora(){
        ahora();
    }

	public FechaHora(long milisegundos){
        ahora();
        establecer (milisegundos);
    }

    public FechaHora(String milisegundos){
        ahora();
        establecer (milisegundos);
    }

    public FechaHora(Date fecha){
        ahora();
	    establecer (fecha);
    }


    // get y sets

    public Date obtener () {
        return momento;
    }

    public String Obtener (FormatoString formatoString) {
        return formato(formatoString).format(momento);
    }


    public void establecer (long milisegundos) {
        momento.setTime(milisegundos);
    }

    public void establecer (String milisegundosString) {
        long milisegundos;
        try { milisegundos = Long.parseLong(milisegundosString); }
        catch (Exception e) { milisegundos = 0; }
        momento.setTime(milisegundos);
    }

    public void establecer (Date FechaHora) {
        establecer (FechaHora.getTime());
    }


    // devuelve la hora actual
    public String obtenerHora() {
        //String hora = String.valueOf(fecha.getHours()) + ":" + String.valueOf(fecha.getMinutes()) + ":" + String.valueOf(fecha.getSeconds()) ;
        return formato(FormatoString.Hora).format(momento);
    }

    public String obtenerFechaNormal () {
        return formato(FormatoString.ddMMyyyy).format(momento);
    }

    public String obtenerFechaHoraNormal () {
        return formato(FormatoString.ddMMyyyyHHmmss).format(momento);
    }

    public String obtenerHoraFechaNormal () {
        return formato(FormatoString.horaFechaNormal).format(momento);
    }

    public String obtenerFechaInvertida () {
        return formato(FormatoString.yyyyMMdd).format(momento);
    }

    public String obtenerFechaHoraInvertida () {
        return formato(FormatoString.yyyyMMddHHmmss).format(momento);
    }

    public String obtenerFechaHoraFormatoBD() {
        return obtenerFechaHoraInvertida();
    }




    // operaciones

    // suma segundos


    public FechaHora sumar (Date fecha ){
        return sumar(fecha.getTime());
    }

    public FechaHora sumar (long milisegundos ){
        long ini = momento.getTime();
        long fin = milisegundos;
        Long suma= (fin+ini);
        //momento.setTime(suma);
        return new FechaHora(suma);
    }


    public FechaHora restar (Date fecha ){
        return restar(fecha.getTime() );
    }

    public FechaHora restar (long milisegundos) {
        return sumar(0 - milisegundos);
    }







    // ZONAS

	
	/*
	public FechaHora ZonaUTC () {
		TimeZone actual = TimeZone.getDefault();
		long diferencia = 0-actual.getRawOffset();
		FechaHora nuevo = Copia();
		nuevo.Sumar(diferencia);
        return nuevo;
	}

	public FechaHora ZonaEspecifica (long milisegundos) {
		return ZonaUTC().Sumar(milisegundos);
	}
    
	public FechaHora ZonaArgentina () {
		Long milisegundos = 0-(3*60*60*1000) ; // zona horaria argentina
		return ZonaEspecifica (milisegundos);
	}
	*/
	
	

	


    public static final int horaEnSegundos = 60*60;
    public final static int horaEnMilisegundos = horaEnSegundos * 1000;

	public FechaHora zonaUTC () {
		TimeZone actual = TimeZone.getDefault();
		long diferencia = 0-actual.getRawOffset();
		return sumar(diferencia);
	}

	public FechaHora zonaEspecifica (int horas) {
        return zonaUTC().sumar(horas * horaEnMilisegundos);
	}
    
	public FechaHora zonaArgentina () {
		return zonaEspecifica (-3);
	}
	
	public FechaHora copia () {
		FechaHora duplicado = new FechaHora(obtener().getTime());
        return duplicado;
	}
	

    // obtiene la fecha y hora actual
    public void ahora () {
        momento = new Date();
    }
	
	



    // devuelve un simpledateformat
    private SimpleDateFormat formato (FormatoString formatoString) {
        return new SimpleDateFormat(formatoString.obtenerString());
    }


    public enum FormatoString {
		ddMMyyyy ("dd-MM-yyyy"),
		fechaNormal ("dd-MM-yyyy"),
		yyyyMMdd ("yyyy-MM-dd"),
		fechaInvertida ("yyyy-MM-dd"),
		Completo ("dd-MM-yyyy HH:mm:ss"),
		ddMMyyyyHHmmss ("dd-MM-yyyy HH:mm:ss"),
		fechaHoraNormal ("dd-MM-yyyy HH:mm:ss"),
		HHmmssddMMyyyy ("HH:mm:ss dd-MM-yyyy"),
		horaFechaNormal ("HH:mm:ss dd-MM-yyyy"),
		yyyyMMddHHmmss ("yyyy-MM-dd HH:mm:ss"),
		baseDeDatos ("yyyy-MM-dd HH:mm:ss"),
		fechaHoraInvertida ("yyyy-MM-dd HH:mm:ss"),
        HoraMinuto ("HH:mm"),
		HHmmss ("HH:mm:ss"),
		Hora ("HH:mm:ss"),
		HH ("HH"),
		SoloHora ("HH"),
		mm ("mm"),
		SoloMinuto ("mm"),
		ss ("ss"),
		SoloSegundo ("ss"),
		dd ("dd"),
		SoloDia ("dd");

        private final String formato;
        FormatoString (String formato) {
            this.formato = formato;
        }

        public String obtenerString () {
            return formato;
        }


    }
}
