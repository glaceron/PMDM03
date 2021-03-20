package com.example.PMDM;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.PMDM.controllers.WebsController;
import com.example.PMDM.model.Web;

public class AgregarWebActivity extends AppCompatActivity {
    private Button btnAgregarWeb, btnCancelarNuevaWeb;
    private EditText etNombre, etLink, etEmail, etCategoria, etFotoAñadir;

    private WebsController websController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_web);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etLink = findViewById(R.id.etLink);
        etEmail = findViewById(R.id.etEmail);
        etCategoria = findViewById(R.id.etCategoria);
        etFotoAñadir = findViewById(R.id.etFotoAñadir);


        btnAgregarWeb = findViewById(R.id.btnAgregarWeb);
        btnCancelarNuevaWeb = findViewById(R.id.btnCancelarNuevaWeb);
        // Crear el controlador
        websController = new WebsController(com.example.PMDM.AgregarWebActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etLink.setError(null);
                etEmail.setError(null);
                etCategoria.setError(null);
                String nombre = etNombre.getText().toString(),
                        link = etLink.getText().toString(),
                        email = etEmail.getText().toString(),
                        categoria = etCategoria.getText().toString(),
                        foto = etFotoAñadir.getText().toString();

                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre de la mascota");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(link)) {
                    etLink.setError("Escribe la edad de la mascota");
                    etLink.requestFocus();
                    return;
                }
                if ("".equals(email)) {
                    etEmail.setError("Escribe la edad de la mascota");
                    etEmail.requestFocus();
                    return;
                }
                if ("".equals(categoria)) {
                    etCategoria.setError("Escribe la edad de la mascota");
                    etCategoria.requestFocus();
                    return;
                }
                if ("".equals(foto)) {
                    etFotoAñadir.setError("Escribe la edad de la mascota");
                    etFotoAñadir.requestFocus();
                    return;
                }

                // Ya pasó la validación
                Web nuevaWeb = new Web(nombre, link, email, categoria, foto);
                long id = websController.nuevaWeb(nuevaWeb);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(com.example.PMDM.AgregarWebActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
