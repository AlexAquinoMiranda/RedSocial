package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.redsocial.Firebase.FirebaseController;
import com.example.redsocial.dto.Usuario;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Login en donde se autentican los usuarios
 */
public class Login extends AppCompatActivity {
    EditText user, password;
    TextView link;
    Button send;
    private FirebaseController firebase;
    private static ArrayList<Usuario> users = new ArrayList<>();

    /**
     * autenticarUsuario en donde recibe sus credenciales y los compara con los existentes en la lista.
     *
     * @param email
     * @param pass
     */
    private void autenticarUsuario(String email, String pass) {
        HashMap dataUser = new HashMap();
        dataUser.put("username", email);
        dataUser.put("password", pass);
        System.out.print(email + " " + pass);
        firebase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Login.getUsers().clear();
                for (DataSnapshot element : snapshot.getChildren()) {
                    Usuario userAux = element.getValue(Usuario.class);
                    Login.users.add(userAux);
                }

                /**   Usuario userPrueba = Login.users.stream().filter((a) -> (a.getUsername().equals(email)  && a.getPassword().equals(pass))).findFirst().get();
                 if(userPrueba!= null){
                 Publicationes.userglobal = userPrueba;
                 abrirVentana(Publicationes.class);
                 }else {
                 Toast.makeText(getApplicationContext(), "Credenciales Incorrecta. Ingréselas nuevamente", Toast.LENGTH_SHORT).show();
                 user.setText("");
                 password.setText("");

                 }**/

                for (Usuario user_aux : users) {
                    if ((user_aux.getUsername().equals(dataUser.get("username").toString()) ||
                            user_aux.getEmail().equals(dataUser.get("username").toString()) &&
                                    user_aux.getPassword().equals(dataUser.get("password")))) {
                        System.out.println(user_aux.toString());
                        Publicationes.userglobal = user_aux;
                        abrirVentana(Publicationes.class);
                    }else {
                        Toast.makeText(getApplicationContext(), "Credenciales Incorrecta. Ingréselas nuevamente", Toast.LENGTH_SHORT).show();
                        user.setText("");
                        password.setText("");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
        link.setOnClickListener(v -> {
            abrirVentana(CreateUser.class);
        });
        // autenticar usuario.
        this.send.setOnClickListener(v -> {
            String email, pass;
            email = String.valueOf(user.getText().toString());
            pass = String.valueOf(password.getText().toString());
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) return;
            autenticarUsuario(email, pass);
        });
    }


    void inicializacion() {
        this.send = (Button) findViewById(R.id.btnLogin);
        this.user = findViewById(R.id.emailLogin);
        this.password = findViewById(R.id.PasswordLogin);
        this.link = findViewById(R.id.linkRegister);
        firebase = new FirebaseController("Users", getApplicationContext());
    }

    public static ArrayList<Usuario> getUsers() {
        return users;
    }

    /**
     * Metodo que recibe una venata y la visualiza.
     *
     * @param view ventana  visualizar
     */
    public void abrirVentana(Class view) {
        Intent i = new Intent(getApplicationContext(), view);
        startActivity(i);
        finish();
    }

}