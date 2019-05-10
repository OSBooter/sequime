package pc.javier.seguime;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

import pc.javier.seguime.adaptador.Coordenada;
import utilidades.Alarma;
import utilidades.FechaHora;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {


/*
        Field propiedades[] = (Coordenada.class).getDeclaredFields();
        for (Field x :
                propiedades) {
            System.out.println (x.getName());
        }

        */


    }


    @Test
    public void p() {
        Coordenada coordenada = new Coordenada(22, 34);
        utilidades.localizacion.Coordenada uc = new utilidades.localizacion.Coordenada();
        uc.setLatitud(1);
        uc.setLongitud(2);


        coordenada = (Coordenada) uc;

        mens(coordenada.toString());
        mens(String.valueOf(coordenada.getLongitud()));

    }


    public void mens(String texto) {
        System.out.println(texto);
    }

    public void mens(long texto) {
        System.out.println(String.valueOf(texto));
    }

    public void mens(double texto) {
        System.out.println(String.valueOf(texto));
    }


    @Test
    public void a() {
        Alarma alarma = new Alarma();
        alarma.setInicio(new Date());
        alarma.setFin(new Date((new Date().getTime() + (1))));

        mens(alarma.getInicio().toString());
        mens(alarma.getFin().toString());

        mens(alarma.getMilisegundosRestantes());
        mens(alarma.getMilisegundosTranscurridos());
        mens(String.valueOf(alarma.activada()));


    }


    @Test
    public void dec() {
        double n = 331;


        String texto = String.valueOf(n);
        int punto = texto.lastIndexOf(".");
        int decimales = 3;
        int total = texto.length();


        String r = "";
        for (String x : texto.split("\\.")) {
            System.out.println(x);
        }


        int maximo = decimales + punto;
        if (maximo > total)
            total = total - (maximo - total);
        else
            total = maximo;

        System.out.println(String.format("%.2f", n).replace(",", "."));


    }


    @Test
    public void comando() {
        String dato = " sesion";
        int indice = dato.indexOf(" ");

        System.out.println(indice + "++");


        String comando = dato;
        String parametro = "";

        if (indice > 0) {
            comando = dato.substring(0, indice);
            parametro = dato.substring(indice + 1);
        }


        System.out.println(comando + "++");
        System.out.println(parametro + "++");


    }


    @Test
    public void parse() {
        String x = "Trues";
        boolean b = Boolean.parseBoolean(x);
        System.out.println(b);

    }


    @Test
    public void jay() {
        JSONStringer s = new JSONStringer();

        JSONObject o = new JSONObject();

        JSONTokener t = new JSONTokener("hola");

        System.out.println(t);
    }


    @Test
    public void id () {
        Long i = (new Date().getTime());
        System.out.println(i);
        Random r = new Random();
        System.out.println(r.nextInt(2));

        System.out.println(Long.toHexString(i));
        System.out.println(Long.toOctalString(i));
        System.out.println(Long.toBinaryString(i));


    }



    @Test
    public void id2 () {
        Date d = new Date();






    }










    @Test
    public void fechas () {
        FechaHora f = new FechaHora();
        System.out.println("bd " + f.obtenerFechaHoraFormatoBD());
        System.out.println("normal " + f.obtenerHoraFechaNormal());

        System.out.println("utc" + f.zonaUTC().obtenerHoraFechaNormal());
        System.out.println("normal " + f.obtenerHoraFechaNormal());

        System.out.println("-3 " + f.sumar(-3*60*60*1000).obtenerHoraFechaNormal());
        System.out.println("arg " + f.zonaArgentina().obtenerHoraFechaNormal());

        System.out.println("normal " + f.obtenerHoraFechaNormal());

    }


    @Test
    public void dat () {
        Date d = new Date();
        long h = 3;
        System.out.println(d);
        System.out.println(d.getTime());
        d.setTime(d.getTime() + (h * 60*60*1000));
        System.out.println(d);



    }





    @Test
    public void falsso () {
        String v = "false";

        boolean x = Boolean.parseBoolean(v);
        if (x)
            System.out.println("si");
        else
            System.out.println("no");
    }






    @Test
    public void comandos () {
        String dato = " alarma false";
        // al dato lo "subdivide" en comando y parametro
        int indice = dato.indexOf(" ");
        String comando = dato;
        String parametro = "";

        if (indice > 0) {
            comando = dato.substring(0, indice);
            parametro = dato.substring(indice + 1);
        }


        System.out.println("comando:" + comando + "*");
        System.out.println("parametro:" + parametro + "*");
    }
}




