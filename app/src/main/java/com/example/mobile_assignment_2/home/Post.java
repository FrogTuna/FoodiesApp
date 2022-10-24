package com.example.mobile_assignment_2.home;

public class Post {
    private String title, description, author;
    public Post(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
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
}
