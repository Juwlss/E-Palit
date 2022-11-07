package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Model.User;
import com.example.barter10.SignInSignUp.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class signupotp extends AppCompatActivity {
    private EditText inputsgn1, inputsgn2, inputsgn3, inputsgn4, inputsgn5, inputsgn6;
    private  String verificationsgn;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    private PhoneAuthCredential phoneAuthCredential;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupotp);
        TextView textmobile = findViewById(R.id.txtsgnotp);
        textmobile.setText(String.format(
                "+63-%s", getIntent().getStringExtra("phodb")
        ));
        inputsgn1 = findViewById(R.id.pensgn1);
        inputsgn2 = findViewById(R.id.pensgn2);
        inputsgn3 = findViewById(R.id.pensgn3);
        inputsgn4 = findViewById(R.id.pensgn4);
        inputsgn5 = findViewById(R.id.pensgn5);
        inputsgn6 = findViewById(R.id.pensgn6);
        firebaseAuth = FirebaseAuth.getInstance();
        setsgnotp();

        ProgressBar progressotpBar = findViewById(R.id.prgbarsgnotp);
        Button buttonsgnotp = findViewById(R.id.button_sgn);

        verificationsgn = getIntent().getStringExtra("verificationsgn");



        database = FirebaseDatabase.getInstance().getReference("users");
        buttonsgnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputsgn1.getText().toString().trim().isEmpty()
                        || inputsgn2.getText().toString().trim().isEmpty()
                        || inputsgn3.getText().toString().trim().isEmpty()
                        || inputsgn4.getText().toString().trim().isEmpty()
                        || inputsgn5.getText().toString().trim().isEmpty()
                        || inputsgn6.getText().toString().trim().isEmpty()){
                    Toast.makeText(signupotp.this,"Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inputsgn1.getText().toString()+
                        inputsgn2.getText().toString()+
                        inputsgn3.getText().toString()+
                        inputsgn4.getText().toString()+
                        inputsgn5.getText().toString()+
                        inputsgn6.getText().toString();


                if(verificationsgn !=null){
                    progressotpBar.setVisibility(View.VISIBLE);
                    buttonsgnotp.setVisibility(View.INVISIBLE);
                    phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationsgn,
                            code
                    );




                    firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressotpBar.setVisibility(View.GONE);
                            buttonsgnotp.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                addUser();
                                Toast.makeText(signupotp.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(signupotp.this,"The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                Toast.makeText(signupotp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });
        findViewById(R.id.resendsgn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+63" + getIntent().getStringExtra("phodb"),
                        60,
                        TimeUnit.SECONDS,
                        signupotp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){

                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e){

                                Toast.makeText(signupotp.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationsgn, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
                                verificationsgn = newverificationsgn;
                                Toast.makeText(signupotp.this,"OTP has been sent",Toast.LENGTH_SHORT).show();

                            }

                        }
                );



            }
        });



    }

    private void addUser(){
        String full = getIntent().getStringExtra("fulldb");
        String userz = getIntent().getStringExtra("userdb").trim();
        String pass = getIntent().getStringExtra("passdb");
        String pho = getIntent().getStringExtra("phodb");
        String pic = "gs://e-palit.appspot.com/PostItem/Default/Vector (1).png";

        String username = userz+"@epalit.com";

        firebaseAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(FirebaseAuth.getInstance().getUid(), full,pic,userz,pass,pho);
                    database.child(FirebaseAuth.getInstance().getUid()).setValue(user);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(signupotp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void  setsgnotp(){
        inputsgn1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
