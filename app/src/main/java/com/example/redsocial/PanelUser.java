package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.redsocial.Adapter.AdapterPublication;
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
 * perfil de los demas usuarios.
 */
public class PanelUser extends AppCompatActivity {

    FloatingActionButton nuevo, eliminar, modificar, ajustes;
    private static Usuario userMostrarPublico;
    CircleImageView fotoPerfil;

    private static Usuario userMostrarPublic;
    TextView nombre, descripcion, countPublicacion, countContact;
    public static String idUsuario;
    RecyclerView imagenes;
    public static ArrayList<Usuario> users = new ArrayList<>();
    private FirebaseController firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_user);
        inicializar();
        cargarDatosUser();
        encontrarUsuario();

    }


    void inicializar(){

        nombre = findViewById(R.id.perfilUsuarioNamePublic);
        descripcion = findViewById(R.id.perfilDescripcionUserPublic);
        countContact = findViewById(R.id.cantidadContacPubl);
        countPublicacion = findViewById(R.id.cantidadPublicacionPubli);
        imagenes = findViewById(R.id.FotosUserPublic);
        fotoPerfil = findViewById(R.id.fotoPerfilUsuarios);
        firebase = new FirebaseController("Users", getApplicationContext());
        encontrarUsuario();

    }
    void rellenaDatos(Usuario u) {
        nombre.setText(u.getUsername());
        descripcion.setText(u.getNombre());
        if (Publicationes.getListaMostrar() != null) {
            countPublicacion.setText(String.valueOf(Publicationes.getListaMostrar().size()));
        }
        countContact.setText(String.valueOf(Publicationes.getPublicationes().size()));
        new AuxiliarImg(u.getNombre(), fotoPerfil);
        imagenes.setLayoutManager(new GridLayoutManager(this, 3));
        AdapterPublication adapter = new AdapterPublication(Publicationes.getPublicationes(), this);
        imagenes.setAdapter(adapter);
        //Publicationes.getPublicationes().
    }

    void cargarDatosUser() {
     //   nombre.setText(Publicationes.getUserglobal().getUsername());
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
                        userMostrarPublico = user_aux;
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