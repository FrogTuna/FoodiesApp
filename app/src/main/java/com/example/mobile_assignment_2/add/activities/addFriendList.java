package com.example.mobile_assignment_2.add.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.chat.FriendListAdapter;
import com.example.mobile_assignment_2.chat.FriendListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class addFriendList extends AppCompatActivity {

    private CircleImageView friendImageView;
    private TextView friendName, friendEmail, friendDescription;
    private String currentUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friendslist);



//        friendImageView = findViewById(R.id.imageViewAvatar);
//        friendName = findViewById(R.id.friend_name);
//        friendEmail = findViewById(R.id.friend_distance);
//        friendDescription = findViewById(R.id.post_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent myIntent = getIntent(); // gets the previously created intent
//        String objectUser = myIntent.getStringExtra("objectUser"); // will return "FirstKeyValue"
//        String currentUser= myIntent.getStringExtra("currentUser"); // will return "SecondKeyValue"
//
//        Log.w("objectUser:", objectUser);
//        Log.w("currentUser:", currentUser);

        /* Params from "shakeActivity" */
        ArrayList<HashMap<String, String>> userInfosArrayList = (ArrayList<HashMap<String, String>>)myIntent.getSerializableExtra("userInfosArrayList");
        currentUID = myIntent.getExtras().getString("currentUser");

        addFriendListData[] addfriendlistdata = new addFriendListData[userInfosArrayList.size()];

        for(int i = 0; i < userInfosArrayList.size(); i++) {
            addfriendlistdata[i] = new addFriendListData(userInfosArrayList.get(i).get("ID"), userInfosArrayList.get(i).get("username"), userInfosArrayList.get(i).get("imageUrl"));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.friendRecyclerView);
        addFriendListAdapter addFriendListAdapter = new addFriendListAdapter(addfriendlistdata, currentUID);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addFriendListAdapter);
//        FriendListData[] friendListData = new FriendListData[friendArrayList.size()];



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



//        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                    // TODO: handle the post
//
////                updateDBFriends(mDatabase,dataSnapshot,objectUser,currentUser);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void updateDBFriends(DatabaseReference mDatabase,DataSnapshot dataSnapshot,String objectUser, String currentUser){

        Log.w("objectUserInfo:", objectUser);


        mDatabase.child("Users").child(objectUser).child("friends").child(currentUser).setValue("true");
        mDatabase.child("Users").child(currentUser).child("friends").child(objectUser).setValue("true");


        for (DataSnapshot user: dataSnapshot.child("Users").getChildren()) {

            Log.d("111111:", "name: "+user.child("name").getValue() +
                    " email: " + user.child("email").getValue()+
                    " description: "+ user.child("remark").getValue());

            if(user.getKey().equals(objectUser)){

//                Log.d("111111:", "name: "+user.child("name").getValue() +
//                        " email: " + user.child("email").getValue()+
//                        " description: "+ user.child("remark").getValue());

                friendName.setText((String)user.child("name").getValue());
                friendEmail.setText((String)user.child("email").getValue());
                friendDescription.setText((String)user.child("remark").getValue());
                String image = (String)user.child("imageUrl").getValue();
//                Picasso.with(getApplicationContext()).load(image).fit().centerCrop().into(friendImageView);

            }
        }

    }

    public boolean friendsExist(DataSnapshot dataSnapshot,String objectUser, String currentUser){


        for (DataSnapshot user: dataSnapshot.child("Users").child(currentUser).child("friends").getChildren()) {
//            Log.w("111111:", user.getKey());
            if(user.getKey().equals(objectUser)){
                return true;
            }
        }
        return false;
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
