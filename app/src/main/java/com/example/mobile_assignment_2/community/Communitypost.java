package com.example.mobile_assignment_2.community;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class Communitypost {
    // fruitImage to store the resource id if post image
    // private int commImage;
    // fruitName to store the string of fruit name
    private String commName, uid, cid;
    private String imageUrls;
    private String comType;
    private String authorName;
    private String comDescription;

    public Communitypost() {
    }

    public Communitypost(String cid, String uid, String commName, String imageUrls, String comType, String authorName, String comDescription) {
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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

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
