package com.example.mobile_assignment_2.community;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
//import com.example.mobile_assignment_2.home.ForYouFragment;
//import com.example.mobile_assignment_2.home.Post;

import java.util.ArrayList;
import java.util.List;


public class MyCommunityFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ListView listView;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_community, container, false);


        List<Communitypost> posts = new ArrayList<>();
        getcommPosts(posts);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), posts);
        listView = view.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
//        customAdapter.addAll(getcommPosts());

        return view;
    }

    public class CustomAdapter extends BaseAdapter {
        private List<Communitypost> musers = null;
        private Context mcontext = null;


        public CustomAdapter(Context context, List<Communitypost> users) {
            musers = users;
            mcontext = context;
//            super(context, 0, users);
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
//            Communitypost post = getItem(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.community_leave_posts, null);
                holder.image = convertView.findViewById(R.id.communityImg);
                holder.title = convertView.findViewById(R.id.community_name);
                holder.delete = convertView.findViewById(R.id.btn_leave);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        musers.remove(position);
                        notifyDataSetChanged();

                    }
                });
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

//            TextView titleView =  (TextView) convertView.findViewById(R.id.community_name);
//            TextView descpView =  (TextView) convertView.findViewById(R.id.community_description);
//            ImageView commImg =  convertView.findViewById(R.id.communityImg);
//            Button delBtn = convertView.findViewById(R.id.btn_leave);



//            titleView.setText(post.getCommName());
//            descpView.setText(post.getDescription());
//            commImg.setBackgroundResource(post.getCommImage());
            holder.image.setBackgroundResource(musers.get(position).getCommImage());
            holder.title.setText(musers.get(position).getCommName());
            return  convertView;
        }
    }

    // To generate an array of posts example
    private List<Communitypost> getcommPosts(List<Communitypost> newPosts) {
//        ArrayList<Communitypost> newPosts = new ArrayList<>();
        newPosts.add(new Communitypost(R.drawable.food, "commName1"));
        newPosts.add(new Communitypost(R.drawable.food2, "commName2"));
        newPosts.add(new Communitypost(R.drawable.food, "commName3"));
        newPosts.add(new Communitypost(R.drawable.food2, "commName4"));
        return newPosts;
    }


}
