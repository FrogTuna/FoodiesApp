package com.example.mobile_assignment_2.me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.home.PostDetails;
import com.example.mobile_assignment_2.home.PostItemClickListener;
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
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class CollectPostsActivity extends AppCompatActivity {


    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<String> collectPids = new ArrayList<>();
    ArrayList<Post> collectPosts = new ArrayList<>();
    RecyclerView postsRecyclerView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    MyPostsAdapter myPostsAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_posts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference postsRef = firebaseDatabase.getReference("Posts");
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    posts.add(post);
                }
                DatabaseReference usersRef = firebaseDatabase.getReference("Users");
                usersRef.child(currentUser.getUid()).child("collects").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        collectPids.clear();
                        collectPosts.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String collectPid = (String) dataSnapshot.getValue();
                            collectPids.add(collectPid);
                        }
                        Log.d("Collects", String.valueOf(collectPids.size()));
                        for (Post p : posts) {

                            if (collectPids.contains(p.getPid())) {
                                collectPosts.add(p);
                            }
                        }
                        Collections.reverse(collectPosts);
                        postsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        postsRecyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        postsRecyclerView.setLayoutManager(gridLayoutManager);
                        myPostsAdapter = new MyPostsAdapter(collectPosts, getApplicationContext());
                        myPostsAdapter.setClickListener(new PostItemClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Post post = collectPosts.get(position);
                                Intent i = new Intent(CollectPostsActivity.this, PostDetails.class);
                                i.putExtra("title", post.getTitle());
                                i.putExtra("description", post.getDescription());
                                i.putExtra("author", post.getAuthor());
                                i.putExtra("pid", post.getPid());
                                i.putExtra("uid", post.getUid());
                                i.putStringArrayListExtra("imageURLs", post.getImageUrls());

                                startActivity(i);
                            }
                        });
                        postsRecyclerView.setAdapter(myPostsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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