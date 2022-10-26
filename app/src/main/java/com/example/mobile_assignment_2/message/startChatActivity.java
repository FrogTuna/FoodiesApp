package com.example.mobile_assignment_2.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobile_assignment_2.R;

public class startChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);
        //goToConversationIntent();
    }



    private void goToConversationIntent(){

        Intent intent = new Intent(this, ChatWindowActivity.class);
        startActivity(intent);

    }


}