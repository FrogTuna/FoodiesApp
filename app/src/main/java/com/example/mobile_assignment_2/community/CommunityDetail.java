package com.example.mobile_assignment_2.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobile_assignment_2.R;
//import com.example.mobile_assignment_2.home.ForYouFragment;
//import com.example.mobile_assignment_2.home.Post;

import java.util.ArrayList;


public class CommunityDetail extends Fragment {

    private ListView listView_event, listView_com;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_com_detail, container, false);

        ArrayList<Event> events = new ArrayList<>();
//        ArrayList<Post> newPosts = new ArrayList<>();
        CustomAdapter customAdapter = new CustomAdapter(getContext(), events);
        listView_event = (ListView) view.findViewById(R.id.event_list);
        listView_event.setAdapter(customAdapter);
        ArrayList<Event> newEvents = new ArrayList<>();
//        ArrayList<Post> com_posts = new ArrayList<>();
        newEvents.add(new Event("Sun Oct 23", "university", "author 1"));
        newEvents.add(new Event("Sun Oct 24", "home", "author 2"));
        newEvents.add(new Event("Sun Oct 25", "bridge", "author 3"));

//        com_posts.add(new Post("title 1", "description 1", "author 1"));
//        com_posts.add(new Post("title 2", "description 2", "author 2"));
//        com_posts.add(new Post("title 3", "description 3", "author 3"));

        customAdapter.addAll(newEvents);

        setUpToolbar(view);
        return view;
    }


    public class CustomAdapter extends ArrayAdapter<Event> {
        public CustomAdapter(@NonNull Context context, ArrayList<Event> events) {
            super(context, 0, events);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            Event event = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_com_detail, parent, false);
            }
            TextView timeView = (TextView) convertView.findViewById(R.id.date_item);
            TextView placeView = (TextView) convertView.findViewById(R.id.place_item);
            TextView authorView = (TextView) convertView.findViewById(R.id.event_organizer);

            timeView.setText(event.getEvent_time());
            placeView.setText(event.getEvent_location());
            authorView.setText(event.getAuthor());
            return convertView;
        }
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.com_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.com_select_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

}