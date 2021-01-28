package com.example.proyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LocalesFragment extends Fragment {

    private TextView nombre, tipo, ciudad, calificacion, disca;
    private ArrayList<LocalesDP> listaLocales;
    private int pos;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setArray(ArrayList<LocalesDP> n, int i){
        listaLocales = n;
        pos = i;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_locales, container, false);

        nombre = v.findViewById(R.id.nombreLocalTV);
        tipo = v.findViewById(R.id.tipoLocalTV);
        ciudad = v.findViewById(R.id.ciudadLocalTV);
        calificacion = v.findViewById(R.id.calificacionTV);
        disca = v.findViewById(R.id.discaTV);
        LocalesDP local = listaLocales.get(pos);
        nombre.setText(local.getNombre());
        tipo.setText(local.getTipo());
        ciudad.setText(local.getCiudad());
        calificacion.setText(local.getCalif());
        disca.setText(local.getDisca());

        return v;
    }
}