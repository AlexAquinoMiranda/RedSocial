package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText user, password;
    TextView link;
    Button send;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializacion();
        eventos();
    }

    void eventos() {
        //link para registrarse.
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });
        // autenticar usuario.
        this.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = String.valueOf(user.getText().toString());
                pass = String.valueOf(password.getText().toString());
                if (TextUtils.isEmpty(email)) return;
                if (TextUtils.isEmpty(pass)) return;

                //autenticar
                autenticacion(email, pass);

            }
        });
    }

    void autenticacion(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Authentication correct.",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), homeUsuarios.class);
                            startActivity(i);
                            finish();

                        } else {
                            // fallo la autenticación.
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(getApplicationContext(), "El usuario no esta registrado.", Toast.LENGTH_SHORT).show();
                                //registrar();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    void inicializacion() {
        this.send = (Button) findViewById(R.id.btnLogin);
        this.user = findViewById(R.id.emailLogin);
        this.password = findViewById(R.id.PasswordLogin);
        this.link = findViewById(R.id.linkRegister);
        this.mAuth = FirebaseAuth.getInstance();
    }

}