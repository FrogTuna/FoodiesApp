package com.example.mobile_assignment_2.add.activities.addBySearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.R;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.mobile_assignment_2.add.activities.addFriendList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class addActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    ListViewAdapter adapter;
    SearchView editSearch;
    String[] recordList;
    ArrayList<friendItems> arraylist = new ArrayList<>();
    private ArrayList userInfosArrayList;
    FirebaseUser currentUser;
    FirebaseAuth myAuth;
    DatabaseReference mDatabase;
    private boolean runOnce = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbysearch_view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();
        setTitle("Add Friends - Search");
        // Generate sample data

        recordList = new String[]{"leo727268082@gmail.com", "kyy2@student.unimelb.edu.au",
                "727268082@qq.com", "zouweiran9122@gmail.com"};

        // Locate the ListView in listview_main.xml
        listView = (ListView) findViewById(R.id.chat_addListView);
        for (int i = 0; i < recordList.length; i++) {
            friendItems friendItems = new friendItems(recordList[i]);
            // Binds all strings into an array
            arraylist.add(friendItems);
        }
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editSearch = (SearchView) findViewById(R.id.chat_addSearchView);
        editSearch.setOnQueryTextListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toast.makeText(getApplicationContext(), "Please input username or email", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit:", query);

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (runOnce) {
                    runOnce = false;
                    String userID = currentUser.getUid();
                    Intent intent = new Intent(getApplicationContext(), addFriendList.class);

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String key = userSnapshot.getKey();
                            String email = String.valueOf(userSnapshot.child("email").getValue());
                            String userName = String.valueOf(userSnapshot.child("username").getValue());

                            if (email.equals(query) || userName.equals(query)) {
                                userInfosArrayList = new ArrayList<>();
                                HashMap<String, String> userInfoHashMap = new HashMap<>();
                                Log.d("selected User by email:", key + " "
                                        + (String) userSnapshot.child("username").getValue() + " "
                                        + (String) userSnapshot.child("imageUrl").getValue());
                                userInfoHashMap.put("ID", key);
                                userInfoHashMap.put("username", (String) userSnapshot.child("username").getValue());
                                userInfoHashMap.put("imageUrl", (String) userSnapshot.child("imageUrl").getValue());
                                userInfosArrayList.add(userInfoHashMap);
                                System.out.println("[arr] " + userInfosArrayList);
                                intent.putExtra("userInfosArrayList", userInfosArrayList);
                                intent.putExtra("currentUser", userID);
                                intent.putExtra("flag", "add");
                                startActivity(intent);

                                Log.d("debug: ", "*************** start intent");
                            }
                        }
                    }
                }

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
