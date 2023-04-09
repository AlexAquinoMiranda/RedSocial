package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.redsocial.Adapter.AdapterPublication;
import com.example.redsocial.Auxiliares.AuxChat;
import com.example.redsocial.Firebase.AuxiliarImg;
import com.example.redsocial.Firebase.FirebaseController;
import com.example.redsocial.dto.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Usuario Personal.
 */
public class PerfilUsuario extends AppCompatActivity {
    //String usuario = getIntent().getStringExtra("username");
    private static Usuario userMostrar;
    private FirebaseController firebase;
    public static String idUsuario;
    public static ArrayList<Usuario> users = new ArrayList<>();
    TextView nombre, descripcion, countPublicacion, countContact;
    FloatingActionButton ajustes;
    RecyclerView imagenes;
    CircleImageView fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        inicializar();

       eventos();

    }

    private void eventos() {
        ajustes.setOnClickListener(n -> {

          //  Usuario destino = Login.getUsers().stream().filter(e -> e.getUid().equals(idUsuario)).findFirst().get();

          //  new AuxChat(destino);
            Intent i = new Intent(getApplicationContext(), Publicationes.class);
            startActivity(i);
            finish();

        });
    }

    void rellenaDatos(Usuario u) {
        nombre.setText(u.getUsername());
        descripcion.setText(u.getNombre());
        if(Publicationes.getListaMostrar() != null) {
            countPublicacion.setText(String.valueOf( Publicationes.getPublicationes().size()));
        }
        countContact.setText(String.valueOf(Publicationes.getPublicationes().size()));
        new AuxiliarImg(u.getNombre(), fotoPerfil);
        imagenes.setLayoutManager(
                new GridLayoutManager(this, 3));
        AdapterPublication adapter = new AdapterPublication(Publicationes.getPublicationes(), this);
        imagenes.setAdapter(adapter);
        //Publicationes.getPublicationes().
    }

    void inicializar() {
        nombre = findViewById(R.id.perfilUsuarioName);
        descripcion = findViewById(R.id.perfilDescripcionUser);
        countContact = findViewById(R.id.cantidadContac);
        countPublicacion = findViewById(R.id.cantidadPublicacion);
        ajustes = findViewById(R.id.ajustes);

        imagenes = findViewById(R.id.FotosUser);
        fotoPerfil = findViewById(R.id.fotoPerfilUser);
        firebase = new FirebaseController("Users", getApplicationContext());
        encontrarUsuario();

    }


    boolean encontrarUsuario() {
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
                        userMostrar = user_aux;
                        rellenaDatos(user_aux);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return cond[0];
    }
}