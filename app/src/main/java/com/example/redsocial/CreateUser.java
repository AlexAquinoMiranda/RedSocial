package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redsocial.dto.Publication;
import com.example.redsocial.dto.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class CreateUser extends AppCompatActivity {
    private EditText username, nombre, apellido, password, fechanac, email;
    private Button btnCrear;
    FirebaseDatabase database;
    TextView link;
    DatabaseReference conversationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        inicializar();
        eventos();


    }
    void eventos(){
        link.setOnClickListener(v -> {
            abrirLogin();
        });
        this.btnCrear.setOnClickListener(v -> {
                validateData();
        });
    }

    void inicializar() {
        this.username = findViewById(R.id.userName);
        this.nombre = findViewById(R.id.name);
        this.apellido = findViewById(R.id.lastName);
        this.password = findViewById(R.id.passwor);
        this.fechanac = findViewById(R.id.nacimiento);
        this.email = findViewById(R.id.mail);
        this.btnCrear = findViewById(R.id.abrirGalery);
        this.link = findViewById(R.id.linkAlLogin);
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Users");
    }

    void createUser() {
        Usuario usuario = new Usuario();
        usuario.setUid(UUID.randomUUID().toString());
        usuario.setUsername(this.username.getText().toString());
        usuario.setApellido(this.apellido.getText().toString());
        usuario.setNombre(this.nombre.getText().toString());
        usuario.setPassword(this.password.getText().toString());
        usuario.setEmail(this.email.getText().toString());
        usuario.setFechanac(this.fechanac.getText().toString());
        conversationRef.child(usuario.getUid()).setValue(usuario);
    }

    void limpiar(){
        this.username.setText("");
        this.apellido.setText("");
        this.nombre.setText("");
        this.password.setText("");
        this.email.setText("");
        this.fechanac.setText("");
    }

    void validateData() {
        if (this.username.getText().toString().equals("") || apellido.getText().toString().equals("")
                || nombre.getText().toString().equals("") || password.getText().toString().equals("") &&
                email.getText().toString().equals("") || fechanac.getText().toString().equals("")) {
            //datas vacías
            limpiar();
            return;
        } else {
            createUser();
            Toast.makeText(getApplicationContext(), "Registro correcto. Logueate con tu usuario", Toast.LENGTH_LONG);
            abrirLogin();
        }
    }
    void abrirLogin(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}