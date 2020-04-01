package com.example.finalproyect_allengram.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproyect_allengram.Adaptador.AdaptadorBusqueda;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.ModeloDatos.User;
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

public class FragmentBusqueda extends androidx.fragment.app.Fragment {
    private RecyclerView buscar_recycler;
    private LinearLayout linearLayout;
    private AdaptadorBusqueda adaptadorBusqueda;
    private int i = 0;
    private int theme;

    public FragmentBusqueda(int theme) {
        this.theme = theme;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adaptadorBusqueda = new AdaptadorBusqueda(context);
    }

    private void recogerDatosBBDD() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> nombres = new ArrayList<>();
                ArrayList<Publicacion> post;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArrayList publicaciones = (ArrayList) snapshot.child("Publicaciones").getValue();
                    post = new ArrayList<>();
                    for (int i = 0; i < publicaciones.size(); i++) {
                        Object o = publicaciones.get(i);
                        HashMap list = (HashMap) o;
                        Object[] array = list.values().toArray();

                        Timestamp stamp = new Timestamp((Long) array[0]);
                        String url = (String) array[1];
                        post.add(new Publicacion(stamp.getTime(), url));
                        adaptadorBusqueda.agregarPost(new Publicacion(stamp.getTime(), url));
                    }
                    adaptadorBusqueda.agregarUsuario(new User((String) snapshot.child("name").getValue(), post, nombres));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                adaptadorBusqueda.agregarPost(new Publicacion(new Date().getTime(), "https://lh5.googleusercontent.com/proxy/ls77MOk5TZ1_BrvcMk2vtNJv1UgVMl7PdPzBVo0LrJVsXBXYOD1MV2MiCQWPsjiBThOXy3ECXfOiTOj2lmOQXmaV96ZLFI16b3VehXAbjXHYBKWj_bbkbVmYdByIeYX6hXijUQ=w1200-h630-p-k-no-nu"));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_busqueda, container, false);
        linearLayout = v.findViewById(R.id.fondo);
        buscar_recycler = v.findViewById(R.id.buscar_recycler);
        Log.v("prueba", String.valueOf(buscar_recycler.isAttachedToWindow()));
        definirFondo();
        return v;
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
        if (i > 1) {
            i = 0;
            adaptadorBusqueda.vaciar();
        }
        recogerDatosBBDD();
        buscar_recycler.setAdapter(adaptadorBusqueda);
        buscar_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    public void vaciar() {
        adaptadorBusqueda.vaciar();
    }
}




