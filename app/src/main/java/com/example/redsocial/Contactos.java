package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.redsocial.Adapter.AdapterContactos;
import com.example.redsocial.Adapter.AdapterMensajes;
import com.example.redsocial.Auxiliares.AuxChat;
import com.example.redsocial.dto.Contacts;
import com.example.redsocial.dto.Conversation;
import com.example.redsocial.dto.CreateChat;
import com.example.redsocial.dto.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Contactos extends AppCompatActivity {
    RecyclerView listaContacts;
    TextView nombreUsuario;
    DatabaseReference conversationRef;
    FirebaseDatabase database;
    AdapterContactos adapter;
    FloatingActionButton volver;

    public static ArrayList<CreateChat> contactos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        inicializar();

        eventos();

        volver.setOnClickListener(n->{
            Intent i = new Intent(getApplicationContext(), Publicationes.class);
            startActivity(i);
            finish();
        });
    }

    private void eventos() {
        if (Login.getUsers() != null) {

            if (Login.getUsers().contains(Publicationes.getUserglobal())) {
                System.out.println("contiene a miiiiiiiiiiii");
                Login.getUsers().remove(Publicationes.getUserglobal());
            }

            adapter = new AdapterContactos(Login.getUsers(), this);
            listaContacts.setAdapter(adapter);
        }

        nombreUsuario.setText("Hi " + Publicationes.getUserglobal().getUsername());
    }

    /**
     * metodo que obtiene todos los contactos de firebase y los rellena en una lista.
     */
    public void mostrar() {
        conversationRef = database.getReference("Conversaciones");
        conversationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Contactos.contactos.clear();
                System.out.println("entrandi en for de chatsssss\n");
                for (DataSnapshot element : dataSnapshot.getChildren()) {
                    CreateChat value = element.getValue(CreateChat.class);
                    System.out.println("--------- " + value.toString());
                    Contactos.contactos.add(value);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("------", "Failed to read value.", error.toException());
            }
        });

    }

    void inicializar() {
        listaContacts = findViewById(R.id.listContacts);
        volver=findViewById(R.id.volverContact);
        nombreUsuario = findViewById(R.id.UserContactName);
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Contacts");
        listaContacts.setLayoutManager(
                new LinearLayoutManager(this));

        mostrar();

    }

}