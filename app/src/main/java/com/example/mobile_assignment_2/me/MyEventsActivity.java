package com.example.mobile_assignment_2.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.community.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyEventsActivity extends AppCompatActivity  {

    private TextView comNameView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseAuth EventAuth;
    FirebaseUser curUser;
    DatabaseReference eventRef;
    DatabaseReference userRef;
    private ArrayList<Event> eventLists = new ArrayList<>();
    private Button eventBtn;


    RecyclerView eventRecyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_eventlists);
        eventLists = new ArrayList<>();
        EventAuth = FirebaseAuth.getInstance();
        curUser = EventAuth.getCurrentUser();
        setTitle("Events");

        eventRef = FirebaseDatabase.getInstance().getReference("Event");
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        // get the layout and event list

        eventRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventLists.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event.getUid().equals(currentUser.getUid())) {
                        eventLists.add(event);
                    }
                    Log.d("loadEvent", String.valueOf(eventLists.size()));
                }
                Collections.reverse(eventLists);

                eventRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                eventRecyclerView.setLayoutManager(linearLayoutManager);
                MyEventAdapter eventAdapter = new MyEventAdapter(eventLists);
                eventRecyclerView.setAdapter(eventAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



    public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.ViewHolder> {

        private ArrayList<Event> events = new ArrayList<>();
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameView;
            TextView dateView;
            TextView timeView;
            TextView placeView;
            TextView authorView;
            TextView peoNumView;
            ImageView profileView;
            Button joinBtn;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameView = (TextView) itemView.findViewById(R.id.event_name);
                dateView = (TextView) itemView.findViewById(R.id.date_item);
                timeView = (TextView) itemView.findViewById(R.id.time_item);
                placeView = (TextView) itemView.findViewById(R.id.place_item);
                authorView = (TextView) itemView.findViewById(R.id.event_organizer);
                peoNumView = (TextView)  itemView.findViewById(R.id.event_people_number);
                profileView = (ImageView) itemView.findViewById(R.id.profile_image);
                joinBtn = (Button) itemView.findViewById(R.id.join_event_btn);
            }
        }
        public MyEventAdapter(ArrayList<Event> events) {this.events = events;}
        @NonNull
        @Override
        public MyEventsActivity.MyEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_event, viewGroup, false);
            return new MyEventsActivity.MyEventAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyEventAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

            String eid = events.get(position).getEid();

            HashMap<String, String> peopleList;
            if (events.get(position).getPeopleList() != null) {
                peopleList = events.get(position).getPeopleList();
            } else {
                peopleList = new HashMap<>();
            }
            viewHolder.nameView.setText(events.get(position).getEventName());
            viewHolder.dateView.setText(events.get(position).getEventDate());
            viewHolder.timeView.setText(events.get(position).getEvenTime());
            viewHolder.placeView.setText(events.get(position).getEventLocation());
            viewHolder.authorView.setText(events.get(position).getUserName());
            int curPeoNum = peopleList.size();
            int maxPeoNum = events.get(position).getPeopleNum();
            int waitPeoNum = maxPeoNum-curPeoNum;
            // viewHolder.peoNumView.setText(String.valueOf(events.get(position).getPeopleNum()));
            viewHolder.peoNumView.setText(String.valueOf(waitPeoNum));

            String uid = events.get(position).getUid();
            firebaseDatabase.getReference().child("Users").child(uid).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error in fetching data", task.getException());
                    } else {
                        String profileImageUrl = task.getResult().getValue(String.class);
                        // Download image from URL and set to imageView
                        Picasso.with(getBaseContext()).load(profileImageUrl).fit().centerCrop().into(viewHolder.profileView);
                    }
                }
            });
            viewHolder.joinBtn.setText("JOINED");

        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
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
}