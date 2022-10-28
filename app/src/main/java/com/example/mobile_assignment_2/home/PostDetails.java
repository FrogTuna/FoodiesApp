package com.example.mobile_assignment_2.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PostDetails extends AppCompatActivity {
    TextView titleView;
    TextView descripView;
    TextView authorView;
    LinearLayout linearLayout;
    RecyclerView imagesRecyclerView;
    Button likeBtn;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ArrayList<String> likes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        ArrayList<String> imageURLs = intent.getStringArrayListExtra("imageURLs");
        String pid = intent.getStringExtra("pid");
        final int[] num_likes = {Integer.parseInt(intent.getStringExtra("likes"))};
        titleView = findViewById(R.id.post_title);
        descripView = findViewById(R.id.post_description);
        authorView = findViewById(R.id.author_name);
        titleView.setText(title);
        descripView.setText(description);
        authorView.setText(author);
        linearLayout = findViewById(R.id.post_linearLayout);
        imagesRecyclerView = findViewById(R.id.recyclerView);
        imagesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager imagesLinearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        imagesRecyclerView.setLayoutManager(imagesLinearLayoutManager);
        ImagesAdapter imagesAdapter = new ImagesAdapter(imageURLs, this, R.layout.post_details_image_view);
        imagesRecyclerView.setAdapter(imagesAdapter);

        likeBtn = findViewById(R.id.like);
        likeBtn.setText(String.valueOf(num_likes[0]));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference("Users");

        usersRef.child(currentUser.getUid()).child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                likes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String likedPid = (String) dataSnapshot.getValue();
                    likes.add(likedPid);
                }
                // if user likes, set like btn to filled heart, otherwise, to unfilled heart
                if (likes.contains(pid)) {
                    likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_solid, 0, 0, 0);
                } else {
                    likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!likes.contains(pid)) {
                    num_likes[0] += 1;
                    firebaseDatabase.getReference("Posts").child(pid).child("likes").setValue(num_likes[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // push to user's likes
                            DatabaseReference userLikesRef = firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("likes").push();
                            userLikesRef.setValue(pid);
                            Toast.makeText(getApplicationContext(), "You liked this post",Toast.LENGTH_LONG).show();
                            likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_solid, 0, 0, 0);
                            likeBtn.setText(String.valueOf(num_likes[0]));
                        }
                    });
                } else {
                    num_likes[0] -= 1;
                    firebaseDatabase.getReference("Posts").child(pid).child("likes").setValue(num_likes[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("likes").orderByValue().equalTo(pid).getRef().removeValue();
                            Toast.makeText(getApplicationContext(), "You cancelled like",Toast.LENGTH_LONG).show();
                            likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                            likeBtn.setText(String.valueOf(num_likes[0]));
                        }
                    });
                }

            }
        });

        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.comment, null);
            TextView authorView = view.findViewById(R.id.author_name);
            TextView commentView = view.findViewById(R.id.comment_text);

            authorView.setText(new String(String.valueOf(i)));
            commentView.setText("comment: "+new String(String.valueOf(i)));
            linearLayout.addView(view);
        }
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