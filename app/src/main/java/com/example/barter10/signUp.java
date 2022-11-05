package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Model.User;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {

    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    int passvis=1,passvis2=1;
    EditText fullname,username, email, password, conpassword,phoNo;
    ImageButton visOff, btnGoogle, btnFacebook;
    Button signUp;
    TextView signNow;
    DatabaseReference databaseReference;


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

        firebaseDatabase = FirebaseDatabase.getInstance("https://e-palit-default-rtdb.asia-southeast1.firebasedatabase.app/");
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUser();


            }
        });

        //Button google

        btnGoogle = findViewById(R.id.btnGoogle);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);



        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = gsc.getSignInIntent();
                startActivityForResult(i, 123);
            }
        });



        //Button Facebook

        btnFacebook = findViewById(R.id.btnFacebook);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signUp.this, "Facebook", Toast.LENGTH_SHORT).show();
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

    private void addUser() {

        String str_fullname = fullname.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_conpass = conpassword.getText().toString().trim();
        String str_phone = phoNo.getText().toString().trim();


        String checker = "^[9][0-9]{9}$";


        if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_phone) || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_conpass) || TextUtils.isEmpty(str_fullname)) {
            Toast.makeText(signUp.this, "Please filled all the requirements", Toast.LENGTH_SHORT).show();
        } else if (str_password.length() < 6) {
            Toast.makeText(signUp.this, "Password must have more than 6 characters", Toast.LENGTH_SHORT).show();
        } else if (!str_password.equals(str_conpass)){
            conpassword.setError("Password does not match");
            Toast.makeText(signUp.this, str_password+" "+str_conpass, Toast.LENGTH_SHORT).show();
        }else if (str_username.length() < 4) {
            Toast.makeText(signUp.this, "email is too short", Toast.LENGTH_SHORT).show();
        } else if (str_phone.length() == 9) {
            Toast.makeText(signUp.this, "phone No. is too short", Toast.LENGTH_SHORT).show();
        } else if (!str_phone.matches(checker)){
            phoNo.requestFocus();
            phoNo.setError("Correct Format: +63 9xxxxxxxxx");
        }
        else {

            String emailfb = str_username+"@epalit.com";
            String phone = "+63"+str_phone;
            String profilepic ="gs://e-palit.appspot.com/PostItem/Default/kyo.jpg";

            firebaseAuth.createUserWithEmailAndPassword(emailfb, str_password)
                    .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = firebaseAuth.getUid();

                                User user = new User(userId, str_fullname, profilepic, str_username, str_password, phone);
                                databaseReference.child(userId).setValue(user);

                                Toast.makeText(signUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signUp.this, MainActivity.class));
                            } else {
                                Toast.makeText(signUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            User user1 = new User();
                            assert user != null;

                            user1.setUserID(user.getUid());
                            user1.setUsername(user.getDisplayName());
                            user1.setProfilepic(user.getPhotoUrl().toString());


                            Toast.makeText(signUp.this, "sign in google", Toast.LENGTH_SHORT).show();
                            firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(user1);
                            Intent intent = new Intent(signUp.this, Home.class);
                            intent.putExtra("username", user.getDisplayName());
                            intent.putExtra("profilepic", user.getPhotoUrl().toString());

                            startActivity(intent);
                        }else{
                            Toast.makeText(signUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (ApiException e) {
                Toast.makeText(signUp.this, "wtffff", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
}