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

/**
 * @author: Yao-Wen Chang
 * @description: This file provides the interface for users to check the existing chat history and act
 * as a container for the each chat record
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList chatArrayList, userArrayList;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("Chat");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        // Implement following


        ChatListData[] chatListData = new ChatListData[chatArrayList.size()];

        System.out.println("[Chat] " + chatArrayList);
        System.out.println("[User] " + userArrayList);

        for (int i = 0; i < chatArrayList.size(); i++) {
            for (int j = 0; j < userArrayList.size(); j++) {
                if (((HashMap<String, String>) chatArrayList.get(i)).get("ID").equals(((HashMap<String, String>) userArrayList.get(j)).get("ID"))) {
                    System.out.println("[Check] ");
                    //Log.d("new message is coming", ((HashMap<String, String>)userArrayList.get(j)).get("hasRead"));
                    chatListData[i] = new ChatListData(
                        ((HashMap<String, String>)chatArrayList.get(i)).get("ID"),
                        ((HashMap<String, String>)userArrayList.get(j)).get("username"),
                        ((HashMap<String, String>)chatArrayList.get(i)).get("lastMsg"),
                        ((HashMap<String, String>)userArrayList.get(j)).get("imageUrl"),
                            ((HashMap<String, String>)chatArrayList.get(i)).get("hasRead")
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
}