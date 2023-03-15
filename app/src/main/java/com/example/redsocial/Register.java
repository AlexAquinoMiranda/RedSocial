package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    TextInputEditText user, password, confirmPassword;
    Button send;
    TextView link;
    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    void inicializar() {
        this.send = (Button) findViewById(R.id.btnAbrirRegister);
        this.user = findViewById(R.id.usuarioRegister);
        this.password = findViewById(R.id.passwordRegister);
        this.confirmPassword = findViewById(R.id.passwordConfirm);
        this.mAuth = FirebaseAuth.getInstance();
    }

    void createUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Toast.makeText(Register.this, "Authentication saved.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    void eventos() {
        //abrimos login
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
        //creamos usuario
        this.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, confirmPass;
                email = String.valueOf(user.getText().toString());
                pass = String.valueOf(password.getText().toString());
                confirmPass = String.valueOf(confirmPassword.getText().toString());

                if (TextUtils.isEmpty(email)) return;
                if (TextUtils.isEmpty(confirmPass)) return;
                if (TextUtils.isEmpty(pass)) return;

                if (confirmPass.equals(pass)) {
                    createUser(email, pass);
                } else {
                    Toast.makeText(Register.this, "Contraseñas distintas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializar();
        eventos();


    }
}