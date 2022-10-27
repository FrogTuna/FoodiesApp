package com.example.mobile_assignment_2.community;

import java.util.ArrayList;

public class Communitypost {
    // fruitImage to store the resource id if post image
    private int commImage;
    // fruitName to store the string of fruit name
    private String commName, uid;
    private ArrayList<String> imageUrls;

    public Communitypost() {
    }

    public Communitypost(int commImage, String commName) {
        this.commImage = commImage;
        this.commName = commName;
        this.uid = uid;
        this.imageUrls = imageUrls;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getCommImage() {
        return commImage;
    }

    public void setCommImage(int commImage) {
        this.commImage = commImage;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }
}
