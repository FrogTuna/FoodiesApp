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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList reqArrayList;

    public RequestFragment(ArrayList _reqArrayList) {
        // Required empty public constructor
        reqArrayList = _reqArrayList;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RequestFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RequestFragment newInstance(String param1, String param2) {
//        RequestFragment fragment = new RequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        // Implement following
        RequestListData[] requestListData = new RequestListData[reqArrayList.size()];
        for (int i = 0; i < reqArrayList.size(); i++) {
            requestListData[i] = new RequestListData(
                    ((HashMap<String, String>) reqArrayList.get(i)).get("requestUserID"),
                    ((HashMap<String, String>) reqArrayList.get(i)).get("name"),
                    ((HashMap<String, String>) reqArrayList.get(i)).get("comment"),
                    ((HashMap<String, String>) reqArrayList.get(i)).get("avatar")
                    //android.R.drawable.ic_dialog_email
            );
        }


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.requestRecyclerView);
        RequestListAdapter requestListAdapter = new RequestListAdapter(requestListData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(requestListAdapter);
        return view;


    }
}