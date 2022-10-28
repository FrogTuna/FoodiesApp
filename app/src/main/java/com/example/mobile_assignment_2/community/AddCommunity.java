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
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.R;
import com.google.android.material.textfield.TextInputEditText;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import java.io.IOException;
import java.util.ArrayList;

public class AddCommunity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private TextInputEditText comNameView;
    private TextInputEditText comDescriptionView;
    private Spinner comType;
    String[] comType_list = { "Asian food", "American food", "Italy food", "Greece food", "Mexican food"};
    private Button imageBtn;
    private Button cameraBtn;
    private Button postBtn;
    LinearLayout linearLayout;
    ArrayList<Uri> pickedImageUris = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_community);
        comType = findViewById(R.id.com_type);
        comType.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comType_list);

        // set simple layout resource file for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the Spinner which binds data to spinner
        comType.setAdapter(ad);

        Intent intent = getIntent();
        comNameView = findViewById(R.id.community_name);
        comDescriptionView = findViewById(R.id.com_desc);
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
