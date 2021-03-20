package com.example.PMDM.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.PMDM.AyudanteBaseDeDatos;
import com.example.PMDM.model.Web;


public class WebsController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "webs";

    public WebsController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarWeb(Web web) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(web.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaWeb(Web web) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", web.getNombre());
        valoresParaInsertar.put("link", web.getLink());
        valoresParaInsertar.put("email", web.getEmail());
        valoresParaInsertar.put("categoria", web.getCategoria());
        valoresParaInsertar.put("foto", web.getFoto());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Web webEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", webEditada.getNombre());
        valoresParaActualizar.put("link", webEditada.getLink());
        valoresParaActualizar.put("email", webEditada.getEmail());
        valoresParaActualizar.put("categoria", webEditada.getCategoria());
        valoresParaActualizar.put("foto", webEditada.getFoto());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idMascota
        String[] argumentosParaActualizar = {String.valueOf(webEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Web> obtenerWebs() {
        ArrayList<Web> webs = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "link", "email", "categoria", "foto", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from mascotas
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return webs;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return webs;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de mascotas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            String linkObtenidoDeBD = cursor.getString(1);
            String emailObtenidoDeBD = cursor.getString(2);
            String categoriaObtenidoDeBD = cursor.getString(3);
            String fotoObtenidoDeBD = cursor.getString(4);
            long idWeb = cursor.getLong(5);
            Web webObtenidaDeBD = new Web(nombreObtenidoDeBD, linkObtenidoDeBD, emailObtenidoDeBD, categoriaObtenidoDeBD, fotoObtenidoDeBD, idWeb);
            webs.add(webObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de webs :)
        cursor.close();
        return webs;
    }
}