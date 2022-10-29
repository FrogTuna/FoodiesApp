package com.example.mobile_assignment_2.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.chat.firebaseDataStore.FriendshipInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ChatFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList chatArrayList, userArrayList;
//    private ArrayList friendshipArrayList, chatHistArrayList, reqArrayList, userArrayList;
//    private static final String TAG = "Child: ";
//    private static final String loginUsername = "Wen";  // this var is login user, and should be pass when login to chat page
//    private DatabaseReference chatRef, friendshipRef, reqRef, userRef;
//    private CountDownLatch done = new CountDownLatch(1);



    public ChatFragment(ArrayList _chatArrayList, ArrayList _userArrayList) {
        // Required empty public constructor
        chatArrayList = _chatArrayList;
        userArrayList = _userArrayList;

    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ChatFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ChatFragment newInstance(String param1, String param2) {
//        ChatFragment fragment = new ChatFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view  = inflater.inflate(R.layout.fragment_chat, container, false);
        // Implement following


        ChatListData[] chatListData = new ChatListData[chatArrayList.size()];

        System.out.println("[Chat] " + chatArrayList);
        System.out.println("[User] " + userArrayList);

        for (int i = 0; i < chatArrayList.size(); i++) {
            for(int j = 0; j < userArrayList.size(); j++) {
                if(((HashMap<String, String>)chatArrayList.get(i)).get("ID").equals(((HashMap<String, String>)userArrayList.get(j)).get("ID"))) {
                    System.out.println("[Check] ");

                    chatListData[i] = new ChatListData(
                        ((HashMap<String, String>)chatArrayList.get(i)).get("ID"),
                        ((HashMap<String, String>)userArrayList.get(j)).get("username"),
                        ((HashMap<String, String>)chatArrayList.get(i)).get("lastMsg"),
                        ((HashMap<String, String>)userArrayList.get(j)).get("imageUrl")
                    );
                }
            }

        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
        ChatListAdapter chatListAdapter = new ChatListAdapter(chatListData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatListAdapter);
        return view;

    }



//    private void initialFireBase(){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        friendshipRef = firebaseDatabase.getReference("Friendship"); // get the location URL https://foodies-27bb7-default-rtdb.firebaseio.com
//        chatRef = firebaseDatabase.getReference("Chat");
//        reqRef = firebaseDatabase.getReference("Request");
//        userRef = firebaseDatabase.getReference("User");
//
//
//
//        friendshipRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {
//                    friendshipArrayList.add(dataSnapshot.child("User2").getValue(String.class));
//                }
//                done.countDown();
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//            }
//
//        });
//
//
//
//        userRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                HashMap<String, String> friendInfo = new HashMap<String, String>();
//                friendInfo.put("name", dataSnapshot.child("Name").getValue(String.class));
//                friendInfo.put("remark", dataSnapshot.child("Remark").getValue(String.class));
//                userArrayList.add(friendInfo);
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                FriendshipInfo newFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                FriendshipInfo movedFriendshipInfo = dataSnapshot.getValue(FriendshipInfo.class);
//                String friendshipKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//            }
//        });
//
//    }
}