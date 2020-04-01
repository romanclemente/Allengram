package com.example.finalproyect_allengram.Adaptador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.finalproyect_allengram.R;

import java.util.ArrayList;

public class AdaptadorFragments extends FragmentPagerAdapter {


    private ArrayList<Fragment> listaFragments;
    private ArrayList<String> titulos;

    public ArrayList<String> getTitulos() {
        return titulos;
    }

    public AdaptadorFragments(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> listaFragments) {
        super(fm, behavior);
        titulos= new ArrayList<>();
        this.listaFragments = listaFragments;
        this.titulos.add("Inicio");
        this.titulos.add("Busqueda");
        this.titulos.add("Perfil");
    }

    public void agregarFragment(Fragment fragment) {
        this.listaFragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}