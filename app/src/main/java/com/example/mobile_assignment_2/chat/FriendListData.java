package com.example.mobile_assignment_2.chat;


public class FriendListData {
    private String UID;
    private String username;
    private String remark;
    private int avatarID;



    public FriendListData(String _UID, String _username, String _remark, int _avatarID) {
        this.UID = _UID;
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
        this.avatarID = _avatarID;
    }

    public String getUID() {
        return this.UID;
    }
    public void setUID(String _UID) {
        this.UID = _UID;
    }

}
