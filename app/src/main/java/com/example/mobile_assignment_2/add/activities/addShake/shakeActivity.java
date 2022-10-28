package com.example.mobile_assignment_2.add.activities.addShake;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;

import java.util.*;

public class shakeActivity extends AppCompatActivity {



//    // Declaring sensorManager
//    // and acceleration constants
//    private SensorManager sensorManager = null;
//    private float acceleration = 0f;
//    private float currentAcceleration = 0f;
//    private float lastAcceleration = 0f;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Getting the Sensor Manager instance
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//        Objects.requireNonNull(sensorManager).registerListener(sensorListener,4000)
//
//
//        Objects.requireNonNull(sensorManager)!!
//            .registerListener(sensorListener, sensorManager!!
//            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
//
//        acceleration = 10f;
//        currentAcceleration = SensorManager.GRAVITY_EARTH;
//        lastAcceleration = SensorManager.GRAVITY_EARTH;
//    }
//
//    private SensorEventListener sensorListener
//
//
//
//    private val sensorListener: SensorEventListener = object : SensorEventListener {
//        override fun onSensorChanged(event: SensorEvent) {
//
//            // Fetching x,y,z values
//            val x = event.values[0]
//            val y = event.values[1]
//            val z = event.values[2]
//            lastAcceleration = currentAcceleration
//
//            // Getting current accelerations
//            // with the help of fetched x,y,z values
//            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
//            val delta: Float = currentAcceleration - lastAcceleration
//            acceleration = acceleration * 0.9f + delta
//
//            // Display a Toast message if
//            // acceleration value is over 12
//            if (acceleration > 12) {
//                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
//            }
//        }
//        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
//    }
//
//    @Override
//    protected void onResume() {
//        sensorManager.registerListener(sensorListener,1000)
//        super.onResume();
//    }
//
//    override fun onResume() {
//        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
//                Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
//        )
//        super.onResume()
//    }
//
//    override fun onPause() {
//        sensorManager!!.unregisterListener(sensorListener)
//        super.onPause()
//    }
}


