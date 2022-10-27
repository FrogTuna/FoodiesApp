package com.example.mobile_assignment_2.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PostDetails extends AppCompatActivity {
    TextView titleView;
    TextView descripView;
    TextView authorView;
    LinearLayout linearLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        ArrayList<String> imageURLs = intent.getStringArrayListExtra("imageURLs");
        titleView = findViewById(R.id.post_title);
        descripView = findViewById(R.id.post_description);
        authorView = findViewById(R.id.author_name);
        titleView.setText(title);
        descripView.setText(description);
        authorView.setText(author);
        linearLayout = findViewById(R.id.post_linearLayout);
        imageView = findViewById(R.id.post_image);
        // Download image from URL and set to imageView
        Picasso.with(this).load(imageURLs.get(0)).fit().centerCrop().into(imageView);
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