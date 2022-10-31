package com.example.mobile_assignment_2.add.activities.addBySearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.mobile_assignment_2.Users;
import com.example.mobile_assignment_2.add.activities.addFriendList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

public class addActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView listView;
    ListViewAdapter adapter;
    SearchView editSearch;
    String[] animalNameList;
    ArrayList<friendItems> arraylist = new ArrayList<>();
    private ArrayList userInfosArrayList;
    FirebaseUser currentUser;
    FirebaseAuth myAuth;
    DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbysearch_view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();

        // Generate sample data

//        animalNameList = new String[]{"Joyce", "Wendy", "Estella",
//                "Christina", "Simon", "Becky", "Peter", "Leo",
//                "Wen"};
//
//        // Locate the ListView in listview_main.xml
//        listView = (ListView) findViewById(R.id.chat_addListView);
//        for (int i = 0; i < animalNameList.length; i++) {
//            friendItems friendItems = new friendItems(animalNameList[i]);
//            // Binds all strings into an array
//            arraylist.add(friendItems);
//        }
//        // Pass results to ListViewAdapter Class
//        adapter = new ListViewAdapter(this, arraylist);
//
//        // Binds the Adapter to the ListView
//        listView.setAdapter(adapter);
//
//        // Locate the EditText in listview_main.xml
        editSearch = (SearchView) findViewById(R.id.chat_addSearchView);
        editSearch.setOnQueryTextListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }




    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit:", query);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Users");





//        ArrayList<Object> objectsList = new ArrayList<>();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag = false;
                String userID = currentUser.getUid();
                Intent intent = new Intent(getApplicationContext(),addFriendList.class);

                if(dataSnapshot.exists()){
//                    userInfosArrayList.clear();
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                        String key = userSnapshot.getKey();
                        String email = String.valueOf(userSnapshot.child("email").getValue());

                        if(email.equals(query)){
                            userInfosArrayList = new ArrayList();
                            HashMap<String, String> userInfoHashMap = new HashMap<>();
                            Log.d("selected User by email:" , key + " "
                                    + (String)userSnapshot.child("username").getValue()  + " "
                                    + (String)userSnapshot.child("imageUrl").getValue());
                            userInfoHashMap.put("ID", key);
                            userInfoHashMap.put("username", (String)userSnapshot.child("username").getValue());
                            userInfoHashMap.put("imageUrl", (String)userSnapshot.child("imageUrl").getValue());
                            userInfosArrayList.add(userInfoHashMap);
                        }
                    }
                    System.out.println("[arr] " + userInfosArrayList);
                    intent.putExtra("userInfosArrayList", userInfosArrayList);
                    intent.putExtra("currentUser",userID);
                    startActivity(intent);
                }
//
//                for (DataSnapshot user: dataSnapshot.getChildren()) {
//                    // TODO: handle the post
////                    Log.w("user:", String.valueOf(user.child("email").getValue()));
//                    if(query.equals(String.valueOf(user.child("email").getValue()))){
////                        updateDBFriends(mDatabase,user,currentUser);
//                        flag = true;
//
//                        String objectUser = user.getKey();
//                        String curUser = currentUser.getUid();
//
//                        //                        friendName.setText((String)user.child("name").getValue());
////                        friendEmail.setText((String)user.child("email").getValue());
////                        friendDescription.setText((String)user.child("remark").getValue());
////                        String image = (String)user.child("imageUrl").getValue();
////                        Picasso.with(getApplicationContext()).load(image).fit().centerCrop().into(friendImageView);
//
//
//
//                        Log.d("new intent:", user.getKey() + "  " + currentUser.getUid());
//                        Intent intent = new Intent(getApplicationContext(), addFriendList.class);
//                        intent.putExtra("objectUser",objectUser);
//                        intent.putExtra("currentUser",curUser);
//                        intent.putExtra("name",String.valueOf(user.child("name").getValue()));
//                        intent.putExtra("email",String.valueOf(user.child("email").getValue()));
//                        intent.putExtra("remark","Description: " + String.valueOf(user.child("remark").getValue()));
//                        intent.putExtra("imageUrl",String.valueOf(user.child("imageUrl").getValue()));
//
//                        startActivity(intent);
//
//                    }
//                }
//
//                if(!flag){
//                    Toast.makeText(getApplicationContext(),"User does not exist!",Toast.LENGTH_SHORT).show();
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("readList:", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        String text = newText;
//        adapter.filter(text);
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
