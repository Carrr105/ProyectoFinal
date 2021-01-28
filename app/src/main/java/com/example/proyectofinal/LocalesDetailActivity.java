package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LocalesDetailActivity extends AppCompatActivity {

    private TextView nombre, tipo, ciudad, calificacion, disca;
    private ArrayList<LocalesDP> listaLocales;
    private int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_locales);
        Intent intent = getIntent();
        nombre = findViewById(R.id.nombreLocalTV);
        tipo = findViewById(R.id.tipoLocalTV);
        ciudad = findViewById(R.id.ciudadLocalTV);
        calificacion = findViewById(R.id.calificacionTV);
        disca = findViewById(R.id.discaTV);


        nombre.setText(intent.getStringExtra("nombre"));
        tipo.setText(intent.getStringExtra("tipo"));
        ciudad.setText(intent.getStringExtra("ciudad"));
        calificacion.setText(intent.getStringExtra("calif"));
        disca.setText(intent.getStringExtra("disca"));
    }

}