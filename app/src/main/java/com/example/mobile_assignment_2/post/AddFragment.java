package com.example.mobile_assignment_2.post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mobile_assignment_2.R;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText titleView;
    private TextInputEditText descripView;

    private Button postBtn;
    private Button imageBtn;
    LinearLayout linearLayout;



    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("Create Post");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createpost, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.imageLinearLayout);
        imageBtn = view.findViewById(R.id.imageButton);
        imageBtn.setOnClickListener(this);
        titleView = view.findViewById(R.id.create_post_title);
        descripView = view.findViewById(R.id.create_post_description);
        postBtn = view.findViewById(R.id.postButton);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                getContent.launch("image/*");
        }
    }

    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    Bitmap photoBitmap = null;
                    try {
                        photoBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageView = new ImageView(getContext());
                    if(imageView.getParent() != null) {
                        ((ViewGroup)imageView.getParent()).removeView(imageView); // <- fix
                    }
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            300,
                            RelativeLayout.LayoutParams.MATCH_PARENT
                    );
                    imageView.setLayoutParams(params);
                    imageView.setImageBitmap(photoBitmap);

                    linearLayout.addView(imageView);

                }
            });



}