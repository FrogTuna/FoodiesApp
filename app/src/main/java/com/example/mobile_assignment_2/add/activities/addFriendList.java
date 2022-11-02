package com.example.mobile_assignment_2.add.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author:
 * @description: Show the friends list after adding friends activities
 */
public class addFriendList extends AppCompatActivity {

    private String currentUID, flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friendslist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent myIntent = getIntent(); // gets the previously created intent

        /* Params from "shakeActivity" */
        ArrayList<HashMap<String, String>> userInfosArrayList = (ArrayList<HashMap<String, String>>) myIntent.getSerializableExtra("userInfosArrayList");
        currentUID = myIntent.getExtras().getString("currentUser");
        flag = myIntent.getExtras().getString("flag");

        addFriendListData[] addfriendlistdata = new addFriendListData[userInfosArrayList.size()];

        for (int i = 0; i < userInfosArrayList.size(); i++) {
            addfriendlistdata[i] = new addFriendListData(userInfosArrayList.get(i).get("ID"), userInfosArrayList.get(i).get("username"), userInfosArrayList.get(i).get("imageUrl"));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.friendRecyclerView);
        addFriendListAdapter addFriendListAdapter = new addFriendListAdapter(addfriendlistdata, currentUID, flag);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addFriendListAdapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
