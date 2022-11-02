package com.example.mobile_assignment_2.authentication;

public class User {

    //fields
    public String userID, username, email, password, imageUrl;

    //constructor
    public User(String userID, String username, String email, String password, String imageUrl) {

        this.userID = userID;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;

    }


    //getter
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}