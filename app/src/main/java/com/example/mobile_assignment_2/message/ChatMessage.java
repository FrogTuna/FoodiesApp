package com.example.mobile_assignment_2.message;

/**
 * @author: Yicong Li
 * @description: Message Class
 */
public class ChatMessage {


    //fields
    private String senderText;
    private String senderTime;
    private String senderImageString;
    private String role;
    private String chatID;



    //constructor
    public ChatMessage(String senderText, String senderTime, String senderImageString, String chatID, String role) {
        this.senderText = senderText;
        this.senderTime = senderTime;
        this.senderImageString = senderImageString;
        this.chatID = chatID;
        this.role = role;
    }



    //getter
    public String getSenderText() {
        return senderText;
    }

    public String getSenderTime() {
        return senderTime;
    }

    public String getSenderImage() {
        return senderImageString;
    }

    public String getChatID() {
        return chatID;
    }

    public String getRole() {
        return role;
    }


}
