package com.example.mobile_assignment_2.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.databinding.ActivityChatBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatWindowActivity extends AppCompatActivity {

    ArrayList<ChatMessage> conversationRight = new ArrayList<>();
    ArrayList<ChatMessage> conversationLeft = new ArrayList<>();
    int[] sender = {R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man};
    int[] recevier = {R.drawable.female,R.drawable.female, R.drawable.female, R.drawable.female, R.drawable.female};
    int[] senderIndex = {1,2,3,5,8};
    int[] recevierIndex = {4,6,7,9,10};
    AppCompatImageView backIcon;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //find id from xml
        backIcon = findViewById(R.id.chatWindowBackIcon);
        RecyclerView recyclerView =  findViewById(R.id.chatWindowRecycleView);

        //concat left side and right side messages as
        setUpConversationRight();
        setUpConversationLeft();

        //invoke recycle view in chat window activity
        MessageAdapter2 messageAdapter2 = new MessageAdapter2(this, conversationLeft, conversationRight);
        recyclerView.setAdapter(messageAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //back to social fragment
        backSocialFragmentIntent(backIcon);




    }

    private void backSocialFragmentIntent(AppCompatImageView imageView){

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatWindowActivity.this, startChatActivity.class);
                startActivity(intent);
            }
        });
    }


    //left side message
    private void setUpConversationLeft(){
        String [] messageList = getResources().getStringArray(R.array.messageLeftText);
        String [] messageTime = getResources().getStringArray(R.array.messageTimeLeft);
        for(int i = 0; i < messageList.length; i++){

            conversationLeft.add(new ChatMessage(messageList[i], messageTime[i], recevier[i], "receiver"));

        }
    }

    //right side message
    private void setUpConversationRight(){
        String [] messageList = getResources().getStringArray(R.array.messageRightText);
        String [] messageTime = getResources().getStringArray(R.array.messageTimeRight);
        for(int i = 0; i < messageList.length; i++){

            conversationRight.add(new ChatMessage(messageList[i], messageTime[i], sender[i], "sender"));

        }
    }
}