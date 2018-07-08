package pc.javier.seguime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import pc.javier.seguime.interfaz.Aplicacion;
import pc.javier.seguime.interfaz.GestorCoordenadas;
import pc.javier.seguime.interfaz.GestorDatos;
import pc.javier.seguime.interfaz.Temporizador;
import pc.javier.seguime.utilidades.Boton;
import pc.javier.seguime.utilidades.FechaHora;
import pc.javier.seguime.utilidades.Parametro;

import static java.lang.Math.round;


/**
 * Created by Javier on 24 mar 2018.
 */

public class ActividadRegresiva extends AppCompatActivity {
    NumberPicker tempHora, tempMinuto, tempSegundo;
    EditText tvTexto, tvSms;
    SharedPreferences preferencias;
    public static Handler handler;
    Temporizador temporizador = new Temporizador();

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

        preferencias = Aplicacion.Preferencias();
        handler = Aplicacion.handler;

        Aplicacion.actividadRegresiva = this;

        tvTexto = (EditText) findViewById(R.id.temporizador_texto);
        tvSms = (EditText) findViewById(R.id.temporizador_sms);

        String texto = Aplicacion.preferenciaCadena("alarmatexto");
        String sms = Aplicacion.preferenciaCadena("sms");
        if (texto.equals(""))
            tvTexto.setText(R.string.alarmamensaje);
        else
            tvTexto.setText(texto);

        tvSms.setText(Aplicacion.preferenciaCadena(sms));



        // tvTexto.addTextChangedListener(new EscucharTexto());
        // tempSegundo.setOnValueChangedListener(new EscucharNumeros());
    }

    @Override
    protected void onResume () {
        super.onResume();


        boton();



        // obtiene la hora en la que debe activarse la alarma
        String alarma = Aplicacion.preferenciaCadena("alarma");









        mensajeLog(alarma + ">" + FechaHora.cantidadMinutos(alarma));
        mensajeLog("Intervalo: " + FechaHora.intervalo(alarma));

        if (!estaActivo())
            if (Parametro.telefono != null)
                if (!Parametro.telefono.equals(""))
                    tvSms.setText(Parametro.telefono);
        Parametro.telefono = "";


        tvSms.setEnabled(!estaActivo());
        tvTexto.setEnabled(!estaActivo());

        if (estaActivo())
            temporizadorAlarmaIniciar();
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
        return  (Aplicacion.alarma() != "");
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
        borrarAlarma.EnviarAlarma();

        temporizadorAlarmaDetener();
        boton();
        tvTexto.setEnabled(true);
        tvSms.setEnabled(true);
    }

    private void iniciar(){
        tvTexto.setEnabled(false);
        tvSms.setEnabled(false);

        if (tvTexto.getText().equals(""))
            tvTexto.setText(R.string.alarmamensaje);

        Aplicacion.alarmaServidor("");
        Aplicacion.preferenciaCadena("sms", tvSms.getText().toString());
        Aplicacion.preferenciaCadena("alarmatexto", tvTexto.getText().toString());




        temporizador.DefinirActivacion(ObtenerSegundos());

        mensajeLog("FECHA HORA - SISTEMA: " + temporizador.FechaHoraString());
        mensajeLog("FECHA HORA - UTC: " + temporizador.FechaHoraUTCString());
        mensajeLog("FECHA HORA - ARGENTINA: " + temporizador.FechaHoraServidorString());

        String alarma = temporizador.FechaHoraString();
        Aplicacion.alarma(alarma);
        Parametro.aplicacion.ReiniciarServicio();

        temporizadorAlarmaIniciar();
        boton();
    }


    private int ObtenerSegundos () {
        return (tempMinuto.getValue()*60) + (tempHora.getValue() *60*60) + tempSegundo.getValue();
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
        getMenuInflater().inflate(R.menu.menu_regresiva,menu);
        return true;
    }


    public void ayuda (MenuItem item) {
        Intent i = new Intent(this, ActividadAyudaRegresiva.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_entrada, R.anim.zoom_salida);
    }







    Timer temporizadorAlarma;



//  temporizador local, solo se usa para mostrar en pantalla el tiempo restante, NO ES LA ALARMA
    private void temporizadorAlarmaIniciar () {

temporizadorAlarma = new Timer();


        temporizadorAlarma.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        Contar();
                    }
                }, 1, 1000 );
    }


    private void Contar (){

        if (!estaActivo())
            temporizadorAlarmaDetener();



        Message mensaje = new Message();
        Bundle dato = new Bundle();

        dato.putString("vista", "alarma");
        mensaje.setData(dato);

        handler.sendMessage(mensaje);


    }



    private void temporizadorAlarmaDetener () {
        if (temporizadorAlarma != null)
        temporizadorAlarma.cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        temporizadorAlarmaDetener();
    }




    private class EscucharTexto implements TextWatcher {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //TextView myOutputBox = (TextView) findViewById(R.id.temporizador_textoinformativo);
            //myOutputBox.setText("caracteres: "+tvTexto.length() + " - " + tempSegundo.getValue());
        }
    }


    private class EscucharNumeros implements NumberPicker.OnValueChangeListener {



        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            // temporizador.DefinirActivacion(ObtenerSegundos());

            // TextView myOutputBox = (TextView) findViewById(R.id.temporizador_textoinformativo);
            // myOutputBox.setText("Hora de Activacion: " + temporizador.FechaHoraString());
        }
    }


    private void mensajeLog (String texto) {
        Log.d("Actividad Regresiva", texto);
    }


}
