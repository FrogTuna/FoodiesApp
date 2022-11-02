package com.example.mobile_assignment_2.chat.firebaseDataStore;

import java.io.Serializable;

public class FriendshipInfo implements Serializable {
    /* User1 is the client currently use the app */
    public String User1, User2, Index;


    public FriendshipInfo() {

    }

    public FriendshipInfo(String User1, String User2, String Index) {
        this.User1 = User1;
        this.User2 = User2;
        this.Index = Index;
    }

    /* Getter method */

}
