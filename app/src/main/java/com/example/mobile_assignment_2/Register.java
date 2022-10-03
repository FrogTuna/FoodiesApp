package com.example.mobile_assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {

    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.registerButton);
        System.out.println(registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerIntent();
            }
        });

    }
    //link to login page
    public void registerIntent(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}