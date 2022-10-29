package com.example.mobile_assignment_2.community;

import java.util.ArrayList;

public class Communitypost {
    // fruitImage to store the resource id if post image
    // private int commImage;
    // fruitName to store the string of fruit name
    private String commName, uid, cid;
    private ArrayList<String> imageUrls;
    private String comType;
    private String authorName;
    private String comDescription;

    public Communitypost() {
    }

    public Communitypost(String cid, String uid, String commName, ArrayList<String> imageUrls, String comType, String authorName, String comDescription) {
        this.cid = cid;
        this.uid = uid;
        this.commName = commName;
        this.imageUrls = imageUrls;
        this.comType = comType;
        this.authorName = authorName;
        this.comDescription = comDescription;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

//    public int getCommImage() {
//        return commImage;
//    }
//
//    public void setCommImage(int commImage) {
//        this.commImage = commImage;
//    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComDescription() {
        return comDescription;
    }

    public void setComDescription(String comDescription) {
        this.comDescription = comDescription;
    }
}
