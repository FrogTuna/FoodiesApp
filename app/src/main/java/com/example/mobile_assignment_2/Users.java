package com.example.mobile_assignment_2;

import java.util.ArrayList;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class Users {
    private String email, imageUrl, name, password, remark, userID, username;
    private ArrayList<String> friends;

    public Users(String email, String imageUrl, String name, String password, String remark, String userID, String username, ArrayList<String> friends) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.name = name;
        this.password = password;
        this.remark = remark;
        this.userID = userID;
        this.username = username;
        this.friends = friends;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRemark() {
        return remark;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }


}
