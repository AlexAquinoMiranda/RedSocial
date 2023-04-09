package com.example.redsocial.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.Firebase.AuxiliarImg;
import com.example.redsocial.Login;
import com.example.redsocial.PanelUser;
import com.example.redsocial.PerfilUsuario;
import com.example.redsocial.Publicationes;
import com.example.redsocial.R;
import com.example.redsocial.dto.Publication;
import com.example.redsocial.dto.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPublication extends RecyclerView.Adapter<AdapterPublication.publication> {
    private ArrayList<Publication> lisPublicaciones;
    private Context contexto;

    private DatabaseReference conversationRef;
    private FirebaseDatabase database;

    public AdapterPublication(ArrayList<Publication> lista, Context con) {
        this.lisPublicaciones = lista;
        this.contexto = con;
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Publications");
        System.out.println("entrando en adapter publiiiiii");
    }


    @NonNull
    @Override
    public AdapterPublication.publication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nueva_publicacion, null, false);
        return new AdapterPublication.publication(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPublication.publication holder, int position) {
        int value = position;
        System.out.println(" desde sdarpertr " + lisPublicaciones.get(position).toString());

        holder.userName.setText(lisPublicaciones.get(value).getUserName());

        // holder.userName.setText();

        holder.countLike.setText(" A " + lisPublicaciones.get(position).getLike() + " personas le gusta esta foto.");
        //tengo que ver como cargo la imagen.
        new AuxiliarImg(lisPublicaciones.get(position).getUserImage(), holder.imgUser);

        new AuxiliarImg(lisPublicaciones.get(position).getUrlImagen(), holder.imgPublication);

        holder.imgUser.setOnClickListener(n->{
            //abro historia.
        });

        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  PerfilUsuario.idUsuario = lisPublicaciones.get(value).getIdUsuario();
                if (lisPublicaciones.get(value).getIdUsuario().equals(Publicationes.getUserglobal().getUid())) {
                    PerfilUsuario.idUsuario = lisPublicaciones.get(value).getIdUsuario();
                    Intent i = new Intent(contexto, PerfilUsuario.class);
                    // i.putExtra("username", lisPublicaciones.get(value).getIdUsuario());
                    view.getContext().startActivity(i);
                } else {
                    PanelUser.idUsuario = lisPublicaciones.get(value).getIdUsuario();
                    Intent i = new Intent(contexto, PanelUser.class);
                    // i.putExtra("username", lisPublicaciones.get(value).getIdUsuario());
                    view.getContext().startActivity(i);
                }


            }
        });

        if (lisPublicaciones.get(value).isMeGusta()) {
            holder.btnLike.setImageResource(R.drawable.like);
        } else {
            holder.btnLike.setImageResource(R.drawable.dislike);
        }

        holder.btnLike.setOnClickListener(n -> {

            if (lisPublicaciones.get(value).isMeGusta()) {
                lisPublicaciones.get(value).setLike(lisPublicaciones.get(value).getLike() - 1);
                lisPublicaciones.get(value).setMeGusta(false);
                holder.btnLike.setImageResource(R.drawable.dislike);
            } else {
                lisPublicaciones.get(value).setLike(lisPublicaciones.get(value).getLike() + 1);
                lisPublicaciones.get(value).setMeGusta(true);
                holder.btnLike.setImageResource(R.drawable.like);
            }
            conversationRef.child(lisPublicaciones.get(value).getUid()).setValue(lisPublicaciones.get(value));
            holder.countLike.setText(" A " + lisPublicaciones.get(value).getLike() + " personas le gusta esta foto.");
        });
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.lisPublicaciones.size();
    }

    public class publication extends RecyclerView.ViewHolder {
        TextView userName;
        TextView countLike;
        ImageView imgPublication;
        CircleImageView imgUser;
        FloatingActionButton btnLike;

        public publication(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            imgPublication = itemView.findViewById(R.id.publication_picture);
            imgUser = itemView.findViewById(R.id.profile_picture);
            btnLike = itemView.findViewById(R.id.publication_like);
            countLike = itemView.findViewById(R.id.cantidad_likes);


        }
    }
}
