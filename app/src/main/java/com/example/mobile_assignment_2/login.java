package com.example.mobile_assignment_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText email;
    EditText password;
    Button loginBtn;
    TextView registerInLogin;
    FirebaseAuth myAuth;

    private DatabaseReference myReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);
        registerInLogin = findViewById(R.id.registerInLogin);
        myAuth = FirebaseAuth.getInstance();
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





    private void loginUser(){

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();


        if(TextUtils.isEmpty(emailString)){

            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(passwordString)){

            password.setError("Password cannot be empty");
            password.requestFocus();
        }
        else{
                myAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = myAuth.getCurrentUser();
                            if(user.isEmailVerified()){
                                Snackbar.make(loginBtn,"User login successfully", Snackbar.LENGTH_SHORT).show();
                                mainIntent();
                            }
                            else{
                                Snackbar.make(loginBtn,"Email address has not been verified", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Snackbar.make(loginBtn,"Login error occurs: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }





    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.loginButton:
                loginUser();
                //mainIntent();
                break;
            case R.id.registerInLogin:
                loginIntent();
                break;
        }

    }
}