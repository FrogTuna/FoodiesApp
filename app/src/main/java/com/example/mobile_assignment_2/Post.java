package com.example.mobile_assignment_2;

import java.util.ArrayList;

public class Post {
    private String title, description, author, uid, pid;
    private ArrayList<String> imageUrls;
    public Post(String title, String description, String author, String uid, ArrayList<String> imageUrls, String pid) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.imageUrls = imageUrls;
        this.pid = pid;
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
    public ArrayList<String> getImageUrls() {return imageUrls;}
    public String getPid() {return pid;}
}
