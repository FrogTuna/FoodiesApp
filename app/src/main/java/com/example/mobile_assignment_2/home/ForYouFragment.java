package com.example.mobile_assignment_2.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobile_assignment_2.Post;
import com.example.mobile_assignment_2.R;

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
    private ListView listView;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);


        ArrayList<Post> posts = new ArrayList<>();

        CustomAdapter customAdapter = new CustomAdapter(getContext(), posts);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        ArrayList<Post> newPosts = new ArrayList<>();
        newPosts.add(new Post("title 1", "description 1", "author 1", "", new ArrayList<>()));
        newPosts.add(new Post("title 2", "description 2", "author 2", "", new ArrayList<>()));
        newPosts.add(new Post("title 3", "description 3", "author 3", "", new ArrayList<>()));
        customAdapter.addAll(newPosts);

        return view;
    }

    public class CustomAdapter extends ArrayAdapter<Post> {
        List<Post> posts;
        int resource;
        Context context;
        public CustomAdapter(Context context, ArrayList<Post> users) {
            super(context, 0, users);
        }
//        public CustomAdapter(@NonNull Context context, int resource, List<Post> posts) {
//            super(context, resource);
//            this.resource = resource;
//            this.context = context;
//            this.posts = posts;
//        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Post post = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.for_you_posts, parent, false);
            }
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);
            TextView titleView =  (TextView) convertView.findViewById(R.id.post_title);
            TextView descpView =  (TextView) convertView.findViewById(R.id.post_description);
            TextView authorView = (TextView)  convertView.findViewById(R.id.author_name);

            titleView.setText(post.getTitle());
            descpView.setText(post.getDescription());
            authorView.setText(post.getAuthor());
            return  convertView;
        }
    }
}