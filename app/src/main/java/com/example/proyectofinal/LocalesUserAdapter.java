package com.example.proyectofinal;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocalesUserAdapter extends RecyclerView.Adapter<LocalesUserAdapter.LocalesUserViewHolder> {

    public class LocalesUserViewHolder extends RecyclerView.ViewHolder{

        public TextView texto1, texto2;
        public ImageButton b;

        public LocalesUserViewHolder(@NonNull View itemView){
            super(itemView);

            texto1 = itemView.findViewById(R.id.nameTV);
            texto2 = itemView.findViewById(R.id.tipoTV);
            b = itemView.findViewById(R.id.del);
        }
    }

    private ArrayList<LocalesDP> locales;
    private View.OnClickListener listener;
    private PositionHolder positionHolder;
    private View v;

    public LocalesUserAdapter(ArrayList<LocalesDP> perritos, View.OnClickListener listener,
                              PositionHolder positionHolder){
        this.locales = perritos;
        this.listener = listener;
        this.positionHolder = positionHolder;
    }





    @NonNull
    @Override
    public LocalesUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =(View) LayoutInflater.from(parent.getContext()).inflate(R.layout.roweditdel, parent,false);
        v.setOnClickListener(listener);


        LocalesUserViewHolder avh = new LocalesUserViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalesUserViewHolder holder, int position) {
        LocalesDP str = locales.get(position);
        holder.texto1.setText(str.getNombre());
        holder.texto2.setText(str.getTipo());


        holder.b.setOnClickListener(v -> {
            positionHolder.setPosition(position);
            notifyDataSetChanged();
            // Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();
            delete(position);
            update();
        });
    }

    public void update(){

    }

    public void delete(Integer position){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("lugares").orderByChild("id").equalTo(locales.get(position).getId());

        Log.wtf("AAAA", locales.get(position).getNombre()+"");
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return locales.size();
    }
}