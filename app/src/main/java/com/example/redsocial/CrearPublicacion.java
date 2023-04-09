package com.example.redsocial;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.redsocial.dto.Publication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;


public class CrearPublicacion extends AppCompatActivity {
    private FloatingActionButton exit;
    private ActivityResultLauncher<String> pickImageLauncher;
    Button btnAbrir, subirImagen;
    EditText titulo, descripcion;
    ImageView imageView;
    UploadTask uploadTask;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference conversationRef;
    private Uri pathImage;


    //implementation 'com.google.firebase:firebase-storage:20.1.0'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        inicializar();
        eventos();
    }


    void inicializar() {
        imageView = findViewById(R.id.imagePublicar);
        exit = findViewById(R.id.cancelar);
        titulo = findViewById(R.id.tituloPublicacion);
        descripcion = findViewById(R.id.descripcionPublicacion);
        subirImagen = findViewById(R.id.subirImagen);
        btnAbrir = findViewById(R.id.abrirGalery);
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Publications");
        //
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                imageView.setImageBitmap(bitmap);
                                pathImage = uri;
                            } catch (FileNotFoundException e) {
                            }

                        }
                    }
                });
    }

    void eventos() {
        subirImagen.setOnClickListener(v -> {
            validarCampos();
        });
        btnAbrir.setOnClickListener(n -> {
            pickImageLauncher.launch("image/*");
        });
        exit.setOnClickListener(v -> {
            abrirVentana(Publicationes.class);
        });
    }

    /**
     * dmetodo que crea una publicación y lo guarda en firebase.
     */
    void crearPublicacion(String a, String b) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Publication p = new Publication();
        p.setLike(0);
        p.setUid(UUID.randomUUID().toString());
        p.setMeGusta(false);
        p.setDescripcion(b);
        p.setUserImage(Publicationes.getUserglobal().getNombre());
        p.setUserName(Publicationes.getUserglobal().getUsername());
        p.setIdUsuario(Publicationes.getUserglobal().getUid());
        p.setTítulo(a);
        p.setUrlImagen(a);
        storageRef = storage.getReference().child(p.getTítulo());//hago referencia con el nombre de la imagen
        conversationRef.child(p.getUid()).setValue(p);//guardo en publicaciones
        saveImage();
    }

    /**
     * metodo que valida que todos los campos esten rellenos.
     */
    void validarCampos() {
        String title, desciption;
        title = this.titulo.getText().toString();
        desciption = this.descripcion.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desciption) || pathImage == null) {
            return;
        } else {
            crearPublicacion(title, desciption);
            abrirVentana(Publicationes.class);
        }

    }

    void abrirVentana(Class view) {
        Intent i = new Intent(getApplicationContext(), view);
        startActivity(i);
        finish();
    }

    /**
     * metodoo para guardar imagen
     */
    void saveImage() {
        if (pathImage != null) {
            uploadTask = storageRef.putFile(pathImage);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // La imagen se subió correctamente
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Hubo un error al subir la imagen
                }
            });
        }
    }
}