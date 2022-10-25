package com.example.mobile_assignment_2.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
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


        ArrayList<Post> forYouPosts = new ArrayList<>();
        forYouPosts.add(new Post("1","a","","",new ArrayList<>()));
        forYouPosts.add(new Post("2","b","","",new ArrayList<>()));
        forYouPosts.add(new Post("3","c","","",new ArrayList<>()));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(forYouPosts);
        recyclerView.setAdapter(customAdapter);
        return view;
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Post> posts = new ArrayList<Post>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleView;
            TextView authorView;
            TextView descpView;
            ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                titleView =  (TextView) view.findViewById(R.id.post_title);
                authorView = (TextView)  view.findViewById(R.id.author_name);
                imageView = (ImageView) view.findViewById(R.id.post_image);
                descpView =  (TextView) view.findViewById(R.id.post_description);
            }

        }

        public CustomAdapter(ArrayList<Post> posts) {
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
        public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, final int position) {

            viewHolder.titleView.setText(posts.get(position).getTitle());
            viewHolder.authorView.setText(posts.get(position).getAuthor());
            viewHolder.descpView.setText(posts.get(position).getDescription());

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