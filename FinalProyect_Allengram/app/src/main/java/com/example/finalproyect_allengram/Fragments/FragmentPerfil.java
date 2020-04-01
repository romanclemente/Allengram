package com.example.finalproyect_allengram.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproyect_allengram.Adaptador.AdaptadorRecyclerGenerico;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentPerfil extends androidx.fragment.app.Fragment {
    private ImageView perfil;
    private TextView publi, seguidos;
    private RecyclerView perfil_recycler;
    private LinearLayout linearLayout;
    private Button seguir;
    private AdaptadorRecyclerGenerico adaptadorRecyclerGenerico;
    private String nombre_user;
    private String miUser;
    private Context context;
    private int i = 0;
    private int theme;
    private ArrayList<String> prueba;

    public FragmentPerfil(int theme, String nombre_user, String miUser) {
        prueba = new ArrayList<>();
        this.theme = theme;
        this.nombre_user = nombre_user;
        this.miUser = miUser;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adaptadorRecyclerGenerico = new AdaptadorRecyclerGenerico(context);
        this.context = context;
    }

    private void recogerDatosBBDD() {
        adaptadorRecyclerGenerico.vaciar();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Publicacion> post;
                ArrayList publicaciones = (ArrayList) dataSnapshot.child(nombre_user).child("Publicaciones").getValue();
                publi.setText(publicaciones.size() + "");
                post = new ArrayList<>();
                if (adaptadorRecyclerGenerico.getItemCount() == 0) {
                    for (int i = 0; i < publicaciones.size(); i++) {
                        Object o = publicaciones.get(i);
                        HashMap list = (HashMap) o;
                        Object[] array = list.values().toArray();

                        Timestamp stamp = new Timestamp((Long) array[0]);
                        String url = (String) array[1];
                        post.add(new Publicacion(stamp.getTime(), url));
                        adaptadorRecyclerGenerico.agregarPost(new Publicacion(stamp.getTime(), url));

                    }
                } else {
                    Object o = publicaciones.get(publicaciones.size() - 1);
                    HashMap list = (HashMap) o;
                    Object[] array = list.values().toArray();

                    Timestamp stamp = new Timestamp((Long) array[0]);
                    String url = (String) array[1];
                    post.add(new Publicacion(stamp.getTime(), url));
                    adaptadorRecyclerGenerico.agregarPost(new Publicacion(stamp.getTime(), url));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                adaptadorRecyclerGenerico.agregarPost(new Publicacion(new Date().getTime(), "https://lh5.googleusercontent.com/proxy/ls77MOk5TZ1_BrvcMk2vtNJv1UgVMl7PdPzBVo0LrJVsXBXYOD1MV2MiCQWPsjiBThOXy3ECXfOiTOj2lmOQXmaV96ZLFI16b3VehXAbjXHYBKWj_bbkbVmYdByIeYX6hXijUQ=w1200-h630-p-k-no-nu"));
            }
        });

    }

    private void recogerSeguidos() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> seguido = (ArrayList) dataSnapshot.child(nombre_user).child("Sigues").getValue();
                seguidos.setText(seguido.size() + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void recogerPerfil(final Context context) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String seguido = (String) dataSnapshot.child(nombre_user).child("Perfil").getValue();
                Glide.with(context).load(seguido).into(perfil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void nuevoPerfil(String url) {
        if (nombre_user.equalsIgnoreCase(miUser)) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference();
            reference.child("User").child(miUser).child("Perfil").setValue(url);
            Glide.with(context).load(url).into(perfil);
            adaptadorRecyclerGenerico.vaciar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        linearLayout = v.findViewById(R.id.fondo);
        perfil_recycler = v.findViewById(R.id.perfil_recycler);
        publi = v.findViewById(R.id.publicaciones);
        perfil = v.findViewById(R.id.perfil);
        seguidos = v.findViewById(R.id.seguidos);
        seguir = v.findViewById(R.id.seguir);
        definirFondo();
        recogerSeguidos();
        acciones();
        return v;
    }

    private void acciones() {
        seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rellenarSeguidoresPropios();
            }
        });

    }

    private void rellenarSeguidoresPropios() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> seguido = (ArrayList) dataSnapshot.child(miUser).child("Sigues").getValue();
                if (!seguido.contains(nombre_user)) {
                    seguido.add(nombre_user);
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference reference = database.getReference();
                    reference.child("User").child(miUser).child("Sigues").setValue(seguido);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void rellenarSeguidos(String st) {
        prueba.add(st);
    }


    private void definirFondo() {
        if (theme == 0) {
            linearLayout.setBackgroundResource(R.drawable.fondo_light);
        } else {
            linearLayout.setBackgroundResource(R.drawable.fondo_dark);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        i++;
        if (i > 0) {
            i = 0;
            adaptadorRecyclerGenerico.vaciar();
        }
        recogerDatosBBDD();
        recogerPerfil(context);
        perfil_recycler.setAdapter(adaptadorRecyclerGenerico);
        perfil_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

}
