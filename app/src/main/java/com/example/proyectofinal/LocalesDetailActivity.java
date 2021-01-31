package com.example.proyectofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LocalesDetailActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference ref;

    private TextView nombre, tipo, ciudad, calificacion, disca;
    private ArrayList<ResenasDP> listaLocales;
    private ResenasDP  resenasDP;
    private double lat, lon;
    private String ubi, uID,calif;
    private int pos;
    private double reseCount;

    private Handler handler;
    private  ResenasFragment listaResenas;

    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        FirebaseUser user = auth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_locales);
        Intent intent = getIntent();
        nombre = findViewById(R.id.nombreLocalTV);
        tipo = findViewById(R.id.tipoLocalTV);
        ciudad = findViewById(R.id.ciudadLocalTV);
        calificacion = findViewById(R.id.calificacionTV);
        disca = findViewById(R.id.discaTV);

        calif = intent.getStringExtra("calif");
        nombre.setText(intent.getStringExtra("nombre"));
        tipo.setText(intent.getStringExtra("tipo"));
        ciudad.setText(intent.getStringExtra("ciudad"));
        calificacion.setText(intent.getStringExtra("calif"));
        disca.setText(intent.getStringExtra("disca"));
        ubi = intent.getStringExtra("ubi");
        uID =  intent.getStringExtra("uid");
        //Toast.makeText(this, uID,  Toast.LENGTH_SHORT).show();
        buscaResenas();
    }

    public void verMapa(View v){
        Intent i = new Intent(this, VerMapsLocalActivity.class);
        if(ubi.equals("coordenadas")){
            Snackbar.make(v, "Este local aún no cuenta con ubicación",  Snackbar.LENGTH_SHORT).show();
        }else{
            i.putExtra("ubicate", ubi);
            startActivity(i);
        }
    }

    public void verComoLlegar(View v){

        if(ubi.equals("coordenadas")){
            Snackbar.make(v, "Este local aún no cuenta con ubicación",  Snackbar.LENGTH_SHORT).show();
        }else{
            StringTokenizer st = new StringTokenizer(ubi, "$");
            double lat = Double.parseDouble(st.nextToken());
            double lon = Double.parseDouble(st.nextToken());
            Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    public void verRese(){
        //handler = new Handler(Looper.getMainLooper(), this);
        listaResenas = new ResenasFragment();
        listaResenas.setArray(listaLocales);
        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentByTag("Fragmento");
        FragmentTransaction transaction =  manager.beginTransaction();
        if(listaResenas==f)
            return;

        if(f!=null){
            transaction.remove(f);
        }
        transaction.add(R.id.resenaContainer, listaResenas, "Fragmento");
        transaction.commit();
    }

    public void buscaResenas(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        ref = db.getReference();
        listaLocales = new ArrayList<>();
        FirebaseUser user = auth.getInstance().getCurrentUser();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                verRese();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("lugares").orderByKey().equalTo(uID).addChildEventListener(new ChildEventListener() {

            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //Log.wtf("Resenas", ""+dataSnapshot.child("resena").getChildrenCount());
                reseCount = dataSnapshot.child("resena").getChildrenCount();

                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.child("resena").getValue();
                Object[] hola = td.values().toArray();//Para obtener solo los valores de las reseñas

                for(int i = 0;i<=reseCount-1;i++){
                    resenasDP = new ResenasDP();
                    String reseStr = (String) hola[i];
                    StringTokenizer st2 = new StringTokenizer(reseStr, "$");

                    resenasDP.setCalif(st2.nextToken());
                    resenasDP.setText(st2.nextToken());

                    listaLocales.add(resenasDP);
                    Log.wtf("Ojala",listaLocales.get(i).toString());
                }

            }
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}


            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){}

            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("TAG","murio");
            }
        });
    }

    public void creaRese(View v){
        Intent i = new Intent(this, ReseniaActivity.class);
        i.putExtra("id",uID);
        i.putExtra("count",reseCount);
        i.putExtra("calif", calif);
        startActivity(i);
        finish();
    }
}