package com.example.mobile_assignment_2;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobile_assignment_2.chat.ChatActivity;
import com.example.mobile_assignment_2.chat.ChatListAdapter;
import com.example.mobile_assignment_2.chat.ChatListData;
import com.example.mobile_assignment_2.chat.ChatPagerAdapter;
import com.example.mobile_assignment_2.chat.firebaseDataStore.FriendshipInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialFragment#} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int NUM_PAGES = 3;
    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ChatPagerAdapter chatPagerAdapter;
    private SearchView searchView;
    private DatabaseReference chatRef, friendshipRef, reqRef, userRef;
    private ArrayList friendshipArrayList, chatArrayList, reqArrayList, userArrayList;

    private static final String TAG = "Child: ";
    private static final String loginUsername = "Wen";  // this var is login user, and should be pass when login to chat page

    public SocialFragment() {
        // Required empty public constructor


    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SocialFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SocialFragment newInstance(String param1, String param2) {
//        SocialFragment fragment = new SocialFragment();
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
        friendshipArrayList = new ArrayList();
        userArrayList = new ArrayList();
        reqArrayList = new ArrayList();
        chatArrayList = new ArrayList();
        initialFireBase();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_social, container, false);
        // Implement following


//        ChatListData[] chatListData = new ChatListData[chatArrayList.size()];
//
//        System.out.println("[+] chat list: " + chatArrayList);
//        for (int i = 0; i < chatArrayList.size(); i++) {
//            chatListData[i] = new ChatListData(
//                    ((HashMap<String, String>)chatArrayList.get(i)).get("name"),
//                    ((HashMap<String, String>)chatArrayList.get(i)).get("content"),
//                    android.R.drawable.ic_dialog_email
//            );
//        }
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
//        ChatListAdapter chatListAdapter = new ChatListAdapter(chatListData);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(chatListAdapter);
//        return view;
        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);

        return view;
    }

//    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.getActivity().onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }
    private void initialFireBase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
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
                        getActivity(),
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