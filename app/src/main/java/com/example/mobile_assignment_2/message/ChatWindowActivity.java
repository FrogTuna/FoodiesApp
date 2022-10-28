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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWindowActivity extends AppCompatActivity {

    public ArrayList<ChatMessage> userConversation = new ArrayList<>();
    public ArrayList<ChatMessage> oppositeUserconversation = new ArrayList<>();
    AppCompatImageView backIcon;
    FirebaseUser fuser;
    TextView oppositeHeadingInChat;
    DatabaseReference userRef;
    DatabaseReference oppositeUserRef;
    DatabaseReference allRef;
    DatabaseReference chatMessageRef;
    DatabaseReference userLastMessageRef;
    DatabaseReference oppositeUserLastMessageRef;
    FirebaseAuth myAuth;
    ImageView chatSendButton;
    EditText chatInputBar;
    RecyclerView userRecyclerView;
    RecyclerView oppositeUserRecyclerView;
    List<String> userChatIDList = new ArrayList<String>();
    List<String> oppositeUserChatIDList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //firebase
        myAuth = FirebaseAuth.getInstance();
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("chats");
        oppositeUserRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("friends").child(fuser.getUid()).child("chats");
        userLastMessageRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("lastMessage");
        oppositeUserLastMessageRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("friends").child(fuser.getUid()).child("lastMessage");
        allRef = FirebaseDatabase.getInstance().getReference();
        chatMessageRef = FirebaseDatabase.getInstance().getReference("chatMessage");



        //find id from xml
        backIcon = findViewById(R.id.chatWindowBackIcon);
        oppositeHeadingInChat = findViewById(R.id.oppositeHeadingInChat);
        oppositeHeadingInChat.setText(FriendListAdapter.username);
        userRecyclerView =  findViewById(R.id.chatWindowRecycleView);
        oppositeUserRecyclerView = findViewById(R.id.chatWindowRecycleView);
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
                    DatabaseReference fuserFriendChatRef = userRef.push();
                    DatabaseReference oppositeFuserChatRef = oppositeUserRef.push();
                    DatabaseReference userLastMessageRef2 = userLastMessageRef.push();
                    DatabaseReference oppositeUserLastMessageRef2 = oppositeUserLastMessageRef.push();
                    fuserFriendChatRef.setValue(chatID);
                    oppositeFuserChatRef.setValue(chatID);
                    userLastMessageRef2.setValue(chatInputBar.getText().toString());
                    oppositeUserLastMessageRef2.setValue(chatInputBar.getText().toString());
                    chatInputBar.clearFocus();
                    chatInputBar.setText("");
                    Snackbar.make(chatSendButton,"text has been sent it", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        backSocialFragmentIntent(backIcon);


    }

    private void loadDatabase(){


        //oppositeUser listener
        oppositeUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oppositeUserChatIDList.clear();

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    oppositeUserChatIDList.add(snapshot1.getValue().toString());
                    //Log.d("chatIDList1", snapshot1.getValue().toString());
                }

                chatMessageRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        oppositeUserconversation.clear();
                        for(DataSnapshot snapshot3: snapshot2.getChildren()){

                            for(int i = 0; i < oppositeUserChatIDList.size(); i++){
                                if(snapshot3.getKey().matches(oppositeUserChatIDList.get(i))){
                                    Log.d("chatIDList3", snapshot3.getKey());
                                    String chatMessageRole = snapshot3.child("role").getValue().toString();
                                    String chatMessageText = snapshot3.child("senderText").getValue().toString();
                                    String chatMessageTime = snapshot3.child("senderTime").getValue().toString();
                                    String chatMessagechatID = snapshot3.child("chatID").getValue().toString();
                                    ChatMessage message = new ChatMessage(chatMessageText,chatMessageTime,R.drawable.old_man,chatMessagechatID, chatMessageRole);
                                    oppositeUserconversation.add(message);

                                }
                            }

                            MessageAdapter2 messageAdapter2 = new MessageAdapter2(ChatWindowActivity.this, oppositeUserconversation);
                            oppositeUserRecyclerView.setAdapter(messageAdapter2);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWindowActivity.this);
                            if(oppositeUserconversation.size()>5){
                                linearLayoutManager.setStackFromEnd(true);
                            }
                            oppositeUserRecyclerView.setLayoutManager(linearLayoutManager);
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




        //my listener
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userChatIDList.clear();

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    userChatIDList.add(snapshot1.getValue().toString());
                    //Log.d("chatIDList1", snapshot1.getValue().toString());
                }
                chatMessageRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        userConversation.clear();
                        for(DataSnapshot snapshot3: snapshot2.getChildren()){

                            for(int i = 0; i < userChatIDList.size(); i++){
                                if(snapshot3.getKey().matches(userChatIDList.get(i))){
                                    Log.d("chatIDList3", snapshot3.getKey());
                                    String chatMessageRole = snapshot3.child("role").getValue().toString();
                                    String chatMessageText = snapshot3.child("senderText").getValue().toString();
                                    String chatMessageTime = snapshot3.child("senderTime").getValue().toString();
                                    String chatMessagechatID = snapshot3.child("chatID").getValue().toString();
                                    ChatMessage message = new ChatMessage(chatMessageText,chatMessageTime,R.drawable.old_man,chatMessagechatID, chatMessageRole);
                                    userConversation.add(message);

                                }
                            }

                            MessageAdapter2 messageAdapter2 = new MessageAdapter2(ChatWindowActivity.this, userConversation);
                            userRecyclerView.setAdapter(messageAdapter2);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWindowActivity.this);
                            if(userConversation.size()>5){
                                linearLayoutManager.setStackFromEnd(true);
                            }
                            userRecyclerView.setLayoutManager(linearLayoutManager);
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