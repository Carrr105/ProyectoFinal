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

public class UserpointsActivity extends AppCompatActivity implements Handler.Callback, ListaLocalesFragment.Callback {

    private FirebaseAuth auth;
    private DatabaseReference ref;
    private LocalesDP localesDP;
    private ListaLocalesFragment lista;
    ArrayList<LocalesDP> objList;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpoints);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        ref = db.getReference();
        objList = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf("El numero magico", ""+objList.size());
                Log.wtf("El gran obj", objList.toString());
                tostito();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("lugares").orderByChild("creador").equalTo(userID).addChildEventListener(new ChildEventListener() {

            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    localesDP = new LocalesDP();
                    localesDP.setCalif(dataSnapshot.child("calif").getValue(String.class));
                    localesDP.setCreador(userID);
                    localesDP.setDisca(dataSnapshot.child("disca").getValue(String.class));
                    localesDP.setNombre(dataSnapshot.child("nombre").getValue(String.class));
                    localesDP.setCiudad(dataSnapshot.child("ciudad").getValue(String.class));
                    localesDP.setTipo(dataSnapshot.child("tipo").getValue(String.class));
                    localesDP.setUbi(dataSnapshot.child("ubi").getValue(String.class));
                    objList.add(localesDP);
            }
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}


            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){}

            public void onCancelled(@NonNull DatabaseError error) {
                Log.wtf("TAG","murio");
            }
        });
    }

    public void agregarLocal(View v){
        Intent i = new Intent(this, AgregarActivity.class);
        startActivity(i);
    }

    private void tostito(){
        handler = new Handler(Looper.getMainLooper(), this);
        lista = new ListaLocalesFragment();
        lista.setArray(objList);
        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentByTag("Fragmento");
        FragmentTransaction transaction =  manager.beginTransaction();
        if(lista==f)
            return;

        if(f!=null){
            transaction.remove(f);
        }
        transaction.add(R.id.contenedor, lista, "Fragmento");
        transaction.commit();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public void saludoEnActividad(int pos) {
        Toast.makeText(this, "HOLA "+objList.get(pos).toString(), Toast.LENGTH_LONG).show();
    }
}