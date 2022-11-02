package com.example.mobile_assignment_2.add.activities;

/**
 * @author: Yao-Wen Chang
 * @description: The data frame of friends list
 */
public class addFriendListData {
    private String UID;
    private String username;
    private String imgURL;


    public addFriendListData(String _UID, String _username, String _imgURL) {
        this.UID = _UID;
        this.username = _username;
        this.imgURL = _imgURL;
    }

    // Following is Get/Set function
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setImgURL(String _imgURL) {
        this.imgURL = _imgURL;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String _UID) {
        this.UID = _UID;
    }


}
