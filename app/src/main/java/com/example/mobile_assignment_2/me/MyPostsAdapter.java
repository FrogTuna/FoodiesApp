package com.example.mobile_assignment_2.me;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.home.PostItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.ViewHolder> {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private PostItemClickListener postItemClickListener;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleView;
        TextView authorView;
        ImageView imageView;
        TextView num_like_View;
        ImageView profileView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(this);
            titleView = (TextView) view.findViewById(R.id.post_title);
            authorView = (TextView) view.findViewById(R.id.author_name);
            imageView = (ImageView) view.findViewById(R.id.post_image);
            num_like_View = (TextView) view.findViewById(R.id.like_text);
            profileView = (ImageView) view.findViewById(R.id.profile_image);
        }


        @Override
        public void onClick(View view) {
            if (postItemClickListener != null) {
                postItemClickListener.onClick(view, getAbsoluteAdapterPosition());
            }
        }
    }

    public MyPostsAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;

    }

    public void setClickListener(PostItemClickListener postItemClickListener) {
        this.postItemClickListener = postItemClickListener;
    }

    // Create new view
    @Override
    public MyPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.explore_posts, viewGroup, false);

        return new MyPostsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyPostsAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.titleView.setText(posts.get(position).getTitle());
        viewHolder.authorView.setText(posts.get(position).getAuthor());
        String imageUrl = posts.get(position).getImageUrls().get(0);
        // Download image from URL and set to imageView
        Picasso.with(context).load(imageUrl).fit().centerCrop().into(viewHolder.imageView);
        viewHolder.num_like_View.setText(posts.get(position).getLikes() + " Likes");
        String uid = posts.get(position).getUid();
        firebaseDatabase.getReference().child("Users").child(uid).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error in fetching data", task.getException());
                } else {
                    String profileImageUrl = task.getResult().getValue(String.class);
                    // Download image from URL and set to imageView
                    Picasso.with(context).load(profileImageUrl).fit().centerCrop().into(viewHolder.profileView);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}