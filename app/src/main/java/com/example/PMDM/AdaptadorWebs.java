package com.example.PMDM;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.PMDM.model.Web;

import java.util.List;

public class AdaptadorWebs extends RecyclerView.Adapter<AdaptadorWebs.MyViewHolder> {
    Context mContext;

    private List<Web> listaDeWebs;

    public AdaptadorWebs(Main4 main4, List<Web> list) {
    }


    public void setListaDeWebs(List<Web> listaWebs) {
        this.listaDeWebs = listaWebs;
    }

    public AdaptadorWebs(List<Web> webs) {
        this.listaDeWebs = webs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaWeb = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_web, viewGroup, false);
        return new MyViewHolder(filaWeb);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la mascota de nuestra lista gracias al índice i
        Web web = listaDeWebs.get(i);

        // Obtener los datos de la lista
        String nombreWeb = web.getNombre();
        String linkWeb = web.getLink();
        String emailWeb = web.getEmail();
        String categoriaWeb = web.getCategoria();
        String fotoWeb = web.getFoto();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreWeb);
        myViewHolder.link.setText(linkWeb);
        myViewHolder.email.setText(emailWeb);
        myViewHolder.categoria.setText(categoriaWeb);
        if(fotoWeb.equals("comercio"))
        {
            myViewHolder.foto.setImageResource(R.drawable.comercio);
        }
        else if(fotoWeb.equals("transporte"))
        {
            myViewHolder.foto.setImageResource(R.drawable.transporte);
        }
        else if(fotoWeb.equals("video"))
        {
            myViewHolder.foto.setImageResource(R.drawable.video);
        }
        else
        {
            myViewHolder.foto.setImageResource(R.drawable.predeterminada);
        }

    }

    @Override
    public int getItemCount() {
        return listaDeWebs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, link, email, categoria;
        ImageView foto;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.link = itemView.findViewById(R.id.tvLink);
            this.email = itemView.findViewById(R.id.tvEmail);
            this.categoria = itemView.findViewById(R.id.tvCategoria);
            this.foto = itemView.findViewById(R.id.ivFoto);

        }
    }
}
