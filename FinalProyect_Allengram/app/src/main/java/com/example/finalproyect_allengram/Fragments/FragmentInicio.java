package com.example.finalproyect_allengram.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproyect_allengram.Adaptador.AdaptadorPostInicial;
import com.example.finalproyect_allengram.Dialogos.DialogoError;
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
import java.util.HashMap;

public class FragmentInicio extends androidx.fragment.app.Fragment {
    private RecyclerView recyclerInicio;
    private LinearLayout linearLayout;
    private AdaptadorPostInicial adaptadorPostInicial;
    int i = 0;
    private String myUser;
    private int theme;

    public FragmentInicio(int theme,String myUser) {
        this.theme = theme;
        this.myUser=myUser;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adaptadorPostInicial = new AdaptadorPostInicial(context);
    }

    public void vaciar() {
        adaptadorPostInicial.vaciar();
    }

    private void recogerDatosBBDD() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Publicacion> post;
                ArrayList sigues = (ArrayList) dataSnapshot.child(myUser).child("Sigues").getValue();
                if (adaptadorPostInicial.getItemCount() == 0) {
                    for (Object st : sigues) {
                        ArrayList publicaciones = (ArrayList) dataSnapshot.child((String) st).child("Publicaciones").getValue();
                        post = new ArrayList<>();
                        for (int i = 0; i < publicaciones.size(); i++) {
                            Object o = publicaciones.get(i);
                            HashMap list = (HashMap) o;
                            Object[] array = list.values().toArray();

                            Timestamp stamp = new Timestamp((Long) array[0]);
                            String url = (String) array[1];
                            post.add(new Publicacion(stamp.getTime(), url));
                            adaptadorPostInicial.agregarPost(new Publicacion(stamp.getTime(), url));
                        }
                        adaptadorPostInicial.agregarUsuario(new User(st.toString(), post, sigues));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                DialogoError error = new DialogoError("Dato Inexistente", "Uno de los datos ha sido imposible recuperar");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_incio, container, false);
        linearLayout = v.findViewById(R.id.fondo);
        recyclerInicio = v.findViewById(R.id.recycler_inicio);
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
            adaptadorPostInicial.vaciar();
        }
        recogerDatosBBDD();
        recyclerInicio.setAdapter(adaptadorPostInicial);
        recyclerInicio.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
