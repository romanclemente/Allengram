package com.example.finalproyect_allengram.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproyect_allengram.Database.SQL_Helper;
import com.example.finalproyect_allengram.Dialogos.DialogoError;
import com.example.finalproyect_allengram.ModeloDatos.Schema_Database;
import com.example.finalproyect_allengram.ModeloDatos.User;
import com.example.finalproyect_allengram.R;

public class Log_Activity extends AppCompatActivity implements View.OnClickListener {
    SQL_Helper helper;
    Button iniciar;
    EditText pass, nombre_usu;
    TextView forget, registrar;
    final static String KEY = "k1";
    final static String KEY2 = "k2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_);
        instancias();
        acciones();
    }

    private void acciones() {
        iniciar.setOnClickListener(this);
        forget.setOnClickListener(this);
        registrar.setOnClickListener(this);
    }

    private void instancias() {
        pass = findViewById(R.id.pass);
        nombre_usu = findViewById(R.id.nombre_usuario);
        forget = findViewById(R.id.forget);
        registrar = findViewById(R.id.registro);
        iniciar = findViewById(R.id.iniciar);
        helper = new SQL_Helper(getApplicationContext(), Schema_Database.NOM_DB, null, 1);
    }

    @Override
    public void onClick(View v) {
        if (v == iniciar) {
            //TODO DEJAR POR SI SE LIA LA BBDD

           /* ArrayList<String> personitas= new ArrayList<>();
            personitas.add("Rodrigo");
            personitas.add("Janice");*//*final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference();
            reference.child("User").child("Jose Luis").child("Sigues").setValue(personitas);*//*  Date date = new Date();
            ArrayList<Publicacion> post = new ArrayList<>();
            post.add(new Publicacion(date.getTime(), "https://firebasestorage.googleapis.com/v0/b/first-30bb5.appspot.com/o/milky-way.jpg?alt=media&token=d9f77b68-393d-42cc-8e0a-0db95e8b1962"));

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference();
            reference.child("User").child("Jose Luis").child("Publicaciones").setValue(post);*/

            User userTemp = new User(nombre_usu.getText().toString(), pass.getText().toString(), 1);
            SQLiteDatabase dbR = helper.getReadableDatabase();
            String sql = "Select * from Usuarios where user_name = \'" + userTemp.getUsername() + "\' AND pass =\'" + userTemp.getPass() + "\'";
            Cursor cursor = dbR.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                userTemp.setTheme(cursor.getInt(cursor.getColumnIndex("theme")));
                Intent intent = new Intent(getApplicationContext(), Principal_Activity.class);
                intent.putExtra(KEY, userTemp.getTheme());
                intent.putExtra(KEY2, userTemp.getUsername());
                startActivity(intent);
            } else {
                DialogoError error = new DialogoError("Error con Login", "User o la pass son erroneos, intentalo nuevamente");
                error.show(getSupportFragmentManager(), "Error en login");
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), Registro_Activity.class);
            startActivity(intent);
        }
    }


}
