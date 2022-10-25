package com.example.mobile_assignment_2.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements PostItemClickListener {
    private ArrayList<Post> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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



        posts.add(new Post("title 1", "description 1", "author 1", "", new ArrayList<>()));
        posts.add(new Post("title 2", "description 2", "author 2", "", new ArrayList<>()));
        posts.add(new Post("title 3", "description 3", "author 3", "", new ArrayList<>()));
        posts.add(new Post("title 4", "description 4", "author 4", "", new ArrayList<>()));
        posts.add(new Post("title 5", "description 5", "author 5", "", new ArrayList<>()));
        posts.add(new Post("title 6", "description 6", "author 6", "", new ArrayList<>()));
        // Inflate the layout for this fragment

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CustomAdapter customAdapter = new CustomAdapter(posts);
        customAdapter.setClickListener(this);
        recyclerView.setAdapter(customAdapter);

        return view;
    }

    @Override
    public void onClick(View view, int position) {
        Post post = posts.get(position);
        Intent i = new Intent(getActivity(), PostDetails.class);
        i.putExtra("title", post.getTitle());
        i.putExtra("description", post.getDescription());
        i.putExtra("author", post.getAuthor());
        Log.i("hello", post.getTitle());
        Log.i("hello", post.getDescription());
        startActivity(i);
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Post> posts = new ArrayList<Post>();
        private PostItemClickListener postItemClickListener;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView titleView;

            TextView authorView;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                view.setOnClickListener(this);
                titleView =  (TextView) view.findViewById(R.id.post_title);
                authorView = (TextView)  view.findViewById(R.id.author_name);
            }


            @Override
            public void onClick(View view) {
                if(postItemClickListener != null) {
                    postItemClickListener.onClick(view, getAbsoluteAdapterPosition());
                }
            }
        }

        public CustomAdapter(ArrayList<Post> posts) {
            this.posts = posts;

        }
        public void setClickListener(PostItemClickListener postItemClickListener) {
            this.postItemClickListener = postItemClickListener;
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
            //Post post = (Post) posts.get(position);
            viewHolder.titleView.setText(posts.get(position).getTitle());

            viewHolder.authorView.setText(posts.get(position).getAuthor());

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