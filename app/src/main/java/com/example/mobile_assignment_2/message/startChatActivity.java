package com.example.mobile_assignment_2.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.C;

import java.time.LocalTime;

public class startChatActivity extends AppCompatActivity {


    Button startChatBtn;
    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;
    DatabaseReference chatMessageDatabase;
    ChatMessage leoMessageOne = new ChatMessage("hello from leo","12:07pm",1,"leo");
    ChatMessage peterMessageOne = new ChatMessage("hello from peter","12:08pm",2,"peter");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);
        startChatBtn = findViewById(R.id.StartChatBtn);
        chatMessageDatabase = FirebaseDatabase.getInstance().getReference();
        goToConversationIntent(startChatBtn);

    }



    private void goToConversationIntent(Button button){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(startChatActivity.this, ChatWindowActivity.class);
                chatMessageDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        
                        DatabaseReference messageRef = chatMessageDatabase.child("chatMessage").push();
                        messageRef.setValue(leoMessageOne);
                        //messageRef.setValue(peterMessageOne);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                startActivity(intent);
            }
        });
    }
}