package com.example.mobile_assignment_2.community;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddCommunity extends AppCompatActivity {
    private TextInputEditText comNameView;
    private TextInputEditText comDescriptionView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_community);
        Intent intent = getIntent();


        comNameView = findViewById(R.id.community_name);
        comDescriptionView = findViewById(R.id.com_desc);
        String name = comNameView.getText().toString();
        String descrip = comDescriptionView.getText().toString();
        Log.i("hello", name);
        Log.i("hello", descrip);

    }

}
