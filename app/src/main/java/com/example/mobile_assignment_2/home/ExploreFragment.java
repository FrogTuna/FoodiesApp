package com.example.mobile_assignment_2.home;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Collections;


/**
 * @author: Weiran Zou
 * @description: Screen of 'Explore' tab on Home screen, displaying the posts of strangers of user
 * in two-column view. The user can click on any post to go to the Post Details screen.
 */
public class ExploreFragment extends Fragment {
    private ArrayList<Post> posts = new ArrayList<>();
    ArrayList<Post> strangerPosts = new ArrayList<>();
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ExplorePostsAdapter explorePostsAdapter;


    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        ArrayList<String> friends = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Get a reference to users
        DatabaseReference usersRef = firebaseDatabase.getReference("Users");
        // Get uids of friends of current user's and listen for changes
        usersRef.child(currentUser.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String friendId = dataSnapshot.getKey();
                    friends.add(friendId);
                }
                // Get posts and listen for changes
                DatabaseReference postsRef = firebaseDatabase.getReference("Posts");
                postsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        posts.clear();
                        strangerPosts.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Post post = dataSnapshot.getValue(Post.class);
                            posts.add(post);
                        }

                        // Filter posts for strangers of current user
                        for (Post p : posts) {
                            if (!friends.contains(p.getUid()) && !p.getUid().equals(currentUser.getUid())) {
                                strangerPosts.add(p);
                            }
                        }
                        Collections.reverse(strangerPosts);
                        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        explorePostsAdapter = new ExplorePostsAdapter(strangerPosts);
                        recyclerView.setAdapter(explorePostsAdapter);

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
        return view;
    }

    // RecyclerView Adapter to bind posts data to views in recyclerView
    public class ExplorePostsAdapter extends RecyclerView.Adapter<ExplorePostsAdapter.ViewHolder> {

        private ArrayList<Post> posts = new ArrayList<Post>();

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

            // Handle click event on view in recyclerView, redirecting to post details screen
            @Override
            public void onClick(View view) {
                Post post = strangerPosts.get(getAbsoluteAdapterPosition());
                Intent i = new Intent(getActivity(), PostDetails.class);
                i.putExtra("title", post.getTitle());
                i.putExtra("description", post.getDescription());
                i.putExtra("author", post.getAuthor());
                i.putExtra("pid", post.getPid());
                i.putExtra("uid", post.getUid());
                i.putStringArrayListExtra("imageURLs", post.getImageUrls());

                startActivity(i);
            }
        }

        public ExplorePostsAdapter(ArrayList<Post> posts) {
            this.posts = posts;

        }


        // Create new view
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.explore_posts, viewGroup, false);

            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.titleView.setText(posts.get(position).getTitle());
            viewHolder.authorView.setText(posts.get(position).getAuthor());
            String imageUrl = posts.get(position).getImageUrls().get(0);
            // Download image from URL and set to imageView
            Picasso.with(getContext()).load(imageUrl).fit().centerCrop().into(viewHolder.imageView);
            viewHolder.num_like_View.setText(posts.get(position).getLikes() + " Likes");
            String uid = posts.get(position).getUid();
            // Get author's profile photo url
            firebaseDatabase.getReference().child("Users").child(uid).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error in fetching data", task.getException());
                    } else {
                        String profileImageUrl = task.getResult().getValue(String.class);
                        // Download image from URL and set to imageView
                        Picasso.with(getContext()).load(profileImageUrl).fit().centerCrop().into(viewHolder.profileView);
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


}