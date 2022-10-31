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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.example.mobile_assignment_2.home.ForYouFragment;
//import com.example.mobile_assignment_2.home.Post;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CommunityDetail extends AppCompatActivity {

    private TextView comNameView;
    private View imgView;
    private ListView listView_event, listView_com;
//    private ArrayList<Event> eventList;
    String comName, cid;
    FirebaseAuth EventAuth;
    FirebaseUser curUser;
    DatabaseReference eventRef;
    private ArrayList<Event> eventLists;
    private Button eventBtn;
    public static final String STATE_PAUSED = "join";
    public static final String STATE_UNPAUSED = "leave";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        eventLists = new ArrayList<>();
        EventAuth = FirebaseAuth.getInstance();
        curUser = EventAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        eventRef = FirebaseDatabase.getInstance().getReference("Event");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        comName = intent.getStringExtra("communityName");
        cid = intent.getStringExtra("cid");

        ImageButton btn = (ImageButton)findViewById(R.id.add_event);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommunityDetail.this, AddEvent.class);
                i.putExtra("cid", cid);
                i.putExtra("communityName", comName);
                startActivity(i);
            }
        });
        comNameView = findViewById(R.id.communityName);
        comNameView.setText(comName);

        ArrayList<Event> events = new ArrayList<>();
        CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), events);
        listView_event = findViewById(R.id.event_list);
//        listView_event.clear();
        listView_event.setAdapter(null);

        listView_event.setAdapter(customAdapter);
        ArrayList<String> peoList = new ArrayList<>();

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventLists = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    Log.e("loadEvent", String.valueOf(event));
                    eventLists.add(event);
                    Log.e("loadEvent", String.valueOf(eventLists.size()));
                }
                customAdapter.addAll(eventLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        peoList.add("1234556");
//        peoList.add("7498231");
//        newEvents.add(new Event("123456", "1", "123456788", "ww", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));
//        newEvents.add(new Event("123456", "1", "123456788", "ww", "Happy Thursday", "Mon Nov 31", "03:45", "University", 19, peoList));
//        newEvents.add(new Event("123456", "1", "123456788", "qq", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));
//        newEvents.add(new Event("123456", "1", "123456788", "tt", "Happy Friday", "Mon Oct 31", "02:45", "University", 19, peoList));

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
            TextView dateView = (TextView) convertView.findViewById(R.id.date_item);
            TextView timeView = (TextView) convertView.findViewById(R.id.time_item);
            TextView placeView = (TextView) convertView.findViewById(R.id.place_item);
            TextView authorView = (TextView) convertView.findViewById(R.id.event_organizer);
            TextView peoNumView = (TextView)  convertView.findViewById(R.id.event_people_number);

            // get event join/leave button
            eventBtn = convertView.findViewById(R.id.join_event_btn);
            eventBtn.setTag(STATE_PAUSED);
            eventView.setText(event.getEventName());
            dateView.setText(event.getEventDate());
            timeView.setText(event.getEvenTime());
            placeView.setText(event.getEventLocation());

            authorView.setText(event.getUserName());
            peoNumView.setText(String.valueOf(event.getPeopleNum()));

            eventBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    final String status = (String) view.getTag();
//                    switch (status) {
//                        eventBtn.setText("join");
//                        view.setTag(STATE_PAUSED);
//                    }

                    event.setStatus("leave");
                    eventBtn.setText(event.getStatus());
                }
            });

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