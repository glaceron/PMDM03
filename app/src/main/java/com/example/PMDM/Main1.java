package com.example.PMDM;

import android.graphics.Color;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PMDM.databinding.ActivityMain1Binding;

public class Main1 extends AppCompatActivity implements View.OnClickListener
{
    private ActivityMain1Binding binding;
    private TextWatcher textAeuros = null;
    private TextWatcher textAdolares = null;
    double constante = 0.86;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        binding = ActivityMain1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.radioButtonDolaresAeuros.setChecked(true);

        binding.radioButtonDolaresAeuros.setOnClickListener(this);
        binding.radioButtonEurosADolares.setOnClickListener(this);
        binding.buttonCambiar.setOnClickListener(this);
        binding.editTextValor.setVisibility(View.GONE);
        binding.buttonCambiar.setVisibility(View.GONE);

        setSupportActionBar(binding.toolbar);
        binding.editTextEuros.setEnabled(false);
        binding.editTextDolares.setEnabled(true);


        textAdolares = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                    Double euro = Double.parseDouble(String.valueOf(binding.editTextEuros.getText()));
                    Double dolar= euro * constante;
                    binding.editTextDolares.setText(dolar.toString());
                }
                catch (Exception e)
                {
                    Toast.makeText(Main1.this, "Introduce un numero valido",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        textAeuros = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                    Double dolar = Double.parseDouble(String.valueOf(binding.editTextDolares.getText()));
                    Double euro = dolar / constante ;
                    binding.editTextEuros.setText(euro.toString());
                }
                catch (Exception e)
                {
                    Toast.makeText(Main1.this, "Introduce un numero valido",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.radioButtonDolaresAeuros.callOnClick();
    }







    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.itemAmarillo:
                binding.toolbar.setBackgroundColor(Color.YELLOW);
                return true;

            case R.id.itemAzul:
                binding.toolbar.setBackgroundColor(Color.BLUE);
                return true;

            case R.id.itemVerde:
                binding.toolbar.setBackgroundColor(Color.GREEN);
                return true;

            case R.id.itemInfo:
                Toast.makeText(Main1.this, "Autor: Carlos Manuel Moyano Pérez\nProfesor: Francisco García Sánchez\nAplicación: Calculadora con Toolbar",Toast.LENGTH_LONG).show();
                return true;

            case R.id.itemCambio:
                if(binding.editTextValor.getVisibility() == (View.GONE))
                {
                    binding.editTextValor.setVisibility(View.VISIBLE);
                    binding.buttonCambiar.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.editTextValor.setEnabled(false);
                    binding.editTextValor.setVisibility(View.GONE);
                    binding.buttonCambiar.setVisibility(View.GONE);
                }

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if(v == binding.buttonCambiar)
        {
            try
            {
                constante = Double.parseDouble(String.valueOf(binding.editTextValor.getText()));
                binding.editTextValor.setVisibility(View.GONE);
                binding.buttonCambiar.setVisibility(View.GONE);

                if(binding.radioButtonDolaresAeuros.isChecked())
                {
                    Double dolar = Double.parseDouble(String.valueOf(binding.editTextDolares.getText()));
                    Double euro = dolar / constante ;
                    binding.editTextEuros.setText(euro.toString());
                }
                else if(binding.radioButtonEurosADolares.isChecked())
                {
                    Double euro = Double.parseDouble(String.valueOf(binding.editTextEuros.getText()));
                    Double dolar= euro * constante;
                    binding.editTextDolares.setText(dolar.toString());
                }
            }
            catch (Exception e)
            {
                Toast.makeText(Main1.this, "Introduce un numero valido",Toast.LENGTH_SHORT).show();
            }
        }
        if(binding.radioButtonDolaresAeuros.isChecked())
        {
            binding.editTextDolares.addTextChangedListener(textAeuros);
            binding.editTextEuros.removeTextChangedListener(textAdolares);
            binding.editTextEuros.setEnabled(false);
            binding.editTextDolares.setEnabled(true);
        }
        if(binding.radioButtonEurosADolares.isChecked())
        {
            binding.editTextDolares.removeTextChangedListener(textAeuros);
            binding.editTextEuros.addTextChangedListener(textAdolares);
            binding.editTextEuros.setEnabled(true);
            binding.editTextDolares.setEnabled(false);
        }
    }
}
