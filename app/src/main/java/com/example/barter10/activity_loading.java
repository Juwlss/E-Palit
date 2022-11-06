package com.example.barter10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.barter10.SignInSignUp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class activity_loading extends AppCompatActivity {

    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        firebaseAuth = FirebaseAuth.getInstance();


        currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(activity_loading.this, Home.class));
            finish();
        }else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //auto signed in loading screen
                    startActivity(new Intent(activity_loading.this, MainActivity.class));
                    finish();

                }
            }, 2000);
        }

    }
}