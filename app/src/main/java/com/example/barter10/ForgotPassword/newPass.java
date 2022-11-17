package com.example.barter10.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.barter10.Home;
import com.example.barter10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class newPass extends AppCompatActivity {

    int passvis;
    private String phones;
    private EditText newpass;
    private Button frgbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);

        frgbutton = (Button) findViewById(R.id.imageView7);
        newpass = findViewById(R.id.pass1);



        ImageButton passtoggle = findViewById(R.id.visoff);
        EditText pass1 = findViewById(R.id.pass1);
        ImageButton passtoggle2 = findViewById(R.id.visoff2);
        EditText pass2 = findViewById(R.id.pass2);
        passtoggle.setVisibility(View.GONE);
        passtoggle2.setVisibility(View.GONE);

        phones = getIntent().getStringExtra("mobile");

        frgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query checkphone = FirebaseDatabase.getInstance().getReference("users").orderByChild("userPhone").equalTo(phones);
                checkphone.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snap: snapshot.getChildren()){
                                if(pass1.getText().toString().trim().isEmpty()|| pass2.getText().toString().trim().isEmpty()){
                                    Toast.makeText(newPass.this,"Enter New Password", Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(newPass.this, checkphone.getRef().toString(),Toast.LENGTH_SHORT).show();
                                    checkphone.getRef().child(snap.getKey()).child("userpassword").setValue(newpass.getText().toString().trim());
                                }

                            }

                        }
                        else{
                            Toast.makeText(newPass.this,phones,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
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

}