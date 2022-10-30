package com.example.mobile_assignment_2.community;

public class Event {
    private String cid, eid, uid;
    private String userName;
    private String eventName, eventDate, evenTime, eventLocation;
    private int peopleNum;

    public Event(String cid, String eid, String uid,
                 String userName, String eventName, String eventDate,
                 String evenTime, String eventLocation, int peopleNum) {
        this.cid = cid;
        this.eid = eid;
        this.uid = uid;
        this.userName = userName;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.evenTime = evenTime;
        this.eventLocation = eventLocation;
        this.peopleNum = peopleNum;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEvenTime() {
        return evenTime;
    }

    public void setEvenTime(String evenTime) {
        this.evenTime = evenTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }
}
