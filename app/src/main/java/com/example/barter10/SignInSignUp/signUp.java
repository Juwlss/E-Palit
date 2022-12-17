package com.example.barter10.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.barter10.R;
import com.example.barter10.TermsAndPolicy.Policy;
import com.example.barter10.TermsAndPolicy.Terms;
import com.example.barter10.signupotp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class signUp extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String usernameChecker;

    int passvis=1,passvis2=1;
    private EditText email,username, password, conpassword,phoNo;
    private ImageButton visOff;
    private Button signUp;
    private TextView signNow,terms,policy;
    private DatabaseReference databaseReference;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);

        firebaseAuth = FirebaseAuth.getInstance();
        visOff = findViewById(R.id.visionOff);

        ImageButton passtoggle = findViewById(R.id.visionOff2);


        email = findViewById(R.id.txtEmail);
        username = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPassword);
        conpassword = findViewById(R.id.txtConPassword);
        phoNo = findViewById(R.id.txtPhone);


        checkBox = findViewById(R.id.cb_privacy);

        terms = findViewById(R.id.tv_Terms);
        policy = findViewById(R.id.tv_policy);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        //checking the password when toggling
        visOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (passvis) {
                    case 0:
                        visOff.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis = 1;
                        break;

                    case 1:
                        visOff.setImageResource(R.drawable.ic_baseline_visibility_24);
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis = 0;
                        break;
                }
            }
        });

        passtoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (passvis2) {
                    case 0:
                        passtoggle.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                        conpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis2 = 1;
                        break;

                    case 1:
                        passtoggle.setImageResource(R.drawable.ic_baseline_visibility_24);
                        conpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis2 = 0;
                        break;
                }
            }
        });

        //Button sign up

        signUp = findViewById(R.id.btnSignup);
        final ProgressBar progressBar = findViewById(R.id.prgbarsgn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_email = email.getText().toString().trim();
                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();
                String str_conpass = conpassword.getText().toString().trim();

                String checker1 = "^[a-zA-Z0-9]$";
                String checker = "[^a-zA-Z0-9]*"; //containing alphanumeric characters
//                a-zA-Z0-9

//                ^                 # start-of-string
//                        (?=.*[0-9])       # a digit must occur at least once
//                        (?=.*[a-z])       # a lower case letter must occur at least once
//                        (?=.*[A-Z])       # an upper case letter must occur at least once
//                        (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
//                        (?=\\S+$)          # no whitespace allowed in the entire string
//                        .{4,}             # anything, at least six places though
//                $                 # end-of-string


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            usernameChecker = dataSnapshot.child("username").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                if(TextUtils.isEmpty(str_email)){
                    email.setError("Please enter your email");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
                    email.setError("Please enter a valid email");
                }else if(TextUtils.isEmpty(str_username)){
                    username.setError("Please enter your username");
                } else if(TextUtils.isEmpty(str_password)){
                    password.setError("Please enter your password");
                } else if(TextUtils.isEmpty(str_conpass)){
                    conpassword.setError("Please enter your confirm password");
                } else if(TextUtils.isEmpty(phoNo.getText().toString().trim())){
                    phoNo.setError("Please enter your phone number");
                } else if (str_password.length() < 6) {
                    password.setError("Password must have more than 6 characters");
                } else if (str_password.matches("[^0-9]*")) {
                    password.setError("Password must contain numbers");
                } else if (str_password.matches("[^a-zA-Z]*")) {
                    password.setError("Password must contain letters");
                } else if (!str_password.equals(str_conpass)){
                    conpassword.setError("Password does not match");
                } else if (str_username.length() < 8) {
                    Toast.makeText(signUp.this, "username is too short", Toast.LENGTH_SHORT).show();
                } else if(!checkBox.isChecked()){
                    Toast.makeText(signUp.this, "You must agree to Sign up", Toast.LENGTH_SHORT).show();
                } else{

                    progressBar.setVisibility(View.VISIBLE);
                    signUp.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+63" + phoNo.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            signUp.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){
                                    progressBar.setVisibility(View.GONE);
                                    signUp.setVisibility(View.INVISIBLE);
                                }
                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e){
                                    progressBar.setVisibility(View.GONE);
                                    signUp.setVisibility(View.VISIBLE);
                                    Toast.makeText(signUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String verification, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
                                    super.onCodeSent(verification, forceResendingToken);
                                    progressBar.setVisibility(View.GONE);
                                    signUp.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(signUp.this, signupotp.class);
                                    intent.putExtra("phodb", phoNo.getText().toString());
                                    intent.putExtra("verificationsgn", verification);
                                    intent.putExtra("fulldb",email.getText().toString());
                                    intent.putExtra("userdb",username.getText().toString());
                                    intent.putExtra("passdb",password.getText().toString());
                                    intent.putExtra("userid", FirebaseAuth.getInstance().getUid());



                                    startActivity(intent);

                                }

                            }
                    );

                }


            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp.this, Terms.class));
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp.this, Policy.class));
            }
        });






        //Button Sign in now
        signNow = findViewById(R.id.lblSignnow);

        signNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp.this, MainActivity.class));
            }
        });


    }




}