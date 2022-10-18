package com.example.mobile_assignment_2.chat;

public class ChatListData {
    private String username;
    private String content;
    private int avatarID;


    public ChatListData(String _username, String _content, int _avatarID) {
        this.username = _username;
        this.content = _content;
        this.avatarID = _avatarID;
    }
    // Following is Get/Set function
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getContent() {
        return this.content;
    }
    public void setRemark(String _content) {
        this.content = _content;
    }

    public int getAvatar() {
        return this.avatarID;
    }
    public void setAvatar(int _avatarID) {
        this.avatarID = avatarID;
    }



}
