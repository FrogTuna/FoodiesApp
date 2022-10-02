package com.example.mobile_assignment_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobile_assignment_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;

    Button loginBtn;
    Button registerBtn;
    TextView registerInLogin;
    TextView loginInRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //default page
        setContentView(R.layout.activity_login);
        buttonListener();

    }


    public void buttonListener(){

        loginBtn = findViewById(R.id.loginButton);
        registerInLogin = findViewById(R.id.registerInLogin);
        registerBtn = findViewById(R.id.registerButton);
        loginInRegister = findViewById(R.id.loginInRegister);

        //login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation();
            }
        });

//        //don't have an account
//        registerInLogin.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                loginIntent();
//            }
//        });
//
//        //register
//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerIntent();
//            }
//        });
//
//
//        //go back login interface from register
//        registerInLogin.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                registerIntent();
//            }
//        });

    }

    //link to register page
    public void loginIntent(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //link to login page
    public void registerIntent(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }



    //navigation bar - replace navigation items by id
    private void navigation(){

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.add:
                    replaceFragment(new AddFragment());
                    break;
                case R.id.community:
                    replaceFragment(new CommunityFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new MeFragment());
                    break;

            }

            return true;

        });

    }


    //replace navigation items by id
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }







}