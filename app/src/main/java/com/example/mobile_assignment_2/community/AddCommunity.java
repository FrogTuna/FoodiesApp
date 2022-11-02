package com.example.mobile_assignment_2.community;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOException;
import java.util.ArrayList;

/***
 *
 *
 */
public class AddCommunity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextInputEditText comNameView;
    private TextInputEditText comDescriptionView;
    private Spinner comType;
    String[] comType_list = {"Asian food", "American food", "Italy food", "Greece food", "Mexican food"};
    private Button imageBtn;
    private Button postBtn;
    LinearLayout linearLayout;
    DatabaseReference communityRef;
    FirebaseAuth communityAuth;
    FirebaseUser fuser;
    Uri pickedImageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_community);
        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get CommunityType spinner
        comType = findViewById(R.id.com_type);
        comType.setOnItemSelectedListener(this);

        // connect to firebase
        communityAuth = FirebaseAuth.getInstance();
        fuser = communityAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        communityRef = firebaseDatabase.getReference("Community").push();

        // Create the instance of ArrayAdapter
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comType_list);
        // ArrayAdapter ad = ArrayAdapter.createFromResource(this, R.array.comTypeLists, android.R.layout.simple_spinner_item);

        // set simple layout resource file for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the Spinner which binds data to spinner
        comType.setAdapter(ad);
        comType.setOnItemSelectedListener(this);

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
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("choose image", "aaaaaaaaaaaaaaaaaaaa");
                galleryActivityResultLauncher.launch("image/*");

            }
        });

        postBtn = findViewById(R.id.post_community);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commType = comType.getSelectedItem().toString();
                String comName = comNameView.getText().toString();
                String comDes = comDescriptionView.getText().toString();
                ArrayList<String> downloadImgUrls = new ArrayList<>();
                String cid = communityRef.getKey();
                Uri downloadimageUrl = null;

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference postImagesRef = storageRef.child("communityImages/" + pickedImageUri.getLastPathSegment());
                // upload picked images
                UploadTask uploadTask = postImagesRef.putFile(pickedImageUri);
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

                                DatabaseReference usersRef = firebaseDatabase.getReference("Users");
                                communityRef.setValue(new Communitypost());
                                Uri downloadUri = task.getResult();

                                usersRef.child(fuser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error in fetching data", task.getException());
                                        } else {
                                            Log.e("firebase", "Success in fetching data", task.getException());
                                            String auName = task.getResult().getValue(String.class);
                                            String uid = fuser.getUid();
                                            Communitypost comPost = new Communitypost(cid, uid, comName, downloadUri.toString(), commType, auName, comDes);
                                            communityRef.setValue(comPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(AddCommunity.this, "Community Added successfully", Toast.LENGTH_LONG).show();
                                                    // clear post
                                                    comNameView.setText("");
                                                    comDescriptionView.setText("");
                                                    linearLayout.removeAllViews();
                                                }
                                            });
                                            AddCommunity.this.finish();
                                        }
                                    }
                                });

                            }
                        });
                    }

                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                galleryActivityResultLauncher.launch("image/*");
                break;
        }
    }


    private void alertMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCommunity.this);

        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), comType_list[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    ActivityResultLauncher<String> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            // Handle the returned Uri
            Bitmap photoBitmap = null;
            try {
                photoBitmap = MediaStore.Images.Media.getBitmap(AddCommunity.this.getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(AddCommunity.this);
            if (imageView.getParent() != null) {
                ((ViewGroup) imageView.getParent()).removeView(imageView);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500, RelativeLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(photoBitmap);

            linearLayout.addView(imageView);

            pickedImageUri = uri;
        }
    });

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
