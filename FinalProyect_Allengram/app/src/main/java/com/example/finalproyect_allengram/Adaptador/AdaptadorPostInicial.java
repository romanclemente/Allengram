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

public class AdaptadorPostInicial extends RecyclerView.Adapter<AdaptadorPostInicial.MyHolder> {

    private ArrayList users;
    private ArrayList publicaciones;
    private Context context;


    public AdaptadorPostInicial(Context context) {
        this.users = new ArrayList();
        this.publicaciones = new ArrayList();
        this.context = context;
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

        View view = LayoutInflater.from(context).inflate(R.layout.layout_fragmentinicial, parent, false);
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
        holder.getUser_post().setText(nombre);
        Glide.with(context).load(publicacion.getUrl_storage()).into(holder.getImagen_subida());
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public void vaciar() {
        users.removeAll(users);
        users.clear();
        users=null;
        users=new ArrayList();

        publicaciones.removeAll(publicaciones);
        publicaciones.clear();
        publicaciones=null;
        publicaciones=new ArrayList();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imagen_subida;
        private TextView user_post;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagen_subida = itemView.findViewById(R.id.imagen_subida);
            user_post = itemView.findViewById(R.id.user_post);
        }

        public ImageView getImagen_subida() {
            return imagen_subida;
        }

        public TextView getUser_post() {
            return user_post;
        }

    }
}








