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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
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
    DatabaseReference userRef;
    private ArrayList<Event> eventLists = new ArrayList<>();
    private Button eventBtn;
    EventAdapter eventAdapter;

    RecyclerView eventRecyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static final int STATE_PAUSED = 0;
    public static final int STATE_UNPAUSED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        eventLists = new ArrayList<>();
        EventAuth = FirebaseAuth.getInstance();
        curUser = EventAuth.getCurrentUser();

        eventRef = FirebaseDatabase.getInstance().getReference("Event");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

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
       // comNameView = findViewById(R.id.communityName);
        //comNameView.setText(comName);

        //listView_event = findViewById(R.id.event_list);
        ArrayList<String> peoList = new ArrayList<>();

        // get the layout and event list

        eventRecyclerView = (RecyclerView) findViewById(R.id.event_list);


        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventLists.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    Log.e("loadEvent", String.valueOf(event));
                    eventLists.add(event);
                    Log.e("loadEvent", String.valueOf(eventLists.size()));
                }
                // CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), eventLists);
                // listView_event.setAdapter(customAdapter);


                eventRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                eventRecyclerView.setLayoutManager(linearLayoutManager);
                EventAdapter eventAdapter = new EventAdapter(eventLists);
                eventRecyclerView.setAdapter(eventAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
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
        public EventAdapter(ArrayList<Event> events) {this.events = events; }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_event, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
            final int[] numPeople = {events.get(position).getPeopleNum()};
            Log.d("events", events.get(position).getEventName());
            String author = events.get(position).getUserName();
            String eid = events.get(position).getEid();
            ArrayList<String> peopleList = events.get(position).getPeopleList();

            viewHolder.nameView.setText(events.get(position).getEventName());
            viewHolder.dateView.setText(events.get(position).getEventDate());
            viewHolder.timeView.setText(events.get(position).getEvenTime());
            viewHolder.placeView.setText(events.get(position).getEventLocation());
            viewHolder.authorView.setText(events.get(position).getUserName());
            viewHolder.peoNumView.setText(String.valueOf(events.get(position).getPeopleNum()));

            String uid = events.get(position).getUid();
            Log.d("events","hi");
            firebaseDatabase.getReference().child("Users").child(uid).child("imageUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error in fetching data", task.getException());
                    }
                    else {
                        String profileImageUrl = task.getResult().getValue(String.class);
                        // Download image from URL and set to imageView
                        Picasso.with(getBaseContext()).load(profileImageUrl).fit().centerCrop().into(viewHolder.profileView);
                    }
                }
            });
            viewHolder.joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> eventJoinList = new ArrayList<>();
                    userRef.child(curUser.getUid()).child("eventJoinList").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            eventJoinList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                eventJoinList.add((String) dataSnapshot.getValue());
                            }
                            if(!eventJoinList.contains(eid)) {
                                viewHolder.joinBtn.setText("JOIN");
                                firebaseDatabase.getReference().child("Users").child(curUser.getUid()).child("eventJoinList").child(events.get(position).getEid());
                                // EventAuth.child("Users").child(curUser.getUid()).child("eventLists").child(events.get(getAdapterPosition()).getEid()).setValue("true");
                            } else {
                                viewHolder.joinBtn.setText("LEAVE");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });
        }

        @Override
        public int getItemCount() {
            Log.d("events", String.valueOf(events.size()));
            return events.size();
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

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

            eventView.setText(event.getEventName());
            dateView.setText(event.getEventDate());
            timeView.setText(event.getEvenTime());
            placeView.setText(event.getEventLocation());

            authorView.setText(event.getUserName());
            peoNumView.setText(String.valueOf(event.getPeopleNum()));

            //eventBtn.setTag("JOIN");
            eventBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String status = (String) eventBtn.getText();
                    //String status = (String) view.getTag();
                    String status = eventBtn.getText().toString();
                    switch (status) {
                        case "JOIN":
                            eventBtn.setText("LEAVE");
                            //view.setTag("LEAVE"); //pause
                            break;
                        case "LEAVE":
                            eventBtn.setText("JOIN");
                            //view.setTag("JOIN"); //pause
                            break;
                    }
                    Log.e("button", (String) eventBtn.getText());
                    notifyDataSetChanged();
//                    switch(status) {
//                        case "JOIN":
//                            eventBtn.setText("LEAVE");
//                            break;
//                        case "LEAVE":
//                            eventBtn.setText("JOIN");
//                        default:
//                            eventBtn.setText(status);
//                    }
                   // eventBtn.setText((String) view.getTag());

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