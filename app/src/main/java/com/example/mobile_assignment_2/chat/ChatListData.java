package com.example.mobile_assignment_2.chat;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class ChatListData {
    private String username;
    private String friendID;
    private String lastMsg;
    private String imgURL;


    public ChatListData(String _friendID, String _username, String _lastMsg, String _imgURL) {
        this.friendID = _friendID;
        this.username = _username;
        this.lastMsg = _lastMsg;
        this.imgURL = _imgURL;
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


}
