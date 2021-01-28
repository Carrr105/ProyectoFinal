package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private EditText uname, psw;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtener referencia
        auth = FirebaseAuth.getInstance();

        uname =findViewById(R.id.userText);
        psw = findViewById(R.id.pswText);
        //db = new DBHelper(this);
    }

    public void bRegistro(View v){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //verificacion de usuario conectado
        //si el usuario es nulo esta conectado / validado
        FirebaseUser usuario = auth.getCurrentUser();

        if(usuario==null){
            //Obligar validacion
        }
        /*
        //Para leer cositas
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref = db.getReference("ejemplo2/ejemplo21");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class);

                Toast.makeText(MainActivity.this, "Valor = "+valor, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error en lectura de DB", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void logIn(View v){
        String unameUI = uname.getText().toString();
        String pswUI = psw.getText().toString();
        if(pswUI.isEmpty()||unameUI.isEmpty()){
            Toast.makeText(MainActivity.this,
                    "LogIn fallido: Hay un campo vacío", Toast.LENGTH_SHORT).show();
        }else{
            auth.signInWithEmailAndPassword(unameUI, pswUI)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "LogIn exitoso", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, InicioActivity.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(MainActivity.this,
                                        "LogIn fallido: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}