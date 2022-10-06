package com.example.barter10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class activity_signup_new extends AppCompatActivity {

    int passvis;
    EditText txtPassword;
    ImageButton visOff,btnGoogle,btnFacebook;
    Button signUp;
    TextView signNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);

        txtPassword = findViewById(R.id.txtPassword);
        visOff = findViewById(R.id.visionOff);

        visOff.setVisibility(View.GONE);
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtPassword.getText().length() > 0){
                    visOff.setVisibility(View.VISIBLE);
                    passvis = 1;
                }else{
                    passvis = 0;
                    visOff.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//checking the password when toggling
        visOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (passvis){
                    case 0:
                        visOff.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis = 1;
                        break;

                    case 1:
                        visOff.setImageResource(R.drawable.ic_baseline_visibility_24);
                        txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis = 0;
                        break;
                }
            }
        });


        //Button sign up

        signUp = findViewById(R.id.btnSignup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_signup_new.this, "Sign Up", Toast.LENGTH_SHORT).show();
            }
        });

        //Button google

        btnGoogle = findViewById(R.id.btnGoogle);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_signup_new.this, "google", Toast.LENGTH_SHORT).show();
            }
        });

        //Button Facebook

        btnFacebook = findViewById(R.id.btnFacebook);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_signup_new.this, "Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        //Button Sign in now
        signNow = findViewById(R.id.lblSignnow);

        signNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_signup_new.this, "Sign in now", Toast.LENGTH_SHORT).show();
            }
        });



    }
}