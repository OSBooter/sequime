package utilidades;

import java.lang.reflect.Field;

/**
 * Javieer 2019.
 */




public class Reflexion {

    private String nombreClase = "";


    public Reflexion(String nombreClase) {
        this.nombreClase = nombreClase;
    }




    public Object obtenerObjeto() {

        if (nombreClase.isEmpty())
            return null;

        Class clase;
        Object objeto = null;

        try {
            clase = Class.forName(nombreClase);
            objeto = clase.newInstance();
        }
        catch (Exception ignored) {        }
        return objeto;
    }





























    public void procesarObjeto (Object objeto) {

        if (objeto == null)
            return;

        Field[] propiedades;
        propiedades = objeto.getClass().getDeclaredFields();

        try {

            for (Field propiedad: propiedades) {

                String nombreTipo = propiedad.getType().getSimpleName();

                if (nombreTipo.equals("int")) {
                    System.out.println("Escribe El Valor  que guardara en " + propiedad.getName() + " (Solo Numeros Enteros) ");
                    propiedad.set(objeto, Integer.parseInt("000"));
                }

            }



        } catch (Exception ex) {        }
    }
}