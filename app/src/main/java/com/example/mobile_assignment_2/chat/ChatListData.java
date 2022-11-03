package com.example.mobile_assignment_2.chat;

/**
 * @author: Yao-Wen Chang
 * @description: This class consists of attributes of each user, and also provide the getter and setter
 * methods
 */
public class ChatListData {
    private String username;
    private String friendID;
    private String lastMsg;
    private String imgURL;
    private String hasRead;


    public ChatListData(String _friendID, String _username, String _lastMsg, String _imgURL, String _hasRead) {
        this.friendID = _friendID;
        this.username = _username;
        this.lastMsg = _lastMsg;
        this.imgURL = _imgURL;
        this.hasRead = _hasRead;
    }

    // Following is Get/Set function
    public String getFriendID() {
        return this.friendID;
    }

    public void setFriendID(String _friendID) {
        this.friendID = friendID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getLastMsg() {
        return this.lastMsg;
    }

    public void setLastMsg(String _lastMsg) {
        this.lastMsg = _lastMsg;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setAvatar(String _imgURL) {
        this.imgURL = _imgURL;
    }



    public String getHasRead() {return this.hasRead; }
    public void setHasRead(String  _hasRead) {this.hasRead = _hasRead; }


}
