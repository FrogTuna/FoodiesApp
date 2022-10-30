package com.example.mobile_assignment_2.add.activities.addBySearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import java.util.ArrayList;

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

public class addActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView listView;
    ListViewAdapter adapter;
    SearchView editSearch;
    String[] animalNameList;
    ArrayList<friendItems> arraylist = new ArrayList<>();

    FirebaseAuth myAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbysearch_view);

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
    }




    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit:", query);
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Users");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayList<Object> objectsList = new ArrayList<>();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    // TODO: handle the post
//                    Log.w("user:", String.valueOf(user.child("email").getValue()));
                    if(query.equals(String.valueOf(user.child("email").getValue()))){
//                        updateDBFriends(mDatabase,user,currentUser);
                        flag = true;

//                        Log.w("111111:", user.getKey());
//                        Log.w("111111:", (String) dataSnapshot.child("Users").child(currentUser.getUid()).
//                                child("friends").child(user.getKey()).getValue());
//                        Log.w("111111:", "22222");
//                        if(friendsExist(user,user.getKey(),currentUser.getUid())){
//                            Toast.makeText(getApplicationContext(),"You are already friends!",Toast.LENGTH_SHORT).show();
//                            break;
//                        }
//                        Log.w("111111:", "33333");

                        Log.w("user:", query);
                        Intent intent = new Intent(getApplicationContext(), addFriendList.class);
                        intent.putExtra("objectUser",user.getKey());
                        intent.putExtra("currentUser",currentUser.getUid());
                        startActivity(intent);
                    }
                }

                if(!flag){
                    Toast.makeText(getApplicationContext(),"User does not exist!",Toast.LENGTH_SHORT).show();
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
//        String text = newText;
//        adapter.filter(text);
        return false;
    }




}
