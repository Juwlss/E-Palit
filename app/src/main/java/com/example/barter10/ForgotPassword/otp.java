package com.example.barter10.ForgotPassword;

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

import com.example.barter10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp extends AppCompatActivity {
    private EditText input1, input2, input3, input4, input5, input6;
    private  String verificationotp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        TextView textmobile = findViewById(R.id.textmobileshownumber);
        textmobile.setText(String.format(
                "+63-%s", getIntent().getStringExtra("mobile")
        ));
        input1 = findViewById(R.id.pen1);
        input2 = findViewById(R.id.pen2);
        input3 = findViewById(R.id.pen3);
        input4 = findViewById(R.id.pen4);
        input5 = findViewById(R.id.pen5);
        input6 = findViewById(R.id.pen6);

        setotp();

        final ProgressBar progressBar = findViewById(R.id.prgbarotp);
        final Button buttonotp = findViewById(R.id.button_otp);

        verificationotp = getIntent().getStringExtra("verificationid");

        buttonotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input1.getText().toString().trim().isEmpty()
                        || input2.getText().toString().trim().isEmpty()
                        || input3.getText().toString().trim().isEmpty()
                        || input4.getText().toString().trim().isEmpty()
                        || input5.getText().toString().trim().isEmpty()
                        || input6.getText().toString().trim().isEmpty()){
                    Toast.makeText(otp.this,"Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = input1.getText().toString()+
                        input2.getText().toString()+
                        input3.getText().toString()+
                        input4.getText().toString()+
                        input5.getText().toString()+
                        input6.getText().toString();

                if(verificationotp !=null){
                    progressBar.setVisibility(View.VISIBLE);
                    buttonotp.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationotp,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            buttonotp.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(),newPass.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(otp.this,"The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
    private void  setotp(){
        input1.addTextChangedListener(new TextWatcher() {
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