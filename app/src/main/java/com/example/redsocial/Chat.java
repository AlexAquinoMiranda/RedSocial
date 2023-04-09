package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redsocial.Adapter.AdapterMensajes;
import com.example.redsocial.Adapter.AdapterPublication;
import com.example.redsocial.Firebase.AuxiliarImg;
import com.example.redsocial.Firebase.FirebaseController;
import com.example.redsocial.dto.Contacts;
import com.example.redsocial.dto.Conversation;
import com.example.redsocial.dto.CreateChat;
import com.example.redsocial.dto.Mensaje;
import com.example.redsocial.dto.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    public static CreateChat chatAbrir;
    FirebaseDatabase database;
    String TAG = "---TAG---";
    DatabaseReference conversationRef;
    RecyclerView recyclerView;
    EditText textMensaje;
    Button btnSend;
    AdapterMensajes adapter;
    TextView nombre;
    CircleImageView perfil;
    DividerItemDecoration dividerItemDecoration;
    static ArrayList<Mensaje> mensajes;
    private FirebaseController firebase;
    FloatingActionButton revertChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        inicializar();

        if (mensajes != null) {
            adapter = new AdapterMensajes(mensajes, this);
            recyclerView.setAdapter(adapter);
        }


        eventos();
    }

    private void eventos() {

        mostrar();

        btnSend.setOnClickListener(n -> {
            guardarMensaje();
            textMensaje.setText("");
            mostrar();
            // setteMne();
        });
        revertChat.setOnClickListener(n->{

                Intent i = new Intent(getApplicationContext(), Contactos.class);
                startActivity(i);
                finish();

        });
    }

    void inicializar() {
        revertChat = findViewById(R.id.revertChat);
        recyclerView = findViewById(R.id.recycler);
        textMensaje = findViewById(R.id.textMensaje);
        nombre = findViewById(R.id.NombreUsuario);
        btnSend = findViewById(R.id.btnSend);
        perfil = findViewById(R.id.perfilUsuario);
        database = FirebaseDatabase.getInstance();
        mensajes = new ArrayList<>();
        //dividerItemDecoration =
         //       new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
       // recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        if (chatAbrir != null) {
            firebase = new FirebaseController("chats", getApplicationContext());

        }
        mostrar();
    }

    /**
     *
     * <p>
     * metodo que guarda los mensajes con el objeto Mensaje
     */
    void guardarMensaje() {

        LocalDateTime fechaHora = LocalDateTime.now(); // fecha y hora actual
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"); // formato deseado
        String fechaHoraString = fechaHora.format(formato);

        Mensaje p = new Mensaje();
        p.setMensaje(textMensaje.getText().toString());
        p.setDate(fechaHoraString);
        p.setToUser(Chat.chatAbrir.getIdTo());
        p.setUid(UUID.randomUUID().toString());
        p.setFromUser(Publicationes.getUserglobal().getUid());
        firebase.getReference().child(chatAbrir.getNombreSala()).child(p.getDate()).setValue(p);
    }

    /**
     * metood que carga la lista de mensajes.
     */
    void mostrar() {

        firebase.getReference().child(chatAbrir.getNombreSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensajes.clear();
                for (DataSnapshot element : dataSnapshot.getChildren()) {
                    Mensaje value = element.getValue(Mensaje.class);
                    System.out.println(value.toString());
                    if (value.getFromUser().equals(Publicationes.getUserglobal().getUid())) {
                        //
                        System.err.println("entrando  em el lambadaa ------");
                        try {
                            Usuario destino = Login.getUsers().stream().filter(e -> (e.getUid().equals(value.getToUser()))).findFirst().get();
                           if(destino != null) {

                               nombre.setText(destino.getUsername());
                               new AuxiliarImg(destino.getNombre(), perfil);
                           }
                        }catch(Exception e){
                        System.err.println("error em el lambadaa ------");
                        }
                    }
                    mensajes.add(value);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}