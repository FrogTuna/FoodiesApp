package com.example.mobile_assignment_2.home;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link ForYouFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForYouFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<String> friends = new ArrayList<>();
    ArrayList<String> likes = new ArrayList<>();
    // posts for user and user's friends
    ArrayList<Post> forYouPosts = new ArrayList<>();
    ForYouPostsAdapter forYouPostsAdapter;
    RecyclerView postsRecyclerView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    int lastViewPosition = 0;
    public ForYouFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForYouFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForYouFragment newInstance(String param1, String param2) {
        ForYouFragment fragment = new ForYouFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to users
        DatabaseReference usersRef = firebaseDatabase.getReference("Users");
        usersRef.child(currentUser.getUid()).child("friends").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String friendId = dataSnapshot.getKey();
                    friends.add(friendId);
                }
                usersRef.child(currentUser.getUid()).child("likes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        likes.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String likedPid = (String) dataSnapshot.getValue();
                            likes.add(likedPid);
                        }
                        DatabaseReference postsRef = firebaseDatabase.getReference("Posts");
                        postsRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                posts.clear();
                                forYouPosts.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Post post = dataSnapshot.getValue(Post.class);
                                    posts.add(post);
                                }

                                for (Post p : posts) {
                                    if (friends.contains(p.getUid()) || p.getUid().equals(currentUser.getUid())) {
                                        forYouPosts.add(p);
                                    }
                                }
                                Collections.reverse(forYouPosts);
                                postsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                                postsRecyclerView.setHasFixedSize(true);
                                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                postsRecyclerView.setLayoutManager(linearLayoutManager);
                                forYouPostsAdapter = new ForYouPostsAdapter(forYouPosts);
                                postsRecyclerView.setAdapter(forYouPostsAdapter);
                                postsRecyclerView.getLayoutManager().scrollToPosition(lastViewPosition);
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
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;

    }

    public class ForYouPostsAdapter extends RecyclerView.Adapter<ForYouPostsAdapter.ViewHolder> {

        private ArrayList<Post> posts = new ArrayList<Post>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleView;
            TextView authorView;
            TextView descpView;
            RecyclerView imagesRecyclerView;
            Button likeBtn;
            public ViewHolder(View view) {
                super(view);
                titleView =  (TextView) view.findViewById(R.id.post_title);
                authorView = (TextView)  view.findViewById(R.id.author_name);
                descpView =  (TextView) view.findViewById(R.id.post_description);
                imagesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                likeBtn = (Button) view.findViewById(R.id.like);
            }

        }

        public ForYouPostsAdapter(ArrayList<Post> posts) {
            this.posts = posts;
        }

        // Create new view
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.for_you_posts, viewGroup, false);


            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ForYouPostsAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
            final int[] num_likes = {posts.get(position).getLikes()};

            String pid = posts.get(position).getPid();
            viewHolder.likeBtn.setText(String.valueOf(num_likes[0]));

            viewHolder.titleView.setText(posts.get(position).getTitle());
            viewHolder.authorView.setText(posts.get(position).getAuthor());
            viewHolder.descpView.setText(posts.get(position).getDescription());
            ArrayList<String> imageUrls = posts.get(position).getImageUrls();
            ImagesAdapter imagesAdapter = new ImagesAdapter(imageUrls, getContext(), R.layout.for_you_posts_image_view);
            viewHolder.imagesRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager imagesLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            viewHolder.imagesRecyclerView.setLayoutManager(imagesLinearLayoutManager);
            viewHolder.imagesRecyclerView.setAdapter(imagesAdapter);

            // if user likes, set like btn to filled heart, otherwise, to unfilled heart
            if (likes.contains(pid)) {
                viewHolder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_solid, 0, 0, 0);
            } else {
                viewHolder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
            }

            viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastViewPosition = position;
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    if (!likes.contains(pid)) {
                        firebaseDatabase.getReference("Posts").child(pid).child("likes").setValue(num_likes[0] +1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // push to user's likes
                                DatabaseReference userLikesRef = firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("likes").push();
                                userLikesRef.setValue(pid);
                                Toast.makeText(getContext(), "You liked this post",Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        firebaseDatabase.getReference("Posts").child(pid).child("likes").setValue(num_likes[0] -1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("likes").orderByValue().equalTo(pid).getRef().removeValue();
                                Toast.makeText(getContext(), "You cancelled like",Toast.LENGTH_LONG).show();
                            }
                        });
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