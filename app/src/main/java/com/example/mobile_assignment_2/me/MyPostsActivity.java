package com.example.mobile_assignment_2.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.home.PostDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author: Weiran Zou
 * @description: My posts screen displaying user createed posts
 */
public class MyPostsActivity extends AppCompatActivity {

    ArrayList<Post> myPosts = new ArrayList<>();
    RecyclerView postsRecyclerView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    MyPostsAdapter myPostsAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_postlists);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference postsRef = firebaseDatabase.getReference("Posts");
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPosts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getUid().equals(currentUser.getUid())) {
                        myPosts.add(post);
                    }
                }
                Collections.reverse(myPosts);
                postsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                postsRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                postsRecyclerView.setLayoutManager(gridLayoutManager);
                myPostsAdapter = new MyPostsAdapter(myPosts, getApplicationContext());
                postsRecyclerView.setAdapter(myPostsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
