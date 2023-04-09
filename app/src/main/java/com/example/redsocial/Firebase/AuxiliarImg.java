package com.example.redsocial.Firebase;

import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuxiliarImg {
   private  StorageReference storageRef;

   public AuxiliarImg (String archivo, CircleImageView image){
       this.cargarFotos(archivo, image);
   }


   public AuxiliarImg(String archivo, ImageView image){
       this.cargarImageView(archivo,image);
   }
    private void cargarImageView(String archivo, ImageView image) {
        storageRef = FirebaseStorage.getInstance().getReference().child(archivo);
        mostrarFotoEjemploView(image);

    }
    private  void mostrarFotoEjemploView(ImageView foto) {
        // Descargar la imagen y mostrarla en el ImageView utilizando Picasso
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Cargar la imagen en el ImageView utilizando Picasso
                Picasso.get()
                        .load(uri)
                        .into(foto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception exception) {
                // Manejar el error en caso de que la descarga de la URL falle
            }
        });
    }
    private void cargarFotos(String archivo, CircleImageView image){
        storageRef = FirebaseStorage.getInstance().getReference().child(archivo);
        mostrarFotoEjemplo(image);
    }
    private  void mostrarFotoEjemplo(CircleImageView foto) {
        // Descargar la imagen y mostrarla en el ImageView utilizando Picasso
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Cargar la imagen en el ImageView utilizando Picasso
                Picasso.get()
                        .load(uri)
                        .into(foto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception exception) {
                // Manejar el error en caso de que la descarga de la URL falle
            }
        });
    }
}
