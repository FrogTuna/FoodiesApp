package com.example.mobile_assignment_2.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_assignment_2.R;

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

    public FriendFragment() {
        // Required empty public constructor
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
        FriendListData[] friendListData = new FriendListData[] { // Test Data
                new FriendListData("user_1", "I am user 1", android.R.drawable.ic_dialog_email),
                new FriendListData("user_2", "I am user 2", android.R.drawable.ic_dialog_info),
                new FriendListData("user_3", "I am user 3", android.R.drawable.ic_delete),
                new FriendListData("user_4", "I am user 4", android.R.drawable.ic_dialog_dialer),
                new FriendListData("user_5", "I am user 5", android.R.drawable.ic_dialog_alert),
                new FriendListData("user_6", "I am user 6", android.R.drawable.ic_dialog_map),
                new FriendListData("user_7", "I am user 7", android.R.drawable.ic_dialog_email),
                new FriendListData("user_8", "I am user 8", android.R.drawable.ic_dialog_info),
                new FriendListData("user_9", "I am user 9", android.R.drawable.ic_delete),
                new FriendListData("user_10", "I am user 10", android.R.drawable.ic_dialog_dialer),
                new FriendListData("user_11", "I am user 11", android.R.drawable.ic_dialog_alert),
                new FriendListData("user_12", "I am user 12", android.R.drawable.ic_dialog_map),
        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friendRecyclerView);
        FriendListAdapter friendListAdapter = new FriendListAdapter(friendListData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(friendListAdapter);
        return view;
    }
}