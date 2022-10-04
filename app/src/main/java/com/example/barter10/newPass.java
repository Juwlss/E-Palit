package com.example.barter10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class newPass extends AppCompatActivity {

    int passvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);


        ImageButton passtoggle = findViewById(R.id.visoff);
        EditText pass1 = findViewById(R.id.pass1);
        ImageButton passtoggle2 = findViewById(R.id.visoff2);
        EditText pass2 = findViewById(R.id.pass2);
        passtoggle.setVisibility(View.GONE);
        passtoggle2.setVisibility(View.GONE);


//showing togglepassword when typing
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pass1.getText().length() > 0){
                    passtoggle.setVisibility(View.VISIBLE);
                    passvis = 1;
                }else{
                    passvis = 0;
                    passtoggle.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//checking the password when toggling
        passtoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (passvis){
                    case 0:
                        passtoggle.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                        pass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis = 1;
                        break;

                    case 1:
                        passtoggle.setImageResource(R.drawable.ic_baseline_visibility_24);
                        pass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis = 0;
                        break;
                }
            }
        });

        //showing togglepassword when typing
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pass2.getText().length() > 0){
                    passtoggle2.setVisibility(View.VISIBLE);
                    passvis = 1;
                }else{
                    passvis = 0;
                    passtoggle2.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//checking the password when toggling
        passtoggle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (passvis){
                    case 0:
                        passtoggle2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                        pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis = 1;
                        break;

                    case 1:
                        passtoggle2.setImageResource(R.drawable.ic_baseline_visibility_24);
                        pass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis = 0;
                        break;
                }
            }
        });
    }

    public void submitNPass(View view) {
        startActivity(new Intent(newPass.this, Home.class));
    }
}