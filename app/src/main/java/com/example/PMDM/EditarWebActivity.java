package com.example.PMDM;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.PMDM.controllers.WebsController;
import com.example.PMDM.model.Web;

public class EditarWebActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditarLink, etEditarEmail, etEditarCategoria, etEditarFoto;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Web web;//La web que vamos a estar editando
    private WebsController websController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_web);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        websController = new WebsController(com.example.PMDM.EditarWebActivity.this);

        // Rearmar la mascota
        // Nota: igualmente solamente podríamos mandar el id y recuperar la mascota de la BD
        long idWeb = extras.getLong("idWeb");
        String nombreWeb = extras.getString("nombreWeb");
        String linkWeb = extras.getString("linkWeb");
        String emailWeb = extras.getString("emailWeb");
        String categoriaWeb = extras.getString("categoriaWeb");
        String fotoWeb = extras.getString("fotoWeb");

        web = new Web(nombreWeb, linkWeb, emailWeb, categoriaWeb, fotoWeb, idWeb);


        // Ahora declaramos las vistas
        etEditarNombre = findViewById(R.id.etEditarNombre);
        etEditarLink = findViewById(R.id.etEditarLink);
        etEditarEmail = findViewById(R.id.etEditarEmail);
        etEditarCategoria = findViewById(R.id.etEditarCategoria);
        etEditarFoto = findViewById(R.id.etEditarFoto);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionMascota);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosMascota);


        // Rellenar los EditText con los datos de la mascota
        etEditarNombre.setText(web.getNombre());
        etEditarLink.setText(web.getLink());
        etEditarEmail.setText(web.getEmail());
        etEditarCategoria.setText(web.getCategoria());
        etEditarFoto.setText(web.getFoto());


        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarLink.setError(null);
                etEditarEmail.setError(null);
                etEditarCategoria.setError(null);
                etEditarFoto.setError(null);

                // Crear la mascota con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String nuevoLink = etEditarLink.getText().toString();
                String nuevoEmail = etEditarEmail.getText().toString();
                String nuevaCategoria = etEditarCategoria.getText().toString();
                String nuevaFoto = etEditarFoto.getText().toString();

                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (nuevoLink.isEmpty()) {
                    etEditarLink.setError("Escribe el link");
                    etEditarLink.requestFocus();
                    return;
                }
                if (nuevoEmail.isEmpty()) {
                    etEditarEmail.setError("Escribe el email");
                    etEditarEmail.requestFocus();
                    return;
                }
                if (nuevaCategoria.isEmpty()) {
                    etEditarCategoria.setError("Escribe la categoria");
                    etEditarCategoria.requestFocus();
                    return;
                }
                if (nuevaFoto.isEmpty()) {
                    etEditarFoto.setError("Escribe el nombre de la foto");
                    etEditarFoto.requestFocus();
                    return;
                }

                // Si llegamos hasta aquí es porque los datos ya están validados
                Web webConNuevosCambios = new Web(nuevoNombre, nuevoLink, nuevoEmail, nuevaCategoria, nuevaFoto,web.getId());
                int filasModificadas = websController.guardarCambios(webConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(com.example.PMDM.EditarWebActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
