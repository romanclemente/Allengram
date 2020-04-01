package com.example.finalproyect_allengram.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.ModeloDatos.User;
import com.example.finalproyect_allengram.R;

import java.util.ArrayList;

public class AdaptadorRecyclerGenerico extends RecyclerView.Adapter<AdaptadorRecyclerGenerico.MyHolder> {

    private ArrayList publicaciones;
    private OnImageSelected listener;
    private Context context;


    public AdaptadorRecyclerGenerico(Context context) {
        this.publicaciones = new ArrayList();
        listener= (OnImageSelected) context;
        this.context = context;
    }

    public void agregarPost(Publicacion p) {
        publicaciones.add(p);
        notifyDataSetChanged();

    }

    public void vaciar() {
        publicaciones.clear();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_fragmentgenerico, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final Publicacion publicacion = (Publicacion) publicaciones.get(position);
        Glide.with(context).load(publicacion.getUrl_storage()).into(holder.getImagen_subida());
        holder.getImagen_subida().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMiImage(publicacion.getUrl_storage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imagen_subida;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagen_subida = itemView.findViewById(R.id.image_ver);
        }

        public ImageView getImagen_subida() {
            return imagen_subida;
        }

    }

    public interface OnImageSelected{
        void onMiImage(String url);
    }
}








