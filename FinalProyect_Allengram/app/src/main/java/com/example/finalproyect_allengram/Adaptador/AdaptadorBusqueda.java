package com.example.finalproyect_allengram.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.ModeloDatos.User;
import com.example.finalproyect_allengram.R;

import java.util.ArrayList;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.MyHolder> {
    private ArrayList users;
    private ArrayList publicaciones;
    private Context context;
    private OnUserListener listener;


    public AdaptadorBusqueda(Context context) {
        this.users = new ArrayList();
        this.publicaciones = new ArrayList();
        this.context = context;
        listener = (OnUserListener) context;
    }

    public void agregarPost(Publicacion p) {
        publicaciones.add(p);
        notifyDataSetChanged();
    }

    public void agregarUsuario(User user) {
        this.users.add(user);
        notifyDataSetChanged();
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
        String nombre = "Default";
        for (Object user : users) {
            User user1 = (User) user;
            for (Publicacion publicacion1 : user1.getPublicacionesCronologia()) {
                if (publicacion1.getUrl_storage().equalsIgnoreCase(publicacion.getUrl_storage())) {
                    nombre = user1.getUsername();
                }
            }
        }
        Glide.with(context).load(publicacion.getUrl_storage()).into(holder.getImagen_subida());
        final String finalNombre = nombre;
        holder.getImagen_subida().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMiRecycler(finalNombre);
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public void vaciar() {
        users.clear();
        publicaciones.clear();
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

    public interface OnUserListener {
        public void onMiRecycler(String us);
    }
}







