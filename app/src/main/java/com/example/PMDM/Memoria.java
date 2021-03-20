package com.example.PMDM;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Memoria {

    public static boolean escribirExterna(String fichero, String cadena) throws IOException{
        File miFichero, tarjeta;

        tarjeta = Environment.getExternalStorageDirectory();
        //tarjeta = Environment.getExternalStoragePublicDirectory("datos/programas/");
        //tarjeta.mkdirs();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);

        return escribir(miFichero, cadena);
    }

    private static boolean escribir(File fichero, String cadena) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter out = null;
        boolean correcto = true;

        fos = new FileOutputStream(fichero);
        osw = new OutputStreamWriter(fos);
        out = new BufferedWriter(osw);
        out.write(cadena);
        out.close();

        return correcto;
    }

    public static String mostrarPropiedades(File fichero) {
        SimpleDateFormat formato = null;
        StringBuffer txt = new StringBuffer();

        try {
            if (fichero.exists()) {
                txt.append("Nombre: " + fichero.getName() + '\n');
                txt.append("Ruta: " + fichero.getAbsolutePath() + '\n');
                txt.append("Tamaño (bytes): " + Long.toString(fichero.length()) + '\n');
                formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
                txt.append("Fecha: " + formato.format(new Date(fichero.lastModified())) + '\n');
            } else
                txt.append("No existe el fichero " + fichero.getName() + '\n');

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            txt.append(e.getMessage());
        }
        return txt.toString();
    }

    public static String mostrarPropiedadesExterna(String fichero) {
        File miFichero, tarjeta;

        tarjeta = Environment.getExternalStorageDirectory();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);

        return mostrarPropiedades(miFichero);
    }

    public static boolean disponibleEscritura() {
        boolean escritura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED))
            escritura = true;

        return escritura;
    }

    public static boolean disponibleLectura() {
        boolean lectura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)
                || estado.equals(Environment.MEDIA_MOUNTED))
            lectura = true;

        return lectura;
    }

    public static String leerExterna(String fichero) throws IOException {
        File miFichero, tarjeta;

        //tarjeta = Environment.getExternalStoragePublicDirectory("datos/programas/");
        tarjeta = Environment.getExternalStorageDirectory();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);

        return leer(miFichero);
    }

    private static String leer(File fichero) throws IOException {
        FileInputStream fis = null;
        InputStreamReader isw = null;
        BufferedReader br = null;
        String linea;
        StringBuilder miCadena = new StringBuilder();

        fis = new FileInputStream(fichero);
        isw = new InputStreamReader(fis);
        br = new BufferedReader(isw);
        //while ((n = in.read()) != -1)
        //    miCadena.append((char) n);
        while ((linea = br.readLine()) != null)
            miCadena.append(linea).append('\n');
        br.close();
        return miCadena.toString();
    }
}
