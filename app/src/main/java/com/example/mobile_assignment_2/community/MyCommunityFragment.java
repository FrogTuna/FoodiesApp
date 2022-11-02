package com.example.mobile_assignment_2.community;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class MyCommunityFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ListView listView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public MyCommunityFragment() {
    }

    public static MyCommunityFragment newInstance(String param1, String param2) {
        MyCommunityFragment fragment = new MyCommunityFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_community, container, false);
        ArrayList<String> communityPostsUri = new ArrayList<>();
        ArrayList<Communitypost> communityPosts = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to users
        DatabaseReference usrRef = firebaseDatabase.getReference("Users");

        usrRef.child(currentUser.getUid()).child("commLst").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                communityPostsUri.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String postUri = dataSnapshot.getKey();
                    Log.d("postUri", "postUri" + postUri);
                    communityPostsUri.add(postUri);
                }
                DatabaseReference commRef = firebaseDatabase.getReference("Community");
                commRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        communityPosts.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Communitypost post = dataSnapshot.getValue(Communitypost.class);
                            Log.d("idddd", post.getCid());
                            Log.d("commlist", "commlst length " + communityPostsUri.size());
                            if (communityPostsUri.contains(post.getCid())) {
                                Log.d("testttttt", String.valueOf(communityPostsUri.contains(post.getCid())));
                                communityPosts.add(post);
                                Log.d("commlist", "commlst length" + communityPosts.size());
                            }

                        }
                        CustomAdapter customAdapter = new CustomAdapter(getContext(), communityPosts);
                        listView = view.findViewById(R.id.list);
                        listView.setAdapter(customAdapter);
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

    public class CustomAdapter extends BaseAdapter {
        private List<Communitypost> musers = null;
        private Context mcontext = null;


        public CustomAdapter(Context context, List<Communitypost> users) {
            musers = users;
            mcontext = context;
        }

        @Override
        public int getCount() {
            return musers.size();
        }

        @Override
        public Object getItem(int position) {
            return musers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public final class ViewHolder {
            ImageView image;
            TextView title;
            TextView content;
            Button delete;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.community_leave_posts, null);
                holder.image = convertView.findViewById(R.id.communityImg);
                holder.title = convertView.findViewById(R.id.community_name);
                holder.content = convertView.findViewById(R.id.community_description);
                holder.delete = convertView.findViewById(R.id.btn_leave);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("Users").child(currentUser.getUid()).child("commLst").child(musers.get(position).getCid()).removeValue();
                        musers.remove(position);
                        notifyDataSetChanged();


                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(musers.get(position).getCommName());
            holder.content.setText(musers.get(position).getComDescription());
            String imageUrl = musers.get(position).getImageUrls();

            // Download image from URL and set to imageView
            Picasso.with(getContext()).load(imageUrl).fit().centerCrop().into(holder.image);
            return convertView;
        }
    }


}
