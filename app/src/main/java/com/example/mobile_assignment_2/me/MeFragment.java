package com.example.mobile_assignment_2.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.authentication.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    ImageView editHeadPortrait;
    DatabaseReference userRef;
    FirebaseUser fuser;
    View view;


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
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_me, container, false);
        signOutBtn = (Button) view.findViewById(R.id.SignOut);
        editHeadPortrait = (ImageView) view.findViewById(R.id.headPortrait);
        loadDatabase(view);
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
        editHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();

            }
        });


        return view;
    }

    private void imageChooser() {




    }

    public void fromMePageToMyPostsPageIntent(View view){
        Intent intent = new Intent(getActivity(), MyPostsActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {

        Intent intent = new Intent(getActivity(), login.class);
        startActivity(intent);


    }


    public void loadDatabase(View view){

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(snapshot.child("userID").getValue().equals(fuser.getUid())){
                    TextView username = (TextView) view.findViewById(R.id.profileName);
                    username.setText(snapshot.child("name").getValue().toString());
                };
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}