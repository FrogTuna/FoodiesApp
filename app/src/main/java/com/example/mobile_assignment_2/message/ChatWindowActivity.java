package com.example.mobile_assignment_2.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.MainActivity;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.chat.FriendListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindowActivity extends AppCompatActivity {


    //fields
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
    DatabaseReference oppositeUserImageRef;
    DatabaseReference userLastMessageHasReadRef;
    DatabaseReference oppositeUserLastMessageHasReadRef;
    FirebaseAuth myAuth;
    ImageView chatSendButton;
    EditText chatInputBar;
    RecyclerView userRecyclerView;
    RecyclerView oppositeUserRecyclerView;
    CircleImageView userAvatar;
    CircleImageView oppositeUserAvatar;
    List<String> userChatIDList = new ArrayList<String>();
    List<String> oppositeUserChatIDList = new ArrayList<String>();
    String imageUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //firebase
        myAuth = FirebaseAuth.getInstance();
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("chats");
        //useHasRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child()
        oppositeUserRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("friends").child(fuser.getUid()).child("chats");
        userLastMessageRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("lastMessage");
        userLastMessageHasReadRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("friends").child(FriendListAdapter.userID).child("hasRead");
        oppositeUserLastMessageRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("friends").child(fuser.getUid()).child("lastMessage");
        oppositeUserLastMessageHasReadRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("friends").child(fuser.getUid()).child("hasRead");
        allRef = FirebaseDatabase.getInstance().getReference();
        chatMessageRef = FirebaseDatabase.getInstance().getReference("chatMessage");
        oppositeUserImageRef = FirebaseDatabase.getInstance().getReference("Users").child(FriendListAdapter.userID).child("imageUrl");


        //find id from xml
        backIcon = findViewById(R.id.chatWindowBackIcon);
        oppositeHeadingInChat = findViewById(R.id.oppositeHeadingInChat);
        oppositeHeadingInChat.setText(FriendListAdapter.username);
        userRecyclerView = findViewById(R.id.chatWindowRecycleView);
        oppositeUserRecyclerView = findViewById(R.id.chatWindowRecycleView);
        chatSendButton = findViewById(R.id.chatSendButton);
        chatInputBar = findViewById(R.id.chatInputBar);
        userAvatar = findViewById(R.id.chatWindowRightUserImage);
        oppositeUserAvatar = findViewById(R.id.chatWindowLeftUserImage);


        //load data first if there's existing message in firebase
        loadDatabase();


        //send button listener
        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //cannot be empty
                if (chatInputBar.length() == 0) {

                    Snackbar.make(chatSendButton, "input text could not be empty", Snackbar.LENGTH_SHORT).show();

                //send message to firebase and display on the chat window
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    DatabaseReference chatMessageRef = allRef.child("chatMessage").push();
                    String chatID = chatMessageRef.getKey();
                    ChatMessage message = new ChatMessage(chatInputBar.getText().toString(), formatter.format(date), fuser.getPhotoUrl().toString(), chatID, fuser.getUid());
                    chatMessageRef.setValue(message);
                    DatabaseReference fuserFriendChatRef = userRef.push();
                    DatabaseReference oppositeFuserChatRef = oppositeUserRef.push();
                    DatabaseReference userLastMessageRef2 = userLastMessageRef.push();
                    DatabaseReference oppositeUserLastMessageRef2 = oppositeUserLastMessageRef.push();
                    userLastMessageHasReadRef.setValue("true");
                    oppositeUserLastMessageHasReadRef.setValue("false");
                    fuserFriendChatRef.setValue(chatID);
                    oppositeFuserChatRef.setValue(chatID);
                    userLastMessageRef2.setValue(chatInputBar.getText().toString());
                    oppositeUserLastMessageRef2.setValue(chatInputBar.getText().toString());
                    chatInputBar.clearFocus();
                    chatInputBar.setText("");
                    Snackbar.make(chatSendButton, "text has been sent it", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        backSocialFragmentIntent(backIcon);

    }

    private void loadDatabase() {


        //opposite user in chat listener (firebase)
        oppositeUserImageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot imageSnapshot) {
                imageUrl = "";
                imageUrl = imageSnapshot.getValue().toString();

                //oppositeUser listener
                oppositeUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        oppositeUserChatIDList.clear();

                        //Log.d("chatIDList1", snapshot.getValue().toString());
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            oppositeUserChatIDList.add(snapshot1.getValue().toString());
                        }

                        chatMessageRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                oppositeUserconversation.clear();
                                for (DataSnapshot snapshot3 : snapshot2.getChildren()) {

                                    for (int i = 0; i < oppositeUserChatIDList.size(); i++) {
                                        if (snapshot3.getKey().matches(oppositeUserChatIDList.get(i))) {

                                            if(snapshot3.child("role").getValue().toString().equals(fuser.getUid())){
                                                //Log.d("i'm here1", snapshot3.child("senderImage").toString());
                                                snapshot3.child("senderImage").getRef().setValue(fuser.getPhotoUrl().toString());
                                            }
                                            else if(snapshot3.child("role").getValue().toString().equals(FriendListAdapter.userID)){
                                                //Log.d("i'm here2", snapshot3.child("senderImage").toString());
                                                snapshot3.child("senderImage").getRef().setValue(imageUrl);
                                            }
                                            String chatMessageRole = snapshot3.child("role").getValue().toString();
                                            String chatMessageText = snapshot3.child("senderText").getValue().toString();
                                            String chatMessageTime = snapshot3.child("senderTime").getValue().toString();
                                            String chatMessagechatID = snapshot3.child("chatID").getValue().toString();
                                            String chatMessageImage = snapshot3.child("senderImage").getValue().toString();
                                            ChatMessage message1 = new ChatMessage(chatMessageText, chatMessageTime, chatMessageImage, chatMessagechatID, chatMessageRole);
                                            oppositeUserconversation.add(message1);

                                        }
                                    }


                                    MessageAdapter2 messageAdapter2 = new MessageAdapter2(ChatWindowActivity.this, oppositeUserconversation);
                                    oppositeUserRecyclerView.setAdapter(messageAdapter2);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWindowActivity.this);
                                    if (oppositeUserconversation.size() > 6) {
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //current user in chat listener (firebase)
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userChatIDList.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    userChatIDList.add(snapshot1.getValue().toString());
                }

                chatMessageRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        userConversation.clear();
                        for (DataSnapshot snapshot3 : snapshot2.getChildren()) {

                            for (int i = 0; i < userChatIDList.size(); i++) {
                                if (snapshot3.getKey().matches(userChatIDList.get(i))) {
                                    String chatMessageRole = snapshot3.child("role").getValue().toString();
                                    String chatMessageText = snapshot3.child("senderText").getValue().toString();
                                    String chatMessageTime = snapshot3.child("senderTime").getValue().toString();
                                    String chatMessagechatID = snapshot3.child("chatID").getValue().toString();
                                    String chatMessageImage = snapshot3.child("senderImage").getValue().toString();
                                    ChatMessage message2 = new ChatMessage(chatMessageText, chatMessageTime, chatMessageImage, chatMessagechatID, chatMessageRole);
                                    userConversation.add(message2);
                                }
                            }


                            MessageAdapter2 messageAdapter2 = new MessageAdapter2(ChatWindowActivity.this, userConversation);
                            userRecyclerView.setAdapter(messageAdapter2);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWindowActivity.this);
                            if (userConversation.size() > 6) {
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


    //back to home page
    private void backSocialFragmentIntent(AppCompatImageView imageView) {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}