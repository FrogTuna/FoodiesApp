package com.example.mobile_assignment_2.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.databinding.ActivityChatBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ChatWindowActivity extends AppCompatActivity {

    ArrayList<ChatMessage> conversationRight = new ArrayList<>();
    ArrayList<ChatMessage> conversationLeft = new ArrayList<>();
    int[] sender = {R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man};
    int[] recevier = {R.drawable.female,R.drawable.female, R.drawable.female, R.drawable.female, R.drawable.female};
    int[] senderIndex = {1,2,3,5,8};
    int[] recevierIndex = {4,6,7,9,10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //chatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat_window);

        RecyclerView recyclerView =  findViewById(R.id.chatWindowRecycleView);
        setUpConversationRight();
        setUpConversationLeft();
        MessageAdapter2 messageAdapter2 = new MessageAdapter2(this, conversationLeft, conversationRight);
        recyclerView.setAdapter(messageAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setUpConversationLeft(){
        String [] messageList = getResources().getStringArray(R.array.messageLeftText);
        String [] messageTime = getResources().getStringArray(R.array.messageTimeLeft);
        for(int i = 0; i < messageList.length; i++){

            conversationLeft.add(new ChatMessage(messageList[i], messageTime[i], recevier[i], "receiver", recevierIndex[i]));

        }
    }


    private void setUpConversationRight(){
        String [] messageList = getResources().getStringArray(R.array.messageRightText);
        String [] messageTime = getResources().getStringArray(R.array.messageTimeRight);
        for(int i = 0; i < messageList.length; i++){

            conversationRight.add(new ChatMessage(messageList[i], messageTime[i], sender[i], "sender", senderIndex[i]));

        }
    }


//    ArrayList<ChatMessage> conversation = new ArrayList<>();
//
//    int[] imageList = {R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man};
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //chatBinding = ActivityChatBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_chat_window);
//
//        RecyclerView recyclerView =  findViewById(R.id.chatWindowRecycleView);
//        setUpConversation();
//        MessageAdapter messageAdapter = new MessageAdapter(this, conversation);
//        recyclerView.setAdapter(messageAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//
//    private void setUpConversation(){
//        String [] messageList = getResources().getStringArray(R.array.messageList);
//        String [] messageTime = getResources().getStringArray(R.array.messageTime);
//        for(int i = 0; i < messageList.length; i++){
//
//            conversation.add(new ChatMessage(messageList[i], messageTime[i], imageList[i]));
//
//        }
//    }

}