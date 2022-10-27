package com.example.mobile_assignment_2.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.MainActivity;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.SocialFragment;
//import com.example.mobile_assignment_2.databinding.ActivityChatBinding;
import com.example.mobile_assignment_2.chat.FriendListAdapter;
import com.example.mobile_assignment_2.chat.FriendListData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWindowActivity extends AppCompatActivity {

    public ArrayList<ChatMessage> conversation = new ArrayList<>();
    AppCompatImageView backIcon;
    FirebaseUser fuser;
    TextView oppositeHeadingInChat;
    DatabaseReference userRef;
    DatabaseReference allRef;
    DatabaseReference chatMessageRef;
    FirebaseAuth myAuth;
    ImageView chatSendButton;
    EditText chatInputBar;
    RecyclerView recyclerView;
    List<String> chatIDList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //firebase
        myAuth = FirebaseAuth.getInstance();
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID);
        allRef = FirebaseDatabase.getInstance().getReference();
        chatMessageRef = FirebaseDatabase.getInstance().getReference("chatMessage");



        //find id from xml
        backIcon = findViewById(R.id.chatWindowBackIcon);
        oppositeHeadingInChat = findViewById(R.id.oppositeHeadingInChat);
        oppositeHeadingInChat.setText(FriendListAdapter.username);
        recyclerView =  findViewById(R.id.chatWindowRecycleView);
        chatSendButton = findViewById(R.id.chatSendButton);
        chatInputBar = findViewById(R.id.chatInputBar);


        //methods
        loadDatabase();


        //send button listener
        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chatInputBar.length()==0){

                    Snackbar.make(chatSendButton,"input text could not be empty", Snackbar.LENGTH_SHORT).show();

                }else{
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    DatabaseReference chatMessageRef = allRef.child("chatMessage").push();
                    String chatID = chatMessageRef.getKey();
                    ChatMessage message = new ChatMessage(chatInputBar.getText().toString(),formatter.format(date),R.drawable.old_man,chatID, fuser.getUid());
                    chatMessageRef.setValue(message);
                    DatabaseReference fuserFriendChatRef = userRef.child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("chats").push();
                    fuserFriendChatRef.setValue(chatID);
                    chatInputBar.clearFocus();
                    chatInputBar.setText("");
                    //Log.d("长度：",String.valueOf(chatInputBar.getText().length()));
                    Snackbar.make(chatSendButton,"text has been sent it", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        backSocialFragmentIntent(backIcon);


    }

    private void loadDatabase(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatIDList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    chatIDList.add(snapshot1.getValue().toString());
                }
                chatMessageRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        conversation.clear();
                        for(DataSnapshot snapshot3: snapshot2.getChildren()){
                            String chatMessageRole = snapshot3.child("role").getValue().toString();
                            String chatMessageText = snapshot3.child("senderText").getValue().toString();
                            String chatMessageTime = snapshot3.child("senderTime").getValue().toString();
                            ChatMessage message = new ChatMessage(chatMessageText,chatMessageTime,R.drawable.old_man,"", chatMessageRole);
                            conversation.add(message);
                            MessageAdapter2 messageAdapter2 = new MessageAdapter2(ChatWindowActivity.this, conversation);
                            recyclerView.setAdapter(messageAdapter2);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWindowActivity.this);
                            linearLayoutManager.setStackFromEnd(true);
                            recyclerView.setLayoutManager(linearLayoutManager);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void backSocialFragmentIntent(AppCompatImageView imageView){

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}