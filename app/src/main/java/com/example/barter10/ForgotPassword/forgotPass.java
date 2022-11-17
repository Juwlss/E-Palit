package com.example.barter10.ForgotPassword;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.barter10.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class forgotPass extends AppCompatActivity {
    private EditText inputmobile;
    private Button buttonotp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        inputmobile = (EditText) findViewById(R.id.edit_phone);
        buttonotp = (Button) findViewById(R.id.btn_forgotpassword);
        progressBar = (ProgressBar) findViewById(R.id.prgbar);

        buttonotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = inputmobile.getText().toString().trim();
                final String comnumber = "" + number;

                Query checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("userPhone").equalTo(comnumber);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists()){
                            inputmobile.setError(null);
                            progressBar.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            buttonotp.setVisibility(View.INVISIBLE);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+63" + inputmobile.getText().toString(),
                                    60,
                                    TimeUnit.SECONDS,
                                    forgotPass.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){
                                            progressBar.setVisibility(View.GONE);
                                            buttonotp.setVisibility(View.VISIBLE);
                                        }
                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e){
                                            progressBar.setVisibility(View.GONE);
                                            buttonotp.setVisibility(View.VISIBLE);
                                            Toast.makeText(forgotPass.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verification, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
                                            super.onCodeSent(verification, forceResendingToken);
                                            progressBar.setVisibility(View.GONE);
                                            buttonotp.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(getApplicationContext(), otp.class);
                                            intent.putExtra("mobile", inputmobile.getText().toString());
                                            intent.putExtra("verificationid", verification);
                                            startActivity(intent);

                                        }

                                    }
                            );
                        }else{
                            progressBar.setVisibility(View.GONE);
                            inputmobile.setError("No user exist");
                            inputmobile.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if(inputmobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(forgotPass.this,"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });


    }
}