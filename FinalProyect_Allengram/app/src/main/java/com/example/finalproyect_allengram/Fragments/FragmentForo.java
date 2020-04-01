package com.example.finalproyect_allengram.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproyect_allengram.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentForo extends androidx.fragment.app.Fragment {
    private TextView mensajesforo;
    private EditText tumensaje;
    private LinearLayout fondo_foro;
    private String myName;
    private Button send;
    private int theme;
    private String foroGlov;

    public FragmentForo(int theme, String myName) {
        this.theme = theme;
        this.myName = myName;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recogerDatosBBDD();
    }

    private void recogerDatosBBDD() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("Foro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String foro = (String) dataSnapshot.getValue();
                mostrarMensajes(foro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mostrarMensajes(String foro) {
        foroGlov = foro;
        String[] parts = foro.split("#");
        for (String st : parts) {
            mensajesforo.append(st + "\n");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foro, container, false);
        fondo_foro = v.findViewById(R.id.fondo_foro);
        mensajesforo = v.findViewById(R.id.mensajesforo);
        tumensaje = v.findViewById(R.id.tumensaje);
        send = v.findViewById(R.id.send);
        definirFondo();
        return v;
    }

    private void definirFondo() {
        if (theme == 0) {
            fondo_foro.setBackgroundResource(R.drawable.fondo_light);
        } else {
            fondo_foro.setBackgroundResource(R.drawable.fondo_dark);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        acciones();
    }

    private void acciones() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajesforo.setText("");
                String subida=foroGlov+"#"+myName+":"+tumensaje.getText();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                reference.child("Foro").setValue(subida);
                tumensaje.setText("");

            }
        });
    }
}
