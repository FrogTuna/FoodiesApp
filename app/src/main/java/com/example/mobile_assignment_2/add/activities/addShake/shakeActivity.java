package com.example.mobile_assignment_2.add.activities.addShake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.add.activities.addFriendList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class shakeActivity extends AppCompatActivity{
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    /* Update firebase Vars */
    private DatabaseReference mDatabase;
    private String userID;
    private ArrayList userInfosArrayList;

    /* Time counter */
    private long start;

    private boolean runOnce;

    private long TIME_ELAPSED = 10 * 1000; // ms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addshake);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /* Initialize time Vars */
        start = System.currentTimeMillis();;


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        /* Get DB reference */
        mDatabase = FirebaseDatabase.getInstance().getReference();



    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            /* Count 10s after last shaken */

//            if(System.currentTimeMillis() - start > TIME_ELAPSED) {
//                System.out.println("[start] " + start);
//
//                updateShakenInfo(true);
//            }

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));

//            Log.w("AccelCurrent:",  String.valueOf(mAccelCurrent));
//            Log.w("AccelCurrent:",  String.valueOf(x + " " + y + " " + z));


            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
//            System.out.println("[Shake] " + mAccel);
            if (mAccel > 9.5) {

                Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                /* Update shake flag on firebase */
                start = System.currentTimeMillis();

                Log.w("KeYANG",  String.valueOf(mAccel));
                updateShakenInfo(false); // should  change to false
                Thread thread = new Thread(new newThread());
                thread.start();

                runOnce = true;
                mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot user : snapshot.getChildren()) {
                            // TODO: handle the post

//                            Log.w("KeYANG",  "DB changed!");
                            if(String.valueOf(user.child("hasShaken").getValue()).equals("true")
                              && !user.getKey().equals(userID) && runOnce){

                                runOnce=false;
                                Log.w("KeYANG",  "find someone shake!");
                                Log.d("KeYANG" , String.valueOf(System.currentTimeMillis()));
                                Intent intent = new Intent(getApplicationContext(), addFriendList.class);
//                                getShakenUsersID(intent);

                                String key = user.getKey();
                                HashMap<String, String> userInfoHashMap = new HashMap<>();
                                Log.d("KeYANG" , key);
                                userInfoHashMap.put("ID", key);
                                userInfoHashMap.put("username", (String)user.child("username").getValue());
                                userInfoHashMap.put("imageUrl", (String)user.child("imageUrl").getValue());
                                userInfosArrayList = new ArrayList();
                                userInfosArrayList.add(userInfoHashMap);

                                System.out.println("[arr] " + userInfosArrayList);
                                intent.putExtra("userInfosArrayList", userInfosArrayList);
                                intent.putExtra("currentUser",userID);

                                Log.w("KeYANG",  "start to redirect page");
                                startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }


        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public class newThread implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.w("thread: ",  "weak up! set false");
            updateShakenInfo(true);
        }
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    protected void updateShakenInfo(Boolean tenSeconds) {

//        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = mDatabase.child("Users").child(userID).child("hasShaken").getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        if(tenSeconds) {
            childUpdates.put("/Users/" + userID + "/" + key, false);
            mDatabase.updateChildren(childUpdates);
        }
        else {
            childUpdates.put("/Users/" + userID + "/" + key, true);
            mDatabase.updateChildren(childUpdates);
//            Intent intent = new Intent(this, addFriendList.class);
//            userInfosArrayList = new ArrayList();
//            getShakenUsersID(intent);

        }

    }
    protected void getShakenUsersID(Intent intent) {


        Query databaseReference = mDatabase.child("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userInfosArrayList.clear();
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                        String key = userSnapshot.getKey();

                        if((Boolean)userSnapshot.child("hasShaken").getValue() && (!key.equals(userID))) {
                            HashMap<String, String> userInfoHashMap = new HashMap<>();
                            Log.d("selected User:" , key);
                            userInfoHashMap.put("ID", key);
                            userInfoHashMap.put("username", (String)userSnapshot.child("username").getValue());
                            userInfoHashMap.put("imageUrl", (String)userSnapshot.child("imageUrl").getValue());
                            userInfosArrayList.add(userInfoHashMap);
                        }
                    }
                    System.out.println("[arr] " + userInfosArrayList);
                    intent.putExtra("userInfosArrayList", userInfosArrayList);
                    intent.putExtra("currentUser",userID);
                    startActivity(intent);
//                    try {
//                        Thread.sleep(60);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

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