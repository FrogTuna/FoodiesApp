package com.example.mobile_assignment_2.post;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.mobile_assignment_2.Comment;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.Post;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author: Weiran Zou
 * @description: Create Post screen to allow user to create and upload a post with multiple images,
 * title and description. The user can pick an image from gallery and take a photo.
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
    private Button cameraBtn;
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
        cameraBtn = view.findViewById(R.id.cameraButton);

        cameraBtn.setOnClickListener(this);
        postBtn = view.findViewById(R.id.postButton);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleView.getText().toString();
                String descrip = descripView.getText().toString();
                ArrayList<String> downloadimageUrls = new ArrayList<>();
                if (pickedImageUris.size() == 0) {
                    Toast.makeText(getContext(), "Please add an image", Toast.LENGTH_SHORT).show();
                } else if (title.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
                } else if (descrip.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                } else {
                    // Showing progress dialog when uploading post
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading post...");
                    progressDialog.show();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    // upload all picked images and download their Url, then upload post with post infor and these image Urls
                    for (Uri imageUri : pickedImageUris) {
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
                                                        } else {
                                                            String author = task.getResult().getValue(String.class);

                                                            DatabaseReference databaseReference = firebaseDatabase.getReference("Posts").push();
                                                            String pid = databaseReference.getKey();
                                                            Post post = new Post(title, descrip, author, currentUser.getUid(), downloadimageUrls, pid, 0, 0, 0, new HashMap<String, Comment>());
                                                            // add post data to firebase database
                                                            databaseReference.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(getContext(), "Post Added successfully", Toast.LENGTH_LONG).show();
                                                                    // clear post
                                                                    titleView.setText("");
                                                                    descripView.setText("");
                                                                    linearLayout.removeAllViews();
                                                                    pickedImageUris.clear();
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


            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // handle click event on image button
            case R.id.imageButton:
                galleryActivityResultLauncher.launch("image/*");
                break;
            // handle click event on camera button
            case R.id.cameraButton:
                if (checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request permission from user
                    requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA);
                } else { // permission is already granted
                    openCamera();
                }

        }
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<String> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        // Handle the returned Uri
                        Bitmap photoBitmap = null;
                        try {
                            photoBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView imageView = new ImageView(getContext());
                        if (imageView.getParent() != null) {
                            ((ViewGroup) imageView.getParent()).removeView(imageView);
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                500,
                                RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(photoBitmap);
                        linearLayout.addView(imageView);
                        pickedImageUris.add(uri);
                    }

                }
            });


    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ImageView imageView = new ImageView(getContext());
                        if (imageView.getParent() != null) {
                            ((ViewGroup) imageView.getParent()).removeView(imageView);
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                500,
                                RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(bitmap);

                        linearLayout.addView(imageView);
                        String imagePath = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "camera", null);
                        Uri imageUri = Uri.parse(imagePath);
                        pickedImageUris.add(imageUri);

                    }
                }
            });

    //Handle the user's selection result from the dialog of system permissions
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });
}