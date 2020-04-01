package com.example.finalproyect_allengram.Activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproyect_allengram.R;

public class Activity_Fragment extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        instancias();
        recogerDatos();
    }

    private void instancias() {
        img = findViewById(R.id.previs);
    }


    private void recogerDatos() {
        Bundle b = getIntent().getExtras();
        img= (ImageView) b.get(Principal_Activity.KACTIVITY);
    }
}
