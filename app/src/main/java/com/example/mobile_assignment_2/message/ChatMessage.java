package com.example.mobile_assignment_2.message;


import java.util.Comparator;

public class ChatMessage{

    private String senderText;
    private String senderTime;
    private int senderImage;
    private String role;
    private String chatID;


    public ChatMessage(String senderText, String senderTime, int senderImage, String chatID, String role) {
        this.senderText = senderText;
        this.senderTime = senderTime;
        this.senderImage = senderImage;
        this.chatID = chatID;
        this.role = role;
    }


    public String getSenderText() {
        return senderText;
    }

    public String getSenderTime() {
        return senderTime;
    }

    public int getSenderImage() {
        return senderImage;
    }

    public String getChatID(){ return chatID; }

    public String getRole(){ return role; }




}
