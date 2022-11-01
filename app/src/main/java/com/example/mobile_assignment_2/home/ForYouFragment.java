package com.example.mobile_assignment_2.home;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.Comment;
import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    ArrayList<String> collects = new ArrayList<>();
    ArrayList<Comment> commentsList = new ArrayList<>();
    // posts for user and user's friends
    ArrayList<Post> forYouPosts = new ArrayList<>();
    ForYouPostsAdapter forYouPostsAdapter;
    RecyclerView postsRecyclerView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
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
                        usersRef.child(currentUser.getUid()).child("collects").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                collects.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String collectPid = (String) dataSnapshot.getValue();
                                    collects.add(collectPid);
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
            Button collectBtn;
            Button commentBtn;
            ImageView profileView;
            public ViewHolder(View view) {
                super(view);
                titleView =  (TextView) view.findViewById(R.id.post_title);
                authorView = (TextView)  view.findViewById(R.id.author_name);
                descpView =  (TextView) view.findViewById(R.id.post_description);
                imagesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                likeBtn = (Button) view.findViewById(R.id.like);
                collectBtn = (Button) view.findViewById(R.id.collect);
                commentBtn = (Button) view.findViewById(R.id.comment);
                profileView = (ImageView) view.findViewById(R.id.profile_image);
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
            final int[] num_collects = {posts.get(position).getCollects()};
            final int[] num_comments = {posts.get(position).getNumComments()};
            HashMap<String, Comment> comments = posts.get(position).getComments();
            String author = posts.get(position).getAuthor();
            String pid = posts.get(position).getPid();
            viewHolder.likeBtn.setText(String.valueOf(num_likes[0]));
            viewHolder.collectBtn.setText(String.valueOf(num_collects[0]));
            viewHolder.titleView.setText(posts.get(position).getTitle());
            viewHolder.authorView.setText(author);
            viewHolder.descpView.setText(posts.get(position).getDescription());
            ArrayList<String> imageUrls = posts.get(position).getImageUrls();
            ImagesAdapter imagesAdapter = new ImagesAdapter(imageUrls, getContext(), R.layout.for_you_posts_image_view);
            viewHolder.imagesRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager imagesLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            viewHolder.imagesRecyclerView.setLayoutManager(imagesLinearLayoutManager);
            viewHolder.imagesRecyclerView.setAdapter(imagesAdapter);
            viewHolder.commentBtn.setText(String.valueOf(num_comments[0]));
            // if user likes, set like btn to filled heart, otherwise, to unfilled heart
            if (likes.contains(pid)) {
                viewHolder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_solid, 0, 0, 0);
            } else {
                viewHolder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
            }

            // if user collects, set collect btn to filled star, otherwise, to unfilled star
            if (collects.contains(pid)) {
                viewHolder.collectBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_solid, 0, 0, 0);
            } else {
                viewHolder.collectBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.collect, 0, 0, 0);
            }

            String uid = posts.get(position).getUid();
            firebaseDatabase.getReference().child("Users").child(uid).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error in fetching data", task.getException());
                    }
                    else {
                        String profileImageUrl = task.getResult().getValue(String.class);
                        // Download image from URL and set to imageView
                        Picasso.with(getContext()).load(profileImageUrl).fit().centerCrop().into(viewHolder.profileView);
                    }
                }
            });
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
                                DatabaseReference likeRef = firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("likes");
                                Query query = likeRef.orderByValue().equalTo(pid);
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            dataSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Toast.makeText(getContext(), "You cancelled like",Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }
            });

            viewHolder.collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastViewPosition = position;
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    if (!collects.contains(pid)) {
                        firebaseDatabase.getReference("Posts").child(pid).child("collects").setValue(num_collects[0] +1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // push to user's collects
                                DatabaseReference userLikesRef = firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("collects").push();
                                userLikesRef.setValue(pid);
                                Toast.makeText(getContext(), "You collected this post",Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        firebaseDatabase.getReference("Posts").child(pid).child("collects").setValue(num_collects[0] -1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DatabaseReference collectRef = firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("collects");
                                Query query = collectRef.orderByValue().equalTo(pid);
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            dataSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Toast.makeText(getContext(), "You cancelled collect",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            });
            viewHolder.commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View popupCommentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_popup_window,null);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;
                    PopupWindow popupWindow = new PopupWindow(popupCommentView,width, height*1/2, true);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    LinearLayout commentsLinearLayout = popupCommentView.findViewById(R.id.commentsLinearLayout);
                    Button sendBtn = popupCommentView.findViewById(R.id.sendCommentButton);
                    TextView commentTextField = popupCommentView.findViewById(R.id.textFieldComment);
                    firebaseDatabase.getReference().child("Posts").child(pid).child("comments").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            commentsList.clear();
                            commentsLinearLayout.removeAllViews();
                            for (DataSnapshot commentDataSnapshot : snapshot.getChildren()) {
                                Comment comment = commentDataSnapshot.getValue(Comment.class);
                                commentsList.add(comment);

                            }
                            for (Comment c : commentsList){
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.comment, null);
                                TextView authorView = view.findViewById(R.id.author_name);
                                TextView commentView = view.findViewById(R.id.comment_text);
                                ImageView profileView = view.findViewById(R.id.profile_image);

                                String profileImageUrl = c.getAuthorProfileUrl();
                                // Download image from URL and set to imageView
                                Picasso.with(getContext()).load(profileImageUrl).fit().centerCrop().into(profileView);
                                authorView.setText(c.getAuthor());
                                commentView.setText(c.getCommentMessage());
                                commentsLinearLayout.addView(view, 0);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    sendBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String commentText = commentTextField.getText().toString();
                            if (!commentText.isEmpty()) {
                                firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error in fetching data", task.getException());
                                        }
                                        else {
                                            String authorName = task.getResult().getValue(String.class);
                                            firebaseDatabase.getReference("Users").child(currentUser.getUid()).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    String profileImageUrl = task.getResult().getValue(String.class);
                                                    Comment comment = new Comment(authorName, commentText, profileImageUrl);
                                                    DatabaseReference commentRef = firebaseDatabase.getReference().child("Posts").child(pid).child("comments").push();
                                                    commentRef.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            firebaseDatabase.getReference().child("Posts").child(pid).child("numComments").setValue(num_comments[0]+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(getContext(), "Comment Added successfully",Toast.LENGTH_LONG).show();
                                                                    commentTextField.setText("");

                                                                }
                                                            });

                                                        }
                                                    });
                                                }
                                            });



                                        }
                                    }
                                });

                            }
                        }
                    });




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