package com.example.mobile_assignment_2;

public class Post {
    private String title, description, author, uid;
    public Post(String title, String description, String author, String uid) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.uid = uid;
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
}
