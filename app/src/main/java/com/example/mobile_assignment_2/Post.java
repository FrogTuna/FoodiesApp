package com.example.mobile_assignment_2;

import java.util.ArrayList;

public class Post {
    private String title, description, author, uid;
    private ArrayList<String> imageUrl;
    public Post(String title, String description, String author, String uid, ArrayList<String> imageUrl) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.imageUrl = imageUrl;
    }
    public Post(){

    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
    public String getUid() {
        return uid;
    }
    public ArrayList<String> getImageUrl() {return imageUrl;}
}
