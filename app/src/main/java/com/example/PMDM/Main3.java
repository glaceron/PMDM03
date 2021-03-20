package com.example.PMDM;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.PMDM.controllers.WebsController;
import com.example.PMDM.model.Web;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Main3 extends AppCompatActivity
{
    private List<Web> listaDeWebs;
    private RecyclerView recyclerView;
    private AdaptadorWebs adaptadorWebs;
    private WebsController websController;
    private FloatingActionButton fabAgregarWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        websController = new WebsController(com.example.PMDM.Main3.this);

        recyclerView = findViewById(R.id.web_list);
        fabAgregarWeb = findViewById(R.id.fabAgregarWeb);

        listaDeWebs = new ArrayList<>();
        adaptadorWebs = new AdaptadorWebs(listaDeWebs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorWebs);

        refrescarListaDeWebs();


        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarMascotaActivity.java
                Web webSeleccionada = listaDeWebs.get(position);
                Intent intent = new Intent(com.example.PMDM.Main3.this, EditarWebActivity.class);
                intent.putExtra("idWeb", webSeleccionada.getId());
                intent.putExtra("nombreWeb", webSeleccionada.getNombre());
                intent.putExtra("linkWeb", webSeleccionada.getLink());
                intent.putExtra("emailWeb", webSeleccionada.getEmail());
                intent.putExtra("categoriaWeb", webSeleccionada.getCategoria());
                intent.putExtra("fotoWeb", webSeleccionada.getFoto());

                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Web webParaEliminar = listaDeWebs.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(com.example.PMDM.Main3.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                websController.eliminarWeb(webParaEliminar);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la web " + webParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(com.example.PMDM.Main3.this, AgregarWebActivity.class);
                startActivity(intent);
            }
        });

        // Créditos
        fabAgregarWeb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(com.example.PMDM.Main3.this)
                        .setTitle("Acerca de")
                        .setMessage("CRUD de Android con SQLite creado por Carlos Moyano")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://educacionadistancia.juntadeandalucia.es/cursos/user/profile.php?id=1836"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeWebs();
    }

    public void refrescarListaDeWebs()
    {
        if (adaptadorWebs == null) return;
        listaDeWebs = websController.obtenerWebs();
        adaptadorWebs.setListaDeWebs(listaDeWebs);
        adaptadorWebs.notifyDataSetChanged();
    }
}
