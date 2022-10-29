package com.example.mobile_assignment_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobile_assignment_2.add.CustomItem;
import com.example.mobile_assignment_2.add.activities.addBySearch.addActivity;
import com.example.mobile_assignment_2.add.addFriendsAdapter;
import com.example.mobile_assignment_2.add.activities.nearByActivity;
import com.example.mobile_assignment_2.add.activities.shakeActivity;
import com.example.mobile_assignment_2.chat.ChatPagerAdapter;
import com.example.mobile_assignment_2.chat.firebaseDataStore.FriendshipInfo;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
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

    private Spinner customSpinner;
    private ArrayList<CustomItem> customList;

    private DatabaseReference chatRef, friendshipRef, reqRef, userRef;
    private ArrayList friendshipArrayList, chatArrayList, reqArrayList, userArrayList;

    private FirebaseAuth myAuth;
    private String userID = "";  // this var is login user, and should be pass when login to chat page
    private static final String TAG = "Child: ";
    public static  HashMap<String, String> outsideFriendInfo;


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
//        searchView = (SearchView) findViewById(R.id.searchView);
//        FirebaseUser fuser = myAuth.getCurrentUser();
        myAuth = FirebaseAuth.getInstance();
        userID = myAuth.getCurrentUser().getUid();

        friendshipArrayList = new ArrayList();
        userArrayList = new ArrayList();
        reqArrayList = new ArrayList();
        chatArrayList = new ArrayList();
        initialFireBase();

        /* Search view */
//        1. determine page
//        2. search based on different array
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(getActivity(), "No Match found",Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //    adapter.getFilter().filter(newText);
//                return false;
//            }
//        });





    }


    public ArrayList<CustomItem> getCustomList() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("null", R.drawable.add_user));
        customList.add(new CustomItem("add", R.drawable.ic_baseline_add_24));
        customList.add(new CustomItem("Shake", R.drawable.ic_baseline_screen_rotation_24));
        customList.add(new CustomItem("NearBy", R.drawable.ic_baseline_near_me_24));
        return customList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_social, container, false);


        TextView logo = view.findViewById(R.id.logoInSocial);

        customSpinner = view.findViewById(R.id.customIconSpinner);
        customList=getCustomList();
        addFriendsAdapter adapter = new addFriendsAdapter(this.getContext(),customList);
        if(customSpinner!=null) {
            customSpinner.setAdapter(adapter);
            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    CustomItem item = (CustomItem) adapterView.getSelectedItem();
                    Log.d(TAG, "selected:" + item.getSpinnerItemName());

                    if(item.getSpinnerItemName().equals("add")){
                        Intent intent = new Intent(getActivity(), addActivity.class);
                        startActivity(intent);
                    }else if(item.getSpinnerItemName().equals("Shake")){
                        Intent intent = new Intent(getActivity(), shakeActivity.class);
                        startActivity(intent);
                    }else if(item.getSpinnerItemName().equals("NearBy")) {
                        Intent intent = new Intent(getActivity(), nearByActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Log.d(TAG, "Non-selected:");
                }
            });
        }



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
        searchView = view.findViewById(R.id.searchView);
//        addFriendsBtn = view.findViewById(R.id.addFriendsButton);
//
//        addFriendsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Redirect to add friend page
//                Toast.makeText(view.getContext(),"Jump to add friend page: ",Toast.LENGTH_LONG).show();
//            }
//        });
        System.out.println("[Chat + ] " + chatArrayList);
        System.out.println("[User + ] " + userArrayList);
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
//        friendshipRef = firebaseDatabase.getReference("Friendship"); // get the location URL https://foodies-27bb7-default-rtdb.firebaseio.com
//        chatRef = firebaseDatabase.getReference("Chat");
//        reqRef = firebaseDatabase.getReference("Request");
        userRef = firebaseDatabase.getReference("Users");
//        friendshipRef.keepSynced(true);

//        chatRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {
//
//                    HashMap<String, String> chatInfo = new HashMap<String, String>();
//                    chatInfo.put("name", dataSnapshot.child("User2").getValue(String.class));
//                    chatInfo.put("content", dataSnapshot.child("Content").getValue(String.class));
//                    chatArrayList.add(chatInfo);
//
//                }
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
//        friendshipRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {
//                    friendshipArrayList.add(dataSnapshot.child("User2").getValue(String.class));
//                }
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


        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());


                HashMap<String, String> usersInfo = new HashMap<String, String>();
                usersInfo.put("ID", dataSnapshot.getKey());
                usersInfo.put("imageUrl", (String) dataSnapshot.child("imageUrl").getValue());
                usersInfo.put("username", (String) dataSnapshot.child("username").getValue());
//                usersInfo.put("remark", (String) dataSnapshot.child("remark").getValue());
                userArrayList.add(usersInfo);
                if(dataSnapshot.getKey().equals(userID)) {

                    for(DataSnapshot friendsSnapshot : dataSnapshot.child("friends").getChildren()) {
                        HashMap<String, String> friendsInfo = new HashMap<String, String>();
                        friendsInfo.put("ID", friendsSnapshot.getKey());
                        friendshipArrayList.add(friendsInfo);
                        HashMap<String, Object> chatsInfo = new HashMap<>();
                        String lastMsg = "";
                        chatsInfo.put("ID", friendsSnapshot.getKey());
                        for(DataSnapshot chatsSnapshot : friendsSnapshot.getChildren()) {
                            if(chatsSnapshot.getKey().equals("lastMessage")) {
                                for(DataSnapshot msgSnapshot : chatsSnapshot.getChildren()) {
                                    lastMsg = (String) msgSnapshot.getValue();
                                }
                            }

                        }
                        chatsInfo.put("lastMsg", lastMsg);
                        chatArrayList.add(chatsInfo);


                    }
                    System.out.println("[Chat - ] " + chatArrayList);

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
//        reqRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                if(dataSnapshot.child("User1").getValue().equals(loginUsername)) {
//
//                    HashMap<String, String> reqInfo = new HashMap<String, String>();
//                    reqInfo.put("name", dataSnapshot.child("User2").getValue(String.class));
//                    reqInfo.put("content", dataSnapshot.child("Content").getValue(String.class));
//                    reqArrayList.add(reqInfo);
//
//                }
//                chatPagerAdapter = new ChatPagerAdapter(
//                        getActivity(),
//                        NUM_PAGES,
//                        friendshipArrayList,
//                        userArrayList,
//                        reqArrayList,
//                        chatArrayList
//                );
//                viewPager2.setAdapter(chatPagerAdapter);
//                new TabLayoutMediator(tabLayout, viewPager2,
//                        new TabLayoutMediator.TabConfigurationStrategy() {
//                            @Override
//                            public void onConfigureTab(TabLayout.Tab tab, int position) {
//                                switch (position) {
//                                    case 0:
//                                        tab.setText("Chat");
//                                        break;
//                                    case 1:
//                                        tab.setText("Friend");
//                                        break;
//                                    case 2:
//                                        tab.setText("Request");
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        }).attach();
//
//            }
//
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
//
//        });

    }

    public void searchUsername() {

    }
}