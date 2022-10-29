package com.example.mobile_assignment_2.chat;

public class ChatListData {
    private String username;
    private String friendID;
    private String lastMsg;
    private int avatarID;


    public ChatListData(String _friendID, String _username, String _lastMsg, int _avatarID) {
        this.friendID = _friendID;
        this.username = _username;
        this.lastMsg = _lastMsg;
        this.avatarID = _avatarID;
    }
    // Following is Get/Set function
    public String getFriendID() {
        return this.friendID;
    }
    public void setfriendID(String _friendID) {
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

    public int getAvatar() {
        return this.avatarID;
    }
    public void setAvatar(int _avatarID) {
        this.avatarID = avatarID;
    }



}
