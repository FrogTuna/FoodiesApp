package com.example.mobile_assignment_2.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.Calendar;
import java.util.Date;


public class CommunityDetail extends AppCompatActivity {

    private TextView comNameView;
    private View imgView;
    private ListView listView_event, listView_com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        String comName = intent.getStringExtra("communityName");
//        String comImg = intent.getStringExtra("communityImg");

        comNameView = findViewById(R.id.communityName);
//        imgView = findViewById(R)
        comNameView.setText(comName);
//        imgView.setText(imgView)
//        comListView = findViewById(R.id.fragment_com_detail);
        ArrayList<Event> events = new ArrayList<>();
        CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), events);
        listView_event = findViewById(R.id.event_list);
        listView_event.setAdapter(customAdapter);
        ArrayList<Event> newEvents = new ArrayList<>();
//        ArrayList<Post> com_posts = new ArrayList<>();
        newEvents.add(new Event("aaa", "Sun Oct 23", "university", "author 1"));
        newEvents.add(new Event("bbb", "Sun Oct 24", "home", "author 2"));
        newEvents.add(new Event("ccc", "Sun Oct 25", "bridge", "author 3"));
        newEvents.add(new Event("ddd", "Sun Oct 26", "acvne", "author 4"));
//        com_posts.add(new Post("title 1", "description 1", "author 1"));
//        com_posts.add(new Post("title 2", "description 2", "author 2"));
//        com_posts.add(new Post("title 3", "description 3", "author 3"));

        customAdapter.addAll(newEvents);
//      setUpToolbar(comNameView);
//        return comNameView;
    }

    public class CustomAdapter extends ArrayAdapter<Event> {

        public CustomAdapter(@NonNull Context context, ArrayList<Event> events) {
            super(context, 0, events);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Event event = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_event, parent, false);
            }
            TextView eventView = (TextView) convertView.findViewById(R.id.event_name);
            TextView timeView = (TextView) convertView.findViewById(R.id.date_item);
            TextView placeView = (TextView) convertView.findViewById(R.id.place_item);
            TextView authorView = (TextView) convertView.findViewById(R.id.event_organizer);

            eventView.setText(event.getEvent_name());
            timeView.setText(event.getEvent_time());
            placeView.setText(event.getEvent_location());
            authorView.setText(event.getAuthor());
            return convertView;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    private void setUpToolbar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.com_bar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.setSupportActionBar(toolbar);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        getMenuInflater().inflate(R.menu.com_select_menu, menu);
//        return true;
////        menuInflater.inflate(R.menu.com_select_menu, menu);
////        super.onCreateOptionsMenu(menu, menuInflater);
//    }
}



//        listView_event = findViewById(R.id.event_linear);
//
//        Date currentTime = Calendar.getInstance().getTime();
//        for (int i = 0; i < 3; i++) {
//            View view = LayoutInflater.from(this).inflate(R.layout.activity_event, null);
//            TextView organizer = view.findViewById(R.id.event_organizer);
//            TextView date_item = view.findViewById(R.id.date_item);
//            TextView place_item = view.findViewById(R.id.place_item);
//            organizer.setText(new String(String.valueOf(i)));
//            date_item.setText(new String(String.valueOf(currentTime)));
//            place_item.setText("Will be hold at: " + new String(String.valueOf(i)));
//            listView_event.addView(view);
//        }
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.com_bar);
//        setSupportActionBar(myToolbar);

//public class CommunityDetail extends Fragment {
//
//    private ListView listView_event, listView_com;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    public View onCreateView(
//            @NonNull LayoutInflater inflater,
//            ViewGroup container,
//            Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_com_detail, container, false);
//
//        ArrayList<Event> events = new ArrayList<>();
////        ArrayList<Post> newPosts = new ArrayList<>();
//        CustomAdapter customAdapter = new CustomAdapter(getContext(), events);
//        listView_event = (ListView) view.findViewById(R.id.event_list);
//        listView_event.setAdapter(customAdapter);
//        ArrayList<Event> newEvents = new ArrayList<>();
////        ArrayList<Post> com_posts = new ArrayList<>();
//        newEvents.add(new Event("Sun Oct 23", "university", "author 1"));
//        newEvents.add(new Event("Sun Oct 24", "home", "author 2"));
//        newEvents.add(new Event("Sun Oct 25", "bridge", "author 3"));
//
////        com_posts.add(new Post("title 1", "description 1", "author 1"));
////        com_posts.add(new Post("title 2", "description 2", "author 2"));
////        com_posts.add(new Post("title 3", "description 3", "author 3"));
//
//        customAdapter.addAll(newEvents);
//
//        setUpToolbar(view);
//        return view;
//    }
//