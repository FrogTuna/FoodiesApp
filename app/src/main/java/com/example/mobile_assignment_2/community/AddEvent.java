package com.example.mobile_assignment_2.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobile_assignment_2.Comment;
import com.example.mobile_assignment_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddEvent extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private TextInputEditText eNameView, eLocView, ePeopleNum;
    private Button dateBtn, timeBtn, saveBtn, postBtn;
    private String format="";
    FirebaseAuth EventAuth;
    FirebaseUser curUser;
    DatabaseReference eventRef;
    private String eventName, eventDate, eventTime, eventLocation;
    private int eventPeoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EventAuth = FirebaseAuth.getInstance();
        curUser = EventAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference("Users");
        eventRef = firebaseDatabase.getReference("Event").push();

        Intent intent = getIntent();
        String comName = intent.getStringExtra("communityName");
        String cid = intent.getStringExtra("cid");

        // init Date and Time to Now
        initDatePicker();
        initTimePicker();

        // select date and time data
        dateBtn = findViewById(R.id.calendar_select);
        dateBtn.setText(getTodaysDate());
        timeBtn = findViewById(R.id.time_select);
        timeBtn.setText(getNowTime());

        // click save and post to new page
        // saveBtn = findViewById(R.id.event_save);
        postBtn = findViewById(R.id.event_post);

        // set textView
        eNameView = findViewById(R.id.event_name);
        eLocView = findViewById(R.id.event_location);
        ePeopleNum = findViewById(R.id.event_people_num);


        postBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                eventName = eNameView.getText().toString();
                eventDate = String.valueOf(dateBtn.getText());
                eventTime = String.valueOf(timeBtn.getText());
                eventLocation = eLocView.getText().toString();
                eventPeoNum = Integer.parseInt(ePeopleNum.getText().toString());
                String eid = eventRef.getKey();
                HashMap<String, String> peoList = new HashMap<>();

//                String td = eventDate.concat(eventTime);
//                Log.e("readEvent", td);

                usersRef.child(curUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error in fetching data", task.getException());
                        } else {
                            Log.e("firebase", "Success in fetching data", task.getException());
                            String userName = task.getResult().getValue(String.class);
                            String uid = curUser.getUid();
                            eventRef.child(eid).child("peopleList").getKey();
//                            peoList.push();
                            String status =  "join";
                            Event event = new Event(cid, eid, uid, userName, eventName, eventDate, eventTime, eventLocation, eventPeoNum, peoList, status);
                            eventRef.setValue(event);
//                            AddEvent.this.finish();
                        }
                    }
                });

                 startActivity(new Intent(AddEvent.this, CommunityDetail.class));
            }
        });

    }

//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.event_save:
//                // do your code
//                startActivity(new Intent(AddEvent.this, CommunityDetail.class));
//                break;
//            case R.id.event_post:
//                // do your code
//                startActivity(new Intent(AddEvent.this, CommunityDetail.class));
//                break;
//            default:
//                break;
//        }
//    }

    private String getNowTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        return makeTimeString(hour, min);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                dateBtn.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this,style, dateSetListener,year, month, day);
    }
    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = makeTimeString(hour, minute);
                timeBtn.setText(time);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, timeSetListener , hour, min, false);
    }

    private String makeTimeString(int hour, int minute) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        String time_str = String.valueOf(new StringBuilder().append(hour).append(" : ").append(minute).append(" ").append(format));
        return time_str;
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1) {
            return "JAN";
        }
        if(month == 2) {
            return "FEB";
        }
        if(month == 3) {
            return "MAR";
        }
        if(month == 4) {
            return "APR";
        }
        if(month == 5) {
            return "MAY";
        }
        if(month == 6) {
            return "JUN";
        }
        if(month == 7) {
            return "JUL";
        }
        if(month == 8) {
            return "AUG";
        }
        if(month == 9) {
            return "SEP";
        }
        if(month == 10) {
            return "OCT";
        }
        if(month == 11) {
            return "NOV";
        }
        if(month == 12) {
            return "DEC";
        }
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    public void openTimePicker(View view) {
        timePickerDialog.show();
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