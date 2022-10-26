package com.example.mobile_assignment_2.post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.authentication.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

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
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    ArrayList<Uri> pickedImageUris = new ArrayList<>();

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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        imageBtn = view.findViewById(R.id.imageButton);
        imageBtn.setOnClickListener(this);
        titleView = view.findViewById(R.id.create_post_title);
        descripView = view.findViewById(R.id.create_post_description);
        postBtn = view.findViewById(R.id.postButton);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleView.getText().toString();
                String descrip = descripView.getText().toString();
                ArrayList<String> downloadimageUrls = new ArrayList<>();

                if (!title.isEmpty() && !descrip.isEmpty()) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    // upload all picked images and download their Url, then upload post with post infor and these image Urls
                    for (Uri imageUri: pickedImageUris) {
                        StorageReference postImagesRef = storageRef.child("postImages/" + imageUri.getLastPathSegment());
                        // upload picked images
                        UploadTask uploadTask = postImagesRef.putFile(imageUri);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }

                                        // retrieve the download URL of uploaded image
                                        return postImagesRef.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            // get downloaded URL of uploaded image
                                            Uri downloadUri = task.getResult();
                                            downloadimageUrls.add(downloadUri.toString());
                                            if (downloadimageUrls.size() == pickedImageUris.size()) {

                                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                // Get a reference to users
                                                DatabaseReference usersRef = firebaseDatabase.getReference("Users");

                                                usersRef.child(currentUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        if (!task.isSuccessful()) {
                                                            Log.e("firebase", "Error in fetching data", task.getException());
                                                        }
                                                        else {
                                                            String author = task.getResult().getValue(String.class);
                                                            Post post = new Post(title, descrip, author, currentUser.getUid(), downloadimageUrls);
                                                            DatabaseReference databaseReference = firebaseDatabase.getReference("Posts").push();

                                                            // add post data to firebase database
                                                            databaseReference.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(getContext(), "Post Added successfully",Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                            }
                                        } else {

                                        }
                                    }
                                });
                            }

                        });
                    }
                }




                }});
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

                    pickedImageUris.add(uri);
                }
            });



}