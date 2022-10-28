package com.example.mobile_assignment_2.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddCommunity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private TextInputEditText comNameView;
    private TextInputEditText comDescriptionView;
    private Spinner comType;
    String[] comType_list = { "Asian food", "American food", "Italy food", "Greece food", "Mexican food"};

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
}
