package com.example.mobile_assignment_2.chat;

public class RequestListData {
//    private String ID;
    private String username;
    private String comment;
    private int avatarID;


    public RequestListData( String _username, String _comment, int _avatarID) {

        this.username = _username;
        this.comment = _comment;
        this.avatarID = _avatarID;
    }
    // Following is Get/Set function


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

    public int getAvatar() {
        return this.avatarID;
    }
    public void setAvatar(int _avatarID) {
        this.avatarID = avatarID;
    }


}
