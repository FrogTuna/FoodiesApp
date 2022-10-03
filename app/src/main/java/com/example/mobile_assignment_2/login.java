package com.example.mobile_assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView username;
    TextView password;
    Button loginBtn;
    TextView registerInLogin;

    private DatabaseReference myReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginBtn = findViewById(R.id.loginButton);
        registerInLogin = findViewById(R.id.registerInLogin);

        loginBtn.setOnClickListener(this);
        registerInLogin.setOnClickListener(this);
    }

    //link to register page
    public void loginIntent(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void mainIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void login(){

        username =(TextView) findViewById(R.id.loginUsername);
        password =(TextView) findViewById(R.id.loginPassword);
        loginBtn =(Button) findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output = username.getText().toString();
                myReference.setValue(output);
            };
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.loginButton:
                login();
                //mainIntent();
                break;
            case R.id.registerInLogin:
                loginIntent();
                break;
        }

    }
}