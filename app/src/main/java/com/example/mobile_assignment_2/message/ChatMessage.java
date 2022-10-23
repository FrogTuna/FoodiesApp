package com.example.mobile_assignment_2.message;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Comparator;

public class ChatMessage implements Comparable<ChatMessage>{

    private String senderText;
    private String senderTime;
    private int senderImage;
    private String role;
    private int index;


    public ChatMessage(String senderText, String senderTime, int senderImage, String role, int index) {
        this.senderText = senderText;
        this.senderTime = senderTime;
        this.senderImage = senderImage;
        this.role = role;
        this.index = index;
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

    public String getRole(){ return role; }

    public int getIndex(){ return index; }


    @Override
    public int compareTo(ChatMessage chatMessage) {
        return this.index - chatMessage.index;
    }
}

class IndexComparator implements Comparator<ChatMessage>{

    @Override
    public int compare(ChatMessage chatMessage1, ChatMessage chatMessage2) {
        return chatMessage1.getIndex() - chatMessage2.getIndex();
    }

    @Override
    public Comparator<ChatMessage> reversed() {
        return Comparator.super.reversed();
    }
}
