package pc.javier.seguime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Date;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.GestorCoordenadas;
import pc.javier.seguime.interfaz.GestorDatos;
import pc.javier.seguime.utilidades.Boton;
import pc.javier.seguime.utilidades.FechaHora;
import pc.javier.seguime.utilidades.Parametro;


/**
 * Created by Javier on 24 mar 2018.
 */

public class ActividadRegresiva extends AppCompatActivity {
    NumberPicker tempHora, tempMinuto, tempSegundo;
    TextView tvTexto, tvSms;
    SharedPreferences preferencias;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regresiva);

        tempHora = (NumberPicker) findViewById(R.id.temporizador_hora);
        tempMinuto = (NumberPicker) findViewById(R.id.temporizador_minuto);
        tempSegundo = (NumberPicker) findViewById(R.id.temporizador_segundo);

        tempHora.setWrapSelectorWheel(true);
        tempMinuto.setWrapSelectorWheel(true);
        tempSegundo.setWrapSelectorWheel(true);

        tempHora.setMinValue(0);
        tempHora.setMaxValue(24);

        tempMinuto.setMinValue(0);
        tempMinuto.setMaxValue(59);

        tempSegundo.setMaxValue(59);
        tempSegundo.setMinValue(0);

        tempMinuto.setValue(5);
        tempHora.setValue(0);

        preferencias = Aplicacion.preferencias;
        handler = Aplicacion.handler;

        Aplicacion.actividadRegresiva = this;

        tvTexto = (TextView) findViewById(R.id.temporizador_texto);
        tvSms = (TextView) findViewById(R.id.temporizador_sms);

        String texto = Aplicacion.preferenciaCadena("alarmatexto");
        String sms = Aplicacion.preferenciaCadena("sms");
        if (texto.equals(""))
            tvTexto.setText(R.string.alarmamensaje);
        else
            tvTexto.setText(texto);

        tvSms.setText(Aplicacion.preferenciaCadena(sms));
    }

    @Override
    protected void onResume () {
        super.onResume();

        boton();



        // obtiene la hora en la que debe activarse la alarma
        String alarma = Aplicacion.preferenciaCadena("alarma");

        // si no hay alarma sale
        if (alarma == "")
            return;

        /*
        // si el alarma llego al final se activa
        long intervalo = FechaHora.intervalo(alarma);
        if (intervalo<=0)
            activar();

    */

        // muestra en la pantalla el tiempo restante
        long intervalo = FechaHora.intervalo(alarma);
        tempHora.setValue((int)intervalo/60/60);
        tempMinuto.setValue((int)intervalo/60);




        mensajeLog(alarma + ">" + FechaHora.cantidadMinutos(alarma));
        mensajeLog("Intervalo: " + FechaHora.intervalo(alarma));

        if (Parametro.telefono == null)
            return;
        if (Parametro.telefono.equals(""))
            return;
        tvSms.setText(Parametro.telefono);
        Parametro.telefono = "";


    }

    public void clickBoton (View view) {
        if (estaActivo()) {
            detener();
            Aplicacion.alarmaServidor("eliminar");
        }
        else
            iniciar();

        boton();
    }

    // verifica si esta activo o inactivo
    private boolean estaActivo() {
        if (Aplicacion.alarma() == "")
            return false;
        else
            return true;
    }


    private void boton() {
        Button boton = (Button) findViewById(R.id.temporizador_boton);
        if (estaActivo()) {
            Boton.color(boton,"rojo");
            boton.setText(R.string.detener);
        } else {
            Boton.color(boton,"verde");
            boton.setText(R.string.iniciar);
        }

    }




    private void detener(){
        // borra el alarma de preferencias
        Aplicacion.alarma("");

        GestorDatos borrarAlarma = new GestorDatos();
        borrarAlarma.enviarAlarma();
    }

    private void iniciar(){
        Aplicacion.alarmaServidor("");
        Aplicacion.preferenciaCadena("sms", tvSms.getText().toString());
        Aplicacion.preferenciaCadena("alarmatexto", tvTexto.getText().toString());

        long segundos = (tempMinuto.getValue()*60) + (tempHora.getValue() *60*60) + tempSegundo.getValue();
        String alarma = FechaHora.suma(String.valueOf(segundos));
        Aplicacion.alarma(alarma);
        Parametro.aplicacion.iniciarServicio();
    }




    // contactos
    public  void mostrarContactos (View v) {

        Parametro.telefono = "";
        Intent contactos = new Intent(this, ActividadContactos.class);
        if (tvSms.getText().toString().equals(""))
            startActivity(contactos);
    }











    // menu

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }


    public void ayuda (MenuItem item) {
        Intent i = new Intent(this, ActividadAyudaRegresiva.class);
        startActivity(i);
    }





    private void mensajeLog (String texto) {
        Log.d("Actividad Registro", texto);
    }


}
