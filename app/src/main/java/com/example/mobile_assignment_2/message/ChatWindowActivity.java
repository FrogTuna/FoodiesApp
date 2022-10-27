package com.example.mobile_assignment_2.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChatWindowActivity extends AppCompatActivity {

    public ArrayList<ChatMessage> conversationRight = new ArrayList<>();
//    ArrayList<ChatMessage> conversationLeft = new ArrayList<>();
//    int[] sender = {R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man, R.drawable.old_man};
//    int[] recevier = {R.drawable.female,R.drawable.female, R.drawable.female, R.drawable.female, R.drawable.female};
//    int[] senderIndex = {1,2,3,5,8};
//    int[] recevierIndex = {4,6,7,9,10};
    AppCompatImageView backIcon;
    FirebaseUser fuser;
    TextView oppositeHeadingInChat;
    DatabaseReference userRef;
    DatabaseReference allRef;
    FirebaseAuth myAuth;
    ImageView chatSendButton;
    EditText chatInputBar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //firebase
        myAuth = FirebaseAuth.getInstance();
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        allRef = FirebaseDatabase.getInstance().getReference();



        //find id from xml
        backIcon = findViewById(R.id.chatWindowBackIcon);
        oppositeHeadingInChat = findViewById(R.id.oppositeHeadingInChat);
        recyclerView =  findViewById(R.id.chatWindowRecycleView);
        chatSendButton = findViewById(R.id.chatSendButton);
        chatInputBar = findViewById(R.id.chatInputBar);
        loadDatabase();


        //concat left side and right side messages
        setUpConversationRight();
//        setUpConversationLeft();
        //invoke recycle view in chat window activity
//        MessageAdapter2 messageAdapter2 = new MessageAdapter2(this, conversationRight);
//        recyclerView.setAdapter(messageAdapter2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //back to social fragment
        //backSocialFragmentIntent(backIcon);
    }

    private void loadDatabase(){

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //oppositeHeadingInChat.setText(FriendListAdapter.username);
                if(snapshot.child("name").getValue().equals(FriendListAdapter.username)){
                    oppositeHeadingInChat.setText(FriendListAdapter.username);
                }
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


//    //left side message
//    private void setUpConversationLeft(){
//        String [] messageList = getResources().getStringArray(R.array.messageLeftText);
//        String [] messageTime = getResources().getStringArray(R.array.messageTimeLeft);
//        for(int i = 0; i < messageList.length; i++){
//
//            conversationLeft.add(new ChatMessage(messageList[i], messageTime[i], recevier[i], "receiver"));
//
//        }
//    }
//
//    //right side message
    private void setUpConversationRight(){

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chatInputBar.length()==0){

                    Snackbar.make(chatSendButton,"input text could not be empty", Snackbar.LENGTH_SHORT).show();

                }else{
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    ChatMessage message = new ChatMessage(chatInputBar.getText().toString(),formatter.format(date),R.drawable.old_man,fuser.getUid());
                    Log.d("username: ", fuser.getUid());
                    allRef.child("chatMessage").push().setValue(message);
                    chatInputBar.clearFocus();
                    chatInputBar.setText("");
                    conversationRight.add(message);
                    MessageAdapter2 messageAdapter2 = new MessageAdapter2(view.getContext(), conversationRight);
                    recyclerView.setAdapter(messageAdapter2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    Snackbar.make(chatSendButton,"text has been sent it", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

//        String [] messageList = getResources().getStringArray(R.array.messageRightText);
//        String [] messageTime = getResources().getStringArray(R.array.messageTimeRight);
//        for(int i = 0; i < messageList.length; i++){
//
//            conversationRight.add(new ChatMessage(messageList[i], messageTime[i], sender[i], "sender"));
//
//        }
    }

}