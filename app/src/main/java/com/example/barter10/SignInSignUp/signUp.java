package com.example.barter10.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.barter10.Home;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.example.barter10.signupotp;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class signUp extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    int passvis=1,passvis2=1;
    private EditText fullname,username, password, conpassword,phoNo;
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


        fullname = findViewById(R.id.txtFullName);
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

                String str_fullname = fullname.getText().toString().trim();
                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();
                String str_conpass = conpassword.getText().toString().trim();


                String checker = "^[9][0-9]{9}$";


                if(phoNo.getText().toString().trim().isEmpty()){
                    Toast.makeText(signUp.this,"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_conpass) || TextUtils.isEmpty(str_fullname)) {
                    Toast.makeText(signUp.this, "Please filled all the requirements", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(signUp.this, "Password must have more than 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!str_password.equals(str_conpass)){
                    conpassword.setError("Password does not match");
                } else if (str_username.length() < 4) {
                    Toast.makeText(signUp.this, "email is too short", Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(getApplicationContext(), signupotp.class);
                                    intent.putExtra("phodb", phoNo.getText().toString());
                                    intent.putExtra("verificationsgn", verification);
                                    intent.putExtra("fulldb",fullname.getText().toString());
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
                setContentView(R.layout.layout_terms);
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.layout_privacypolicy);
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