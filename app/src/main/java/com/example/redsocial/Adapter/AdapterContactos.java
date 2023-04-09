package com.example.redsocial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.Auxiliares.AuxChat;
import com.example.redsocial.Chat;
import com.example.redsocial.Firebase.AuxiliarImg;
import com.example.redsocial.PerfilUsuario;
import com.example.redsocial.Publicationes;
import com.example.redsocial.R;
import com.example.redsocial.dto.Contacts;
import com.example.redsocial.dto.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.Contact> {

    public AdapterContactos(ArrayList<Usuario> contactos, Context context) {
        this.contactos = contactos;
        this.context = context;
    }

    private ArrayList<Usuario> contactos;
    private Context context;


    @NonNull
    @Override
    public AdapterContactos.Contact onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liscontact, null, false);
        return new AdapterContactos.Contact(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContactos.Contact holder, int position) {
        Usuario con = this.contactos.get(position);
        holder.nombreUser.setText(con.getUsername());
        new AuxiliarImg(con.getNombre(), holder.imagen);

        holder.send.setOnClickListener(n -> {
            AuxChat a = new AuxChat(con);
            //a.mostrar();
           // a.crearConversation(con);

            System.out.println("abrir chatttttttt");
            Intent i = new Intent(context, Chat.class);
            n.getContext().startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return this.contactos.size();
    }

    public class Contact extends RecyclerView.ViewHolder {
        private TextView nombreUser;
        CircleImageView imagen ;
        FloatingActionButton send;

        public Contact(@NonNull View itemView) {
            super(itemView);

            send = itemView.findViewById(R.id.floatingActionButton2);
            nombreUser = itemView.findViewById(R.id.nombreContacto);
            imagen = itemView.findViewById(R.id.imageView5);
            //imageView5
        }
    }

}
