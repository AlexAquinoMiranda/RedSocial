package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.redsocial.Firebase.Conversation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {
    FirebaseDatabase database;
    //https://console.firebase.google.com/project/redsocial-68a70/database/redsocial-68a70-default-rtdb/data/~2Fmensaje-1-2?hl=es-419
    DatabaseReference conversationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("conversations/0001-0003");

        conversationRef.push().setValue(new Conversation("\n" +
                "date\n" +
                ":\n" +
                "Valor\n" +
                "20/03/23-18:48\n" +
                "from\n" +
                ":\n" +
                "1\n" +
                "text\n" +
                ":\n" +
                "Valor\n" +
                "hello the  mother fucking\n" +
                "to\n" +
                ":\n" +
                "3\n", "ale"));
        Log.i("----------0", "entro en mensaje chattttt");
    }
}