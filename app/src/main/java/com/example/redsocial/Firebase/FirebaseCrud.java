package com.example.redsocial.Firebase;

import com.example.redsocial.dto.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseCrud {

    FirebaseDatabase database;
    DatabaseReference conversationRef;

    public void create(Usuario user){
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("message");
        conversationRef.child(user.getUid()).setValue(user);
    }

}
