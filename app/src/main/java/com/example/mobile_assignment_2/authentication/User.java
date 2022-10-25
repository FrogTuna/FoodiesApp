package com.example.mobile_assignment_2.authentication;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String userID, username, email, password, imageUrl;
    FirebaseDatabase firebaseDatabase;

    public User(String userID, String username, String email, String password, String imageUrl){

        this.userID = userID;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;

    }


    public String getUserID(){
        return  userID;
    }

    public String getName(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getImageUrl(){
        return imageUrl;
    }


}