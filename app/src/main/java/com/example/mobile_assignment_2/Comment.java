package com.example.mobile_assignment_2;

public class Comment {
    String author, commentMessage, authorProfileUrl;

    public Comment(String author, String commentMessage, String authorProfileUrl) {
        this.author = author;
        this.commentMessage = commentMessage;
        this.authorProfileUrl = authorProfileUrl;
    }

    public Comment() {

    }

    public String getAuthor() {
        return author;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public String getAuthorProfileUrl() {
        return authorProfileUrl;
    }
}
