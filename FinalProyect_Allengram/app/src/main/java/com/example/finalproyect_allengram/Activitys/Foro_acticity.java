package com.example.finalproyect_allengram.Activitys;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproyect_allengram.Adaptador.AdaptadorRecyclerGenerico;
import com.example.finalproyect_allengram.Fragments.FragmentForo;
import com.example.finalproyect_allengram.Fragments.FragmentPerfil;
import com.example.finalproyect_allengram.R;

public class Foro_acticity extends AppCompatActivity implements AdaptadorRecyclerGenerico.OnImageSelected {
    private String myName, otherName;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_acticity);
        recogerInfo();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (otherName == null) {
            ft.replace(R.id.sitio_foro, new FragmentForo(theme, myName));
        } else {
            ft.replace(R.id.sitio_foro, new FragmentPerfil(theme,otherName,myName));
        }
        ft.commit();
    }

    private void recogerInfo() {
        Bundle b = getIntent().getExtras();
        theme = b.getInt(Principal_Activity.MY_THEME);
        try {
            otherName = b.getString(Principal_Activity.OTHER_NAME);
        } catch (NullPointerException ex) {
            otherName = null;
        }
        myName = b.getString(Principal_Activity.MY_NAME);
    }

    @Override
    public void onMiImage(String url) {
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
    }
}
