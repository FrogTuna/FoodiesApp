package com.example.mobile_assignment_2.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.authentication.login;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */




public class MeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Fields
    private String mParam1;
    private String mParam2;
    FirebaseAuth myAuth;
    Button signOutBtn;
    ImageButton postsBtn;
    ImageView editProfileBtn;


    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_me, container, false);
        signOutBtn = (Button) view.findViewById(R.id.SignOut);
        editProfileBtn = (ImageView) view.findViewById(R.id.headPortrait);
        postsBtn = (ImageButton) view.findViewById(R.id.postButtonProfile);


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAuth.signOut();
                signOut(view);
            }
        });
        postsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromMePageToMyPostsPageIntent(view);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fromMePageToEditProfilePageIntent(view);
            }
        });

        return view;
    }

    public void fromMePageToMyPostsPageIntent(View view){
        Intent intent = new Intent(getActivity(), MyPostsActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {

        Intent intent = new Intent(getActivity(), login.class);
        startActivity(intent);


    }

    public void fromMePageToEditProfilePageIntent(View v){

        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);

    }
}