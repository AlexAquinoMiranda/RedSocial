package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.redsocial.Adapter.AdapterPublication;
import com.example.redsocial.dto.Publication;
import com.example.redsocial.dto.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.squareup.picasso.Picasso;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class Publicationes extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton publicacione, subirFoto, mensajes, contactos;
    TextView saludo;
    DatabaseReference conversationRef;
    FirebaseDatabase database;
    CircleImageView fotoPrueba;
    StorageReference storageRef;

    public static ArrayList<Publication> getListaMostrar() {
        return listaMostrar;
    }

    private static ArrayList<Publication> listaMostrar;

    public static ArrayList<Publication> getPublicationes() {
        return publicationes;
    }

    private static ArrayList<Publication> publicationes;
    public static Usuario userglobal;


    void inicializar() {
        saludo = findViewById(R.id.saludoUser);
        subirFoto = findViewById(R.id.subirFoto);
        recyclerView = findViewById(R.id.recyclerPublication);
        mensajes = findViewById(R.id.chat);
        publicacione = findViewById(R.id.publi);
        //fotoPrueba = findViewById(R.id.FotoPerfilPc);
        contactos = findViewById(R.id.contactos);
        listaMostrar = new ArrayList<>();
        publicationes = new ArrayList<>();
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        storageRef = FirebaseStorage.getInstance().getReference().child(Publicationes.getUserglobal().getNombre());
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Publications");

    }

    public static Usuario getUserglobal() {
        return userglobal;
    }

    public static void setUserglobal(Usuario userglobal) {
        Publicationes.userglobal = userglobal;
    }

    void eventos() {
        read();
       // mostrarFotoEjemplo();
        subirFoto.setOnClickListener(n -> {
            abrirVentana(CrearPublicacion.class);
        });
        mensajes.setOnClickListener(n -> {
            PerfilUsuario.idUsuario = Publicationes.getUserglobal().getUid();
            abrirVentana(PerfilUsuario.class);
        });
        publicacione.setOnClickListener(n -> {
            abrirVentana(Publicationes.class);
        });
        contactos.setOnClickListener(n -> {
            abrirVentana(Contactos.class);
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        inicializar();
        //read();
        //
      /**if ( Publicationes.getListaMostrar().isEmpty() != false) {
          AdapterPublication adapter = new AdapterPublication(Publicationes.getPublicationes(), this);
          recyclerView.setAdapter(adapter);
        }else{
       //   read();
      }
            System.out.println("lista no esta vacia --------- ");
           // AdapterPublication adapter = new AdapterPublication( publicationes, this);
            AdapterPublication adapter = new AdapterPublication(Publicationes.getPublicationes(), this);

            recyclerView.setAdapter(adapter);
**/
        //} else {
          /**  System.out.println("lista  esta vacia --------- ");
            Publication a = new Publication();
            a.setIdUsuario("49d758ea-48a8-44cb-91fe-6b8b8874c833");
            a.setTítulo("Publication");
            a.setDescripcion("muuuuucaha  descripción");
            a.setUrlImagen("4076CE15-5711-40DA-B7BA-CD20C0D71606.jpeg");
            a.setUserName("ale");

            a.setUserImage("4076CE15-5711-40DA-B7BA-CD20C0D71606.jpeg");
            a.setUid("0b3dd258-aaae-4319-a81e-821a35805165");
            a.setLike(10);
            a.setMeGusta(false);
            publicationes.add(a);

            AdapterPublication adapter = new AdapterPublication(publicationes, this);
            recyclerView.setAdapter(adapter);**/
          //  recyclerView.setEnabled(true);

        //}

        eventos();

    }

    /**
     * Descargar la imagen y mostrarla en el ImageView utilizando Picasso
     */
    void mostrarFotoEjemplo() {

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Cargar la imagen en el ImageView utilizando Picasso
                Picasso.get()
                        .load(uri)
                        .into(fotoPrueba);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    void read() {

        conversationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Publicationes.getListaMostrar().clear();
                Publicationes.getPublicationes().clear();

                for (DataSnapshot element : dataSnapshot.getChildren()) {
                    Publication userAux = element.getValue(Publication.class);
                    System.out.println(userAux.toString());
                    Publicationes.getPublicationes().add(userAux);
                }

               AdapterPublication adapter = new AdapterPublication(Publicationes.getPublicationes(), Publicationes.this);

                recyclerView.setAdapter(adapter);
               /** Publicationes.getListaMostrar()
                        .addAll(Publicationes.getPublicationes().stream()
                                .filter((a) -> (a.getIdUsuario()
                                        .equals(Publicationes.getUserglobal().getUid())))
                                .collect(Collectors.toList()));//tiene que estar en el perfil de usuario

**/
                  for (Publication p : Publicationes.getPublicationes()) {
                 if (p.getIdUsuario() == Publicationes.getUserglobal().getUid()) {
                 System.out.println("son iguales el id ------------------\n" + p.toString());
                 Publicationes.getListaMostrar().add(p);//tiene que estar en el perfil de usuario
                 }
                 }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("------", "Failed to read value.", error.toException());
            }
        });
    }


    void abrirVentana(Class view) {
        Intent i = new Intent(getApplicationContext(), view);
        startActivity(i);
        finish();
    }
}