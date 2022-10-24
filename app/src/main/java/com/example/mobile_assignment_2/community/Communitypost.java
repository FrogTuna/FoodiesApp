package com.example.mobile_assignment_2.community;

public class Communitypost {
    // fruitImage to store the resource id if post image
    private int commImage;
    // fruitName to store the string of fruit name
    private String commName;

    public Communitypost(int commImage, String commName) {
        this.commImage = commImage;
        this.commName = commName;
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
