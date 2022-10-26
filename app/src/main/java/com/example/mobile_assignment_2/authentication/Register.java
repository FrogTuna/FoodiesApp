package com.example.mobile_assignment_2.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    EditText username;
    EditText password;
    EditText email;
    TextView back;
    Button registerBtn;
    FirebaseAuth myAuth;
    DatabaseReference myReference;
    String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.registerUsername);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerButton);
        back = findViewById(R.id.registerBack);
        myReference = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                createUser();
                //registerIntent();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

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


    //register a user
    private void createUser() {


        String usernameString = username.getText().toString();
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();


        if(TextUtils.isEmpty(usernameString)){

            username.setError("username cannot be empty");
            username.requestFocus();

        }
        else if(TextUtils.isEmpty(emailString)){

            email.setError("Email cannot be empty");
            email.requestFocus();

        }
        else if(TextUtils.isEmpty(passwordString)){

            password.setError("Password cannot be empty");
            password.requestFocus();

        }else{

            myAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        FirebaseUser fuser = myAuth.getCurrentUser();
                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(registerBtn,"Verification Email has been sent", Snackbar.LENGTH_SHORT).show();
//                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username.toString()).build();
//                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                                firebaseUser.updateProfile(userProfileChangeRequest);

                                User user = new User(fuser.getUid(), usernameString, emailString, passwordString, imageUrl = "");
                                myReference.child("Users").child(fuser.getUid()).setValue(user);
//                                DatabaseReference friendRef = myReference.child("users").child(fuser.getUid()).child("friendList").push();
                                registerIntent();
                                finish();
                            }
                        }). addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("OnFailure: Email not sent" + e.getMessage());
                            }
                        });
                    }else{
                        Snackbar.make(registerBtn,"failed to create an account: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}