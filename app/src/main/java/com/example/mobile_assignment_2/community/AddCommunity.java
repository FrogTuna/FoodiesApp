package com.example.mobile_assignment_2.community;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
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

import static androidx.core.content.ContextCompat.checkSelfPermission;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;

public class AddCommunity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextInputEditText comNameView;
    private TextInputEditText comDescriptionView;
    private Spinner comType;
    String[] comType_list = { "Asian food", "American food", "Italy food", "Greece food", "Mexican food"};
    private Button imageBtn;
    private Button cameraBtn;
    private Button postBtn;
    LinearLayout linearLayout;
    DatabaseReference communityRef;
    FirebaseAuth communityAuth;
    FirebaseUser fuser;
    ArrayList<Uri> pickedImageUris = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_community);
        comType = findViewById(R.id.com_type);
        comType.setOnItemSelectedListener(this);

        // connect to firebase
        communityAuth = FirebaseAuth.getInstance();
        fuser = communityAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        communityRef = firebaseDatabase.getReference("Community").push();

        // Create the instance of ArrayAdapter
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comType_list);

        // set simple layout resource file for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the Spinner which binds data to spinner
        comType.setAdapter(ad);

        Intent intent = getIntent();
        comNameView = findViewById(R.id.community_name);
        comDescriptionView = findViewById(R.id.com_desc);

        // get comName and comDescription
        String name = comNameView.getText().toString();
        String descrip = comDescriptionView.getText().toString();
        Log.i("hello", name);
        Log.i("hello", descrip);
        linearLayout = (LinearLayout) findViewById(R.id.imageLinearLayout);
        imageBtn = findViewById(R.id.choose_image);
        imageBtn.setOnClickListener(this);

        postBtn = findViewById(R.id.post_community);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commType = comType.getSelectedItem().toString();
                String comName = comNameView.getText().toString();
                String comDes = comDescriptionView.getText().toString();
                ArrayList<String> downloadImgUrls = new ArrayList<>();
                String cid = communityRef.getKey();

                communityRef.setValue(new Communitypost());

                // Get users information
                DatabaseReference usersRef = firebaseDatabase.getReference("Users");
                communityRef.setValue(new Communitypost());

                usersRef.child(fuser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error in fetching data", task.getException());
                        } else {
                            String auName = task.getResult().getValue(String.class);
                            String uid = fuser.getUid();
                            Communitypost comPost = new Communitypost(cid, uid, comName, downloadImgUrls, commType, auName, comDes);
                            communityRef.setValue(comPost);
                        }
                    }
                });
//                startActivity(new Intent(AddCommunity.this, CommunityFragment.class));
            }
        });
    }
//        imageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("choose image", "aaaaaaaaaaaaaaaaaaaa");
//                galleryActivityResultLauncher.launch("image/*");
//
//            }
//        });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                galleryActivityResultLauncher.launch("image/*");
                break;
//            case R.id.cameraButton:
//                if (checkSelfPermission(getContext(), Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    // Request permission from user
//                    requestPermissionLauncher.launch(
//                            Manifest.permission.CAMERA);
//                } else { // permission is already granted
//                    openCamera();
//                }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                        comType_list[position],
                        Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    ActivityResultLauncher<String> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    Bitmap photoBitmap = null;
                    try {
                        photoBitmap = MediaStore.Images.Media.getBitmap(AddCommunity.this.getContentResolver(),uri);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageView = new ImageView(AddCommunity.this);
                    if(imageView.getParent() != null) {
                        ((ViewGroup)imageView.getParent()).removeView(imageView);
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
            });

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                        ImageView imageView = new ImageView(AddCommunity.this);
                        if(imageView.getParent() != null) {
                            ((ViewGroup)imageView.getParent()).removeView(imageView);
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                500,
                                RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(bitmap);

                        linearLayout.addView(imageView);

                    }
                }
            });

    //Handle the user's selection result from the dialog of system permissions
//    private ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    openCamera();
//                } else {
//                    Toast.makeText(AddCommunity.this, "Permission denied", Toast.LENGTH_SHORT).show();
//                }
//            });

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.choose_image:
//                Log.i("choose image", "aaaaaaaaaaaaaaaaaaaa");
//                galleryActivityResultLauncher.launch("image/*");
//                break;
//            case R.id.use_camera:
//
//                    openCamera();
//
//
//        }
//    }
}
