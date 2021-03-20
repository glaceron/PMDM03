package com.example.PMDM;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.PMDM.databinding.ActivityMain2Binding;

import java.io.IOException;
import java.util.Calendar;

public class Main2 extends AppCompatActivity implements View.OnClickListener{

    public static final String FICHERO = "ficheroExterna.txt";
    private static final int REQUEST_WRITE = 2;
    private ActivityMain2Binding binding;
    private Memoria memoria;

    private TextView notificationsTime;
    private int alarmID = 0;
    private SharedPreferences settings;
    String leido;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        binding.buttonPonerAlarma.setOnClickListener(this);
        binding.buttonBorrarAlarmas.setOnClickListener(this);
        binding.buttonLanzarAlarmas.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        if (v == binding.buttonPonerAlarma)
            guardar();

        if(v == binding.buttonBorrarAlarmas)
        {
            if (memoria.disponibleEscritura()) {
                String vacio = "";
                try {
                    if (memoria.escribirExterna(FICHERO, vacio)) {
                        Toast.makeText(Main2.this, "Alarmas borradas con exito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Main2.this, "Error al borrar las alarmas" + FICHERO, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(v == binding.buttonLanzarAlarmas)
        {
            leer();
            String [] alarmas = leido.split("\n");
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            System.out.println("Son las: " + hour + " : " + minute);
            int alarmasRestantes = cantidadAlarmas();
            System.out.println(alarmasRestantes);
            binding.textViewNumAlarmas.setText(Integer.toString(alarmasRestantes));

            for(int i = 0; i<alarmasRestantes;i++)
            {
                int numeroAlarma = i+1;
                System.out.println("cantidad Alarmas : " + numeroAlarma);
                String [] alarma = alarmas[i ].split(",");
                int minutos = Integer.valueOf(alarma[0]);
                String mensaje = alarma[1];
                String finalHour = null, finalMinute = null;

                if((minutos + minute) >59)
                {
                    finalHour = String.valueOf(hour+1);
                    finalMinute = String.valueOf(minutos + minute-60);
                }else
                {
                    finalHour = String.valueOf(hour);
                    finalMinute = String.valueOf(minutos + minute);
                }

                System.out.println("Alarma puesta a las: " + finalHour + " : " + finalMinute);


                Calendar today = Calendar.getInstance();

                today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(finalHour));
                today.set(Calendar.MINUTE, Integer.parseInt(finalMinute));
                today.set(Calendar.SECOND, 0);


                SharedPreferences.Editor edit = settings.edit();
                edit.putString("hour", finalHour);
                edit.putString("minute", finalMinute);



                alarmID = i;
                //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                edit.putInt("alarmID", alarmID);
                edit.putLong("alarmTime", today.getTimeInMillis());
                edit.putString("mensaje", mensaje);

                edit.commit();

                Toast.makeText(Main2.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                Utils.setAlarm(alarmID, today.getTimeInMillis(), Main2.this);
            }
        }
    }

    public void guardar() {
        String permiso = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        // comprobar los permisos
        if (ActivityCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
            // pedir los permisos necesarios, porque no están concedidos
            ActivityCompat.requestPermissions(this, new String[]{permiso}, REQUEST_WRITE);
            // Cuando se cierre el cuadro de diálogo se ejecutará onRequestPermissionsResult
        } else {
            // Permisos ya concedidos
            escribir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String permiso = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        // chequeo los permisos de nuevo
        switch (requestCode) {
            case REQUEST_WRITE:
                if (ActivityCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
                    // permiso concedido
                    escribir();
                } else {
                    // no hay permiso
                    mostrarMensaje("No hay permiso para escribir en la memoria externa");
                }
                break;
        }
    }

    private void mostrarMensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void escribir() {
        if (memoria.disponibleEscritura()) {
            try {
                String alarma = "";
                String tono = "";

                if(binding.radioButtonTono1.isChecked())
                {
                    tono = "tono1";
                }
                else if(binding.radioButtonTono2.isChecked())
                {
                    tono = "tono2";
                }
                else if(binding.radioButtonTono3.isChecked())
                {
                    tono = "tono3";
                }
                else if(binding.radioButtonTono4.isChecked())
                {
                    tono = "tono4";
                }
                else if(binding.radioButtonTonoSilencio.isChecked())
                {
                    tono = "Silencio";
                }
                System.out.println("Cantidad de alarmas = " + cantidadAlarmas());
                if(cantidadAlarmas()>=5)
                {
                    Toast.makeText(Main2.this,"Ya hay 5 alarmas, no se pueden poner mas.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    alarma = binding.editTextMinutos.getText() + "," + binding.editTextFrase.getText() + "," + tono + "\n" + leido;
                    System.out.println("Se va a guardar la alarma :" + alarma);
                    Toast.makeText(Main2.this,"Alarma guardada:\n " + alarma, Toast.LENGTH_LONG).show();
                    if (memoria.escribirExterna(FICHERO,alarma))
                    {
                        mostrarMensaje("Fichero escrito OK");
                    }
                    else
                    {
                        Toast.makeText(Main2.this,"Error al escribir en el fichero" + FICHERO, Toast.LENGTH_LONG).show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("IOExepction: " + e.getMessage());
            }
        } else {
            Toast.makeText(Main2.this,"Memoria externa no disponible" + FICHERO, Toast.LENGTH_LONG).show();
        }
    }

    private void leer() {
        try {
            leido = memoria.leerExterna(FICHERO);
            Toast.makeText(Main2.this,leido, Toast.LENGTH_LONG).show();
            mostrarMensaje("Fichero leido OK");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Main2.this,"Error al leer de la memoria externa: "  + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int cantidadAlarmas()
    {
        leer();
        String [] alarmas = leido.split("\n");
        return alarmas.length;
    }
}