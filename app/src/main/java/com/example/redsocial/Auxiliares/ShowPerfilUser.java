package com.example.redsocial.Auxiliares;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.redsocial.Firebase.FirebaseController;
import com.example.redsocial.PerfilUsuario;
import com.example.redsocial.dto.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowPerfilUser {
    private static Usuario userMostrar;
    private String idUsuario;
    public static ArrayList<Usuario> users = new ArrayList<>();
    private FirebaseController firebase;

    public ShowPerfilUser(String idUser, Context  c) {
        this.idUsuario = idUser;
        inicializar(c);
    }

    void inicializar(Context c) {
        firebase = new FirebaseController("Users",c);
    }

    public Usuario encontrarUsuario() {
        System.out.println("el id essssss " + idUsuario);
        final boolean[] cond = {false};
        firebase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PerfilUsuario.users.clear();

                for (DataSnapshot element : snapshot.getChildren()) {
                    Usuario userAux = element.getValue(Usuario.class);
                    System.out.println(userAux.toString());
                    users.add(userAux);
                }


                for (Usuario user_aux : users) {
                    if (user_aux.getUid().equals(idUsuario)) {
                        System.out.println(user_aux.toString());
                        cond[0] = true;
                        userMostrar = user_aux;//rellenaDatos(user_aux);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  null;
    }
}
