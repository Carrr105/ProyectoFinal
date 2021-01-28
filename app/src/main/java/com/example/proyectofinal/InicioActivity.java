package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InicioActivity extends AppCompatActivity implements Handler.Callback, ListaLocalesFragment.Callback{

    private FirebaseAuth auth;
    private DatabaseReference ref;
    private LocalesDP localesDP;
    private ArrayList<LocalesDP> listaLocales;
    private ListaLocalesFragment lista;
    private String cityUser, uID;
    private Handler handler;
    private TextView saludo;
    //private DBHelper db;
    private TextView locales;
    private String cityStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        auth = FirebaseAuth.getInstance();
        //Log.wtf("HOLA", "holaaa");
        FirebaseUser user = auth.getInstance().getCurrentUser();

        saludo = findViewById(R.id.saludo);
        String resultadoLocales = "";
        saludo.setText("Hola, "+ user.getDisplayName());


        FirebaseDatabase db =FirebaseDatabase.getInstance();
        ref = db.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                buscaLocales();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child("users").orderByChild("uID").equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String key=childSnapshot.getKey();
                    cityUser = dataSnapshot.child("city").getValue(String.class);
                    Log.wtf("CIUDADFUNC",cityUser);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("TAG","murio");
            }
        });
    }

    private void buscaLocales(){
        listaLocales = new ArrayList<>();
        Log.wtf("buscarLocales()",cityUser);
        FirebaseUser user = auth.getInstance().getCurrentUser();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf("buscaLocales()", listaLocales.toString());
                ponRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("lugares").orderByChild("ciudad").equalTo(cityUser).addChildEventListener(new ChildEventListener() {

            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                localesDP = new LocalesDP();
                localesDP.setCalif(dataSnapshot.child("calif").getValue(String.class));
                localesDP.setCreador(dataSnapshot.child("creador").getValue(String.class));
                localesDP.setDisca(dataSnapshot.child("disca").getValue(String.class));
                localesDP.setNombre(dataSnapshot.child("nombre").getValue(String.class));
                localesDP.setCiudad(dataSnapshot.child("ciudad").getValue(String.class));
                localesDP.setTipo(dataSnapshot.child("tipo").getValue(String.class));
                localesDP.setUbi(dataSnapshot.child("ubi").getValue(String.class));
                listaLocales.add(localesDP);
            }
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}


            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){}

            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("TAG","murio");
            }
        });
    }


    private void ponRecycler(){
        Log.wtf("ponRecycler()","Poniendo ese");
        handler = new Handler(Looper.getMainLooper(), this);
        lista = new ListaLocalesFragment();
        lista.setArray(listaLocales);
        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentByTag("Fragmento");
        FragmentTransaction transaction =  manager.beginTransaction();
        if(lista==f)
            return;

        if(f!=null){
            transaction.remove(f);
        }
        transaction.add(R.id.contenedorMain, lista, "Fragmento");
        transaction.commit();
    }

    public void misLocales(View v){
        Intent i = new Intent(this, UserpointsActivity.class);
        startActivity(i);
    }

    /*public void refresh(View v){
        String resultadoLocales = "";
        Vector<String> resultado = db.buscarLocales(cityStr);
        for(int i=0;i+1<=resultado.size();i++){
            resultadoLocales = resultadoLocales+resultado.get(i)+"\n";
        }
        locales = findViewById(R.id.locales);
        locales.setText(resultadoLocales);
    }
*/
    public void abrirCDMX(View v){
        /*Intent i = new Intent(this, ciudadGeneralActivity.class);
        i.putExtra("city", "CDMX");
        startActivity(i);*/
        Log.wtf("CIUDADBTN",cityUser);
    }

    public void abrirGDL(View v){
        Intent i = new Intent(this, ciudadGeneralActivity.class);
        i.putExtra("city", "Guadalajara");
        startActivity(i);
    }

    public void abrirTOL(View v){
        Intent i = new Intent(this, ciudadGeneralActivity.class);
        i.putExtra("city", "Toluca");
        startActivity(i);
    }

    public void abrirMTY(View v){
        Intent i = new Intent(this, ciudadGeneralActivity.class);
        i.putExtra("city", "Monterrey");
        startActivity(i);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public void saludoEnActividad(int pos) {
        Toast.makeText(this, "Local: "+listaLocales.get(pos).toString(), Toast.LENGTH_LONG).show();
    }
}