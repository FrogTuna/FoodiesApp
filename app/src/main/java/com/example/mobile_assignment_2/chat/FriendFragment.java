package com.example.mobile_assignment_2.chat;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mobile_assignment_2.R;
import java.util.ArrayList;
import java.util.HashMap;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FriendFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList friendArrayList, userArrayList;
    public FriendFragment(ArrayList _friendArrayList, ArrayList _userArrayList) {
        // Required empty public constructor
        friendArrayList = _friendArrayList;
        userArrayList = _userArrayList;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FriendFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FriendFragment newInstance(String param1, String param2) {
//        FriendFragment fragment = new FriendFragment();
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
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_friend, container, false);
        // Implement following
//        System.out.println("[+] Friend check: " + friendArrayList);
        FriendListData[] friendListData = new FriendListData[friendArrayList.size()];


        for (int i = 0; i < friendArrayList.size(); i++) {
            for (int j = 0; j < userArrayList.size(); j++) {
                if(((HashMap<String, String>)friendArrayList.get(i)).get("ID").equals(((HashMap<String, String>)userArrayList.get(j)).get("ID"))) {
                    friendListData[i] = new FriendListData(((HashMap<String, String>)friendArrayList.get(i)).get("ID"), ((HashMap<String, String>)userArrayList.get(j)).get("username"), ((HashMap<String, String>)userArrayList.get(j)).get("remark"), ((HashMap<String, String>)userArrayList.get(j)).get("imageUrl"));
                }
            }
//            friendListData[i] = new FriendListData((String)friendArrayList.get(i), "I am user 1", android.R.drawable.ic_dialog_email);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friendRecyclerView);
        FriendListAdapter friendListAdapter = new FriendListAdapter(friendListData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(friendListAdapter);
        return view;
    }
}