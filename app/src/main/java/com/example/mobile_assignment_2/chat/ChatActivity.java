package com.example.mobile_assignment_2.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.chat.firebaseDataStore.FriendshipInfo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class ChatActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;
    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ChatPagerAdapter chatPagerAdapter;
    private SearchView searchView;
    private DatabaseReference chatRef, friendshipRef, reqRef, userRef;
    private ArrayList friendshipArrayList, chatArrayList, reqArrayList, userArrayList;

    private static final String TAG = "Child: ";
    private static final String loginUsername = "Wen";  // this var is login user, and should be pass when login to chat page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        friendshipArrayList = new ArrayList();
        userArrayList = new ArrayList();
        reqArrayList = new ArrayList();
        chatArrayList = new ArrayList();
        initialFireBase();

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
//        searchView = findViewById(R.id.searchView);

    }
    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }
    private void initialFireBase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        friendshipRef = firebaseDatabase.getReference("Friendship"); // get the location URL https://foodies-27bb7-default-rtdb.firebaseio.com
        chatRef = firebaseDatabase.getReference("Chat");
        reqRef = firebaseDatabase.getReference("Request");
        userRef = firebaseDatabase.getReference("User");
        friendshipRef.keepSynced(true);

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {

                    HashMap<String, String> chatInfo = new HashMap<String, String>();
                    chatInfo.put("name", dataSnapshot.child("User2").getValue(String.class));
                    chatInfo.put("content", dataSnapshot.child("Content").getValue(String.class));
                    chatArrayList.add(chatInfo);

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }

        });
        friendshipRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {
                    friendshipArrayList.add(dataSnapshot.child("User2").getValue(String.class));
                }
//                System.out.println("[+] Friend: " + friendshipArrayList);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }

        });


        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                HashMap<String, String> friendInfo = new HashMap<String, String>();
                friendInfo.put("name", dataSnapshot.child("Name").getValue(String.class));
                friendInfo.put("remark", dataSnapshot.child("Remark").getValue(String.class));
                userArrayList.add(friendInfo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        });
        reqRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {

                    HashMap<String, String> reqInfo = new HashMap<String, String>();
                    reqInfo.put("name", dataSnapshot.child("User2").getValue(String.class));
                    reqInfo.put("content", dataSnapshot.child("Content").getValue(String.class));
                    reqArrayList.add(reqInfo);

                }
                chatPagerAdapter = new ChatPagerAdapter(
                    ChatActivity.this,
                    NUM_PAGES,
                    friendshipArrayList,
                    userArrayList,
                    reqArrayList,
                    chatArrayList
                );
                viewPager2.setAdapter(chatPagerAdapter);
                new TabLayoutMediator(tabLayout, viewPager2,
                        new TabLayoutMediator.TabConfigurationStrategy() {
                            @Override
                            public void onConfigureTab(TabLayout.Tab tab, int position) {
                                switch (position) {
                                    case 0:
                                        tab.setText("Chat");
                                        break;
                                    case 1:
                                        tab.setText("Friend");
                                        break;
                                    case 2:
                                        tab.setText("Request");
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).attach();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
                String friendshipKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }


        });

    }

    public void searchUsername() {

    }



}