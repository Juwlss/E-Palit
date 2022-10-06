package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.barter10.Model.DAOUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signUp extends AppCompatActivity {

    int passvis;

    EditText username, email, password;
    ImageButton signup;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.suname);
        email = findViewById(R.id.suemail);
        password = findViewById(R.id.supass);
        signup = findViewById(R.id.btn_su);

        firebaseAuth = FirebaseAuth.getInstance();

        ImageButton passtoggle = findViewById(R.id.visoff2);


        passtoggle.setVisibility(View.GONE);


    //showing togglepassword when typing
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password.getText().length() > 0){
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
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passvis = 1;
                        break;

                    case 1:
                        passtoggle.setImageResource(R.drawable.ic_baseline_visibility_24);
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passvis = 0;
                        break;
                }
            }
        });

        //signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString().trim();
                String str_email = email.getText().toString().trim();
                String str_password = password.getText().toString().trim();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(signUp.this, "Please filled all the requirements", Toast.LENGTH_SHORT).show();
                }else if (str_password.length() < 6){
                    Toast.makeText(signUp.this, "Password must have more than 6 characters", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        startActivity(new Intent(signUp.this, MainActivity.class));
                                    }else {
                                        Toast.makeText(signUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    Toast.makeText(signUp.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**private void signUp(final String username, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap <String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/e-palit.appspot.com/o/caloocan.jpg?alt=media&token=aee36293-005b-4539-9627-47026f97ba37");

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        User user = new User(username, email, password);
                                        databaseReference.push().setValue(user);
                                        Toast.makeText(signUp.this, "gumana", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(signUp.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(signUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }                 **/


    public void uptoin(View view) {
        startActivity(new Intent(signUp.this, MainActivity.class));
    }
}