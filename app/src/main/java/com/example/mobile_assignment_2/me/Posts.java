package com.example.mobile_assignment_2.me;

public class Posts {
//    private String picture_URL;
    private String title;
    private String author;

    public Posts(String title, String author){
        this.title = title;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
