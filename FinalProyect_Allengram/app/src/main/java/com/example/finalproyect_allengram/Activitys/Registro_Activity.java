package com.example.finalproyect_allengram.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproyect_allengram.Database.SQL_Helper;
import com.example.finalproyect_allengram.Dialogos.DialogoError;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.ModeloDatos.Schema_Database;
import com.example.finalproyect_allengram.ModeloDatos.User;
import com.example.finalproyect_allengram.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class Registro_Activity extends AppCompatActivity {
    SQL_Helper helper;
    int tema = 0;
    Button registro;
    EditText passreg, nombre_usuarioreg;
    CheckBox theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);
        intancias();
        acciones();
    }

    private void acciones() {
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theme.isSelected()) {
                    tema = 1;
                }
                User userTemp = new User(nombre_usuarioreg.getText().toString(), passreg.getText().toString(), tema);
                SQLiteDatabase dbR = helper.getReadableDatabase();
                String sql = "Select * from Usuarios where user_name = \'" + userTemp.getUsername() + "\'";
                Cursor cursor = dbR.rawQuery(sql, null);
                if (cursor.moveToNext()) {
                    DialogoError error = new DialogoError("Error al registrar", "Usuario ya existente, pruebe con un loggin diferente Example: "
                            + cursor.getString(cursor.getColumnIndex("user_name")) + "1234");
                    error.show(getSupportFragmentManager(), "Error en registro");
                } else {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    String query = "insert into %s (%s,%s,%s) values ('%s',%d,'%s')";
                    db.execSQL(String.format(query, Schema_Database.NOM_TAB,
                            Schema_Database.NOM_COL_NAME,
                            Schema_Database.NOM_COL_THEME,
                            Schema_Database.NOM_COL_PASS,
                            userTemp.getUsername(),
                            userTemp.getTheme(),
                            userTemp.getPass()));
                    db.close();

                    ArrayList<String> personitas = new ArrayList<>();
                    personitas.add("Jose Luis");
                    personitas.add("Janice");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference reference = database.getReference();
                    reference.child("User").child(userTemp.getUsername()).child("Sigues").setValue(personitas);
                    Date date = new Date();
                    ArrayList<Publicacion> post = new ArrayList<>();
                    post.add(new Publicacion(date.getTime(), "https://firebasestorage.googleapis.com/v0/b/first-30bb5.appspot.com/o/milky-way.jpg?alt=media&token=d9f77b68-393d-42cc-8e0a-0db95e8b1962"));
                    reference.child("User").child(userTemp.getUsername()).child("Publicaciones").setValue(post);
                    reference.child("User").child(userTemp.getUsername()).child("name").setValue(userTemp.getUsername());

                    /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference reference = database.getReference();*/

                    Intent intent = new Intent(getApplicationContext(), Principal_Activity.class);
                    intent.putExtra(Log_Activity.KEY, userTemp.getTheme());
                    intent.putExtra(Log_Activity.KEY2, userTemp.getUsername());
                    startActivity(intent);
                }
            }
        });
    }

    private void intancias() {
        passreg = findViewById(R.id.passreg);
        nombre_usuarioreg = findViewById(R.id.nombre_usuarioreg);
        theme = findViewById(R.id.checkbox);
        registro = findViewById(R.id.registro);
        helper = new SQL_Helper(getApplicationContext(), Schema_Database.NOM_DB, null, 1);
    }
}
