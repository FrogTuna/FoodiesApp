package com.example.mobile_assignment_2.chat;


public class FriendListData {
    private String username;
    private String remark;
    private int avatarID;


    public FriendListData(String _username, String _remark, int _avatarID) {
        this.username = _username;
        this.remark = _remark;
        this.avatarID = _avatarID;
    }
    // Following is Get/Set function
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String _remark) {
        this.remark = _remark;
    }

    public int getAvatar() {
        return this.avatarID;
    }
    public void setAvatar(int _avatarID) {
        this.avatarID = avatarID;
    }



}
