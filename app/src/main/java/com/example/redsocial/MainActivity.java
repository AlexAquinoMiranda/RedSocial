package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;
    void eventos() {
        btnRegister.setOnClickListener(v ->{
                Intent i = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(i);
        });
        btnLogin.setOnClickListener(n -> {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
        });
    }

    void inicializar() {
        btnLogin = findViewById(R.id.btnAbrirLogin);
        btnRegister = findViewById(R.id.abrirGalery);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        eventos();
    }
}