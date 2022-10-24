package com.example.mobile_assignment_2;

import java.util.ArrayList;

public class Post {
    private String title, description, author, uid;
    private ArrayList<String> imageUrls;
    public Post(String title, String description, String author, String uid, ArrayList<String> imageUrls) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.imageUrls = imageUrls;
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
    public ArrayList<String> getImageUrl() {return imageUrls;}
}
