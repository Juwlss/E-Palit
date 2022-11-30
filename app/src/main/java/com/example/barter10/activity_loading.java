package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.barter10.SignInSignUp.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

            DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference("users");

            deleteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.child(FirebaseAuth.getInstance().getUid()).exists()){
                        firebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(activity_loading.this, "your account has been deleted", Toast.LENGTH_SHORT).show();
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
                        });
                    }else{

                        startActivity(new Intent(activity_loading.this, Home.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


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