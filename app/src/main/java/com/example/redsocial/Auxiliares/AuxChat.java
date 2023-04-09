package com.example.redsocial.Auxiliares;

import android.util.Log;

import com.example.redsocial.Chat;
import com.example.redsocial.Contactos;
import com.example.redsocial.Publicationes;
import com.example.redsocial.dto.CreateChat;
import com.example.redsocial.dto.Mensaje;
import com.example.redsocial.dto.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * clase de ayuda para crear una chat.
 */
public class AuxChat {
    DatabaseReference conversationRef;
    FirebaseDatabase database;
    static Usuario user;
    static ArrayList<CreateChat> contactos;

    public AuxChat(Usuario userTo) {
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Conversaciones");
        AuxChat.contactos = new ArrayList<>();
        mostrar();
        crearConversation(userTo);
    }

    /**
     * metodo que crea en firebase una referencia hacia su chat.
     *
     * @param userTo
     */
    public void crearConversation(Usuario userTo) {

        System.out.println(" el id del logeado es : " + Publicationes.getUserglobal().getUid() +
                "\n y el del que va a enviar el mensaje es " + userTo.getUid());
        user = userTo;
        CreateChat encontrado = null;
        conversationRef = database.getReference("Conversaciones");
        int valorChat = 0;

        if (encontrarChat(userTo) != null) {
            System.out.print("true ---------");
            Chat.chatAbrir = encontrarChat(userTo);
            return;
        } else {
            System.out.print("Falseeee ---------");
            CreateChat con = new CreateChat();
            con.setNombreSala(Publicationes.getUserglobal().getUid() + "-" + userTo.getUid());
            con.setIdFrom(Publicationes.getUserglobal().getUid());
            con.setIdTo(userTo.getUid());
            conversationRef.child(con.getNombreSala()).setValue(con);
            Chat.chatAbrir = con;
        }

        ///abrir chat con estos datos
    }

    CreateChat encontrarChat(Usuario user) {
        CreateChat valor = null;
        if (!Contactos.contactos.isEmpty()) {
            for (CreateChat value : Contactos.contactos) {
                System.out.println(" ----------------" +value.toString());
                if (value.getNombreSala().equals(Publicationes.getUserglobal().getUid() + "-" + user.getUid())
                        || value.getNombreSala() == Publicationes.getUserglobal().getUid() + "-" + user.getUid() ||
                        value.getNombreSala() == user.getUid() + "-" + Publicationes.getUserglobal().getUid()
                        || value.getNombreSala().equals(user.getUid() + "-" + Publicationes.getUserglobal().getUid())) {
                    // if (value.getNombreSala() == "49d758ea-48a8-44cb-91fe-6b8b8874c833-2be14fc6-88a0-41dd-9886-896a9e602766" ||
                     //         value.getNombreSala().equals("49d758ea-48a8-44cb-91fe-6b8b8874c833-2be14fc6-88a0-41dd-9886-896a9e602766")) {

                    System.out.print("trueeee  foooorr---------");
                    System.out.print("trueeee ---------");

                    valor = value;
                }

            }
        } else {
            System.out.println("listavaciaaa --------");
        }
        return valor;
    }

    public void mostrar() {
        conversationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AuxChat.contactos.clear();
                System.out.println("entrandi en for de chatsssss\n");
                for (DataSnapshot element : dataSnapshot.getChildren()) {
                    CreateChat value = element.getValue(CreateChat.class);
                    System.out.println(value.toString());
                    AuxChat.contactos.add(value);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("------", "Failed to read value.", error.toException());
            }
        });

    }


}