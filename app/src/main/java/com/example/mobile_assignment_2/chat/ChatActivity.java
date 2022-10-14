package com.example.mobile_assignment_2.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mobile_assignment_2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;
    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ChatPagerAdapter chatPagerAdapter;
    private SearchView searchView;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        public static class Post {
//
//            public String author;
//            public String title;
//
//            public Post(String author, String title) {
//                // ...
//            }
//
//        }

        // Get a reference to our posts
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("https://foodies-27bb7-default-rtdb.firebaseio.com/");
//
//// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Post post = dataSnapshot.getValue(Post.class);
//                System.out.println(post);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
        initialFireBase("Test");

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
//        searchView = findViewById(R.id.searchView);

        chatPagerAdapter = new ChatPagerAdapter(this, NUM_PAGES);
        viewPager2.setAdapter(chatPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
            new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("Chat");
                            break;
                        case 1:
                            tab.setText("Friend");
                            break;
                        case 2:
                            tab.setText("Request");
                            break;
                        default:
                            break;
                    }
                }
            }).attach();

    }
    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }
    private void initialFireBase(String referenceName){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference(referenceName);

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String inputText = snapshot.getValue(String.class);
//                displayField.setText(inputText);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void searchUsername() {

    }



}