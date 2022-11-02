package com.example.mobile_assignment_2.chat;

public class RequestListData {
    //    private String ID;
    private String username;
    private String comment;
    private String avatarID;
    private String userID;


    public RequestListData(String _userID, String _username, String _comment, String _avatarID) {

        this.userID = _userID;
        this.username = _username;
        this.comment = _comment;
        this.avatarID = _avatarID;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String _comment) {
        this.comment = _comment;
    }

    public String getAvatar() {
        return this.avatarID;
    }

    public void setAvatar(int _avatarID) {
        this.avatarID = avatarID;
    }


}
