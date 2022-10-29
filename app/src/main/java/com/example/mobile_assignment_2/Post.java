package com.example.mobile_assignment_2;

import java.util.ArrayList;
import java.util.HashMap;

public class Post {
    private String title, description, author, uid, pid;
    private int likes, collects, numComments;
    private HashMap<String, Comment> comments;
    private ArrayList<String> imageUrls;
    public Post(String title, String description, String author, String uid, ArrayList<String> imageUrls, String pid, int likes, int collects, int numComments, HashMap<String, Comment> comments) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.imageUrls = imageUrls;
        this.pid = pid;
        this.likes = likes;
        this.collects = collects;
        this.numComments = numComments;
        this.comments = comments;
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
    public int getLikes() {return likes;}
    public int getCollects() {return collects;}
    public int getNumComments() {return numComments;}
    public HashMap<String, Comment> getComments() {return comments;}
}
