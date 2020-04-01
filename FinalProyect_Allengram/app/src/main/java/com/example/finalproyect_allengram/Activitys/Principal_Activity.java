package com.example.finalproyect_allengram.Activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproyect_allengram.Adaptador.AdaptadorBusqueda;
import com.example.finalproyect_allengram.Adaptador.AdaptadorFragments;
import com.example.finalproyect_allengram.Adaptador.AdaptadorRecyclerGenerico;
import com.example.finalproyect_allengram.Fragments.FragmentBusqueda;
import com.example.finalproyect_allengram.Fragments.FragmentInicio;
import com.example.finalproyect_allengram.Fragments.FragmentPerfil;
import com.example.finalproyect_allengram.ModeloDatos.Publicacion;
import com.example.finalproyect_allengram.R;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Principal_Activity extends AppCompatActivity implements AdaptadorBusqueda.OnUserListener, AdaptadorRecyclerGenerico.OnImageSelected {
    static final String MY_NAME = "KE_NAME";
    static final String OTHER_NAME = "KE_OTHER";
    static final String MY_THEME = "KE_THEME";
    static final int GALLERY_INTENT = 2;
    final static String KACTIVITY = "FRG";
    private TabLayout tabLayout;
    public int RESIZE_FOR_PERCENTAGE = 50;
    private NavigationView navigation;
    private FragmentBusqueda busqueda;
    private FragmentInicio inicio;
    private FragmentPerfil perfil;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private MagicalPermissions permissions;
    private ViewPager viewPager;
    private AdaptadorFragments adaptadorFragments;
    private ArrayList<Fragment> listaFG;
    private ArrayList publicaciones;
    private ArrayList<Publicacion> post;
    private int theme;
    private String user_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_principal);
        recogerDatos();
        instancias();
        acciones();
        typeTheme();
        modificarBBDD();
    }

    private void typeTheme() {
        toolbar.setBackground(getDrawable(R.color.model_white));
        toolbar.setTitleTextColor(Color.BLACK);
        tabLayout.setBackground(getDrawable(R.color.model_white));
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.model_black));
        navigation.setBackground(getDrawable(R.color.model_white));

    }

    private void instancias() {

        String[] permisos = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        inicio = new FragmentInicio(theme, user_Name);
        busqueda = new FragmentBusqueda(theme);
        perfil=new FragmentPerfil(theme, user_Name, user_Name);
        permissions = new MagicalPermissions(this, permisos);

        viewPager = findViewById(R.id.view_pager);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Allengram");
        tabLayout.setupWithViewPager(viewPager);

        navigation = findViewById(R.id.navigation);
        inciarPager();
    }

    private void modificarBBDD() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                publicaciones = (ArrayList) dataSnapshot.child(user_Name).child("Publicaciones").getValue();
                post = new ArrayList<>();
                for (int i = 0; i < publicaciones.size(); i++) {
                    Object o = publicaciones.get(i);
                    HashMap list = (HashMap) o;
                    Object[] array = list.values().toArray();

                    Timestamp stamp = new Timestamp((Long) array[0]);
                    String url = (String) array[1];
                    post.add(new Publicacion(stamp.getTime(), url));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void recogerDatos() {
        Bundle b = getIntent().getExtras();
        theme = b.getInt(Log_Activity.KEY);
        user_Name = b.getString(Log_Activity.KEY2);
    }

    private void inciarPager() {
        listaFG = new ArrayList();
        listaFG.add(inicio);
        listaFG.add(busqueda);
        listaFG.add(perfil);
        adaptadorFragments = new AdaptadorFragments(getSupportFragmentManager(), 0, listaFG);
        viewPager.setAdapter(adaptadorFragments);
    }


    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference reference = FirebaseStorage.getInstance().getReference();
            final StorageReference referenceint = reference.child(String.valueOf(uri.getLastPathSegment()));

            UploadTask uploadTask = referenceint.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return referenceint.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();
                        Publicacion publicacion = new Publicacion(new Date().getTime(), downloadURL);
                        post.add(publicacion);
                        setearNuevaInfo();
                        busqueda.vaciar();
                    } else {
                    }
                }
            });
        }
    }


    private void setearNuevaInfo() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        for (int i = 0; i < post.size(); i++) {
            reference.child("User").child(user_Name).child("Publicaciones").child(i + "").setValue(post.get(i));
        }
    }

    private void acciones() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.getCurrentItem();

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.galeria:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/");
                        startActivityForResult(intent, GALLERY_INTENT);
                        break;
                    case R.id.mensajeria:
                        Intent in = new Intent(getApplicationContext(), Foro_acticity.class);
                        in.putExtra(MY_THEME, theme);
                        in.putExtra(MY_NAME, user_Name);
                        startActivity(in);
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

    }


    @SuppressLint("ResourceType")
    @Override
    public void onMiRecycler(String us) {

        Intent in = new Intent(getApplicationContext(), Foro_acticity.class);
        in.putExtra(MY_THEME, theme);
        in.putExtra(MY_NAME, user_Name);
        in.putExtra(OTHER_NAME, us);
        startActivity(in);
    }

    @Override
    public void onMiImage(String url) {
        perfil.nuevoPerfil(url);
    }
}
