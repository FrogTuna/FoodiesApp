package com.example.mobile_assignment_2.me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.authentication.login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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
    ImageButton collectBtn;
    ImageButton postsBtn;
    ImageButton eventBtn;
    ImageView editHeadPortrait;
    TextView username;
    DatabaseReference userRef;
    DatabaseReference userImageRef;
    FirebaseUser fuser;
    View view;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri selectedImage;
    Bitmap bitmap;
    private static final int GALLERY_REQUEST = 1;


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
        getActivity().setTitle("Me");
        myAuth = FirebaseAuth.getInstance();
        fuser = myAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        userImageRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("imageUrl");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_me, container, false);

        username = (TextView) view.findViewById(R.id.profileName);
        //username.setText(fuser.getDisplayName());
        editHeadPortrait = (ImageView) view.findViewById(R.id.headPortrait);
        postsBtn = (ImageButton) view.findViewById(R.id.postButtonProfile);
        signOutBtn = (Button) view.findViewById(R.id.SignOut);
        collectBtn = (ImageButton) view.findViewById(R.id.starButtonProfile);
        eventBtn = (ImageButton) view.findViewById(R.id.eventButtonProfile);
        username.setText(fuser.getDisplayName());
        Log.d("username", fuser.getDisplayName());
        Picasso.with(view.getContext()).load(fuser.getPhotoUrl().toString()).into(editHeadPortrait);

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
                Intent i = new Intent(getActivity(), MyPostsActivity.class);
                startActivity(i);
            }
        });

        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CollectPostsActivity.class);
                startActivity(i);
            }
        });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MyEventsActivity.class);
                startActivity(i);
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

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_REQUEST);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data.getData() != null) {
            selectedImage = data.getData();
            Log.d("URI", selectedImage.toString());
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), selectedImage);
                editHeadPortrait.setImageBitmap(bitmap);
                if (selectedImage != null) {

                    // Code for showing progressDialog while uploading
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    // Defining the child of storageReference
                    StorageReference ref = storageReference.child("userImages/" + UUID.randomUUID().toString());

                    // adding listeners on upload
                    // or failure of image
                    ref.putFile(selectedImage)
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            // Image uploaded successfully
                                            // Dismiss dialog
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    userImageRef.setValue(uri.toString());
                                                    //username.setText(snapshot.child("name").getValue().toString());
                                                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(uri.toString())).build();
                                                    fuser.updateProfile(userProfileChangeRequest);
                                                    Picasso.with(view.getContext()).load(uri.toString()).into(editHeadPortrait);
                                                }
                                            });
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                        }
                                    });
                }
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }


    }


    public void fromMePageToMyPostsPageIntent(View view) {
        Intent intent = new Intent(getActivity(), MyPostsActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {

        Intent intent = new Intent(getActivity(), login.class);
        startActivity(intent);


    }


}