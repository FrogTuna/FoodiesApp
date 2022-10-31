package com.example.mobile_assignment_2.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobile_assignment_2.R;
//import com.example.mobile_assignment_2.home.ForYouFragment;
//import com.example.mobile_assignment_2.home.Post;

import java.lang.reflect.Array;
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
        String cid = intent.getStringExtra("cid");
//        Log.e("communityName", comName);

//        String comImg = intent.getStringExtra("communityImg");

        ImageButton btn = (ImageButton)findViewById(R.id.add_event);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommunityDetail.this, AddEvent.class);
                startActivity(i);
            }
        });
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
        ArrayList<String> peoList = new ArrayList<>();
        peoList.add("1234556");
        peoList.add("7498231");
        newEvents.add(new Event("123456", "1", "123456788", "ww", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));
        newEvents.add(new Event("123456", "1", "123456788", "ww", "Happy Thursday", "Mon Nov 31", "03:45", "University", 19, peoList));
        newEvents.add(new Event("123456", "1", "123456788", "qq", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));
        newEvents.add(new Event("123456", "1", "123456788", "tt", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));
//        com_posts.add(new Post("title 1", "description 1", "author 1"));
//        com_posts.add(new Post("title 2", "description 2", "author 2"));
//        com_posts.add(new Post("title 3", "description 3", "author 3"));


        customAdapter.addAll(newEvents);

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

            eventView.setText(event.getEventName());
            timeView.setText(event.getEvenTime());
            placeView.setText(event.getEventLocation());
            authorView.setText(event.getUserName());
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
//        setSupportActionBar(toolbar);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.com_select_menu, menu);
//        return true;
//    }
}