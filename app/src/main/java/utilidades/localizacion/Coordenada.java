package utilidades.localizacion;


import java.util.Date;

/**
 * Javier 2019.
 */



public class Coordenada {
    protected double latitud;
    protected double longitud;
    protected String proveedor;
    protected float velocidad = 0;
    protected Date fechaHora = new Date();
    protected String codigo;

    public Coordenada (){
        codigo = String.valueOf(fechaHora.getTime());
    }


    public Coordenada (double latitud, double longitud) {
        setLatitud(latitud);
        setLongitud(longitud);
        codigo = String.valueOf(fechaHora.getTime());
    }



    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public Date getFechaHora () {
        return fechaHora;
    }

    public  void setFechaHora (Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
