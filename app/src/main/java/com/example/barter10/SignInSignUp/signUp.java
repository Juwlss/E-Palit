package com.example.barter10.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.facebook.FacebookSdk;
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

    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private CallbackManager callbackManager;
    private static final String TAG = "FacebookAuthentication";



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
        final ProgressBar progressBar = findViewById(R.id.prgbarsgn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoNo.getText().toString().trim().isEmpty()){
                    Toast.makeText(signUp.this,"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }
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


                                startActivity(intent);

                            }

                        }
                );


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
        callbackManager = CallbackManager.Factory.create();

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(signUp.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(@NonNull FacebookException e) {

                    }

                });
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

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "sign in with credential: successful");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Toast.makeText(signUp.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            User user1 = new User();
            assert user != null;

            user1.setUserID(user.getUid());
            user1.setUsername(user.getDisplayName());
            user1.setProfilepic(user.getPhotoUrl().toString());
            firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(user1);

            startActivity(new Intent(signUp.this, Home.class));
        }

    }

//    private void addUser() {
//
//        String str_fullname = fullname.getText().toString().trim();
//        String str_username = username.getText().toString().trim();
//        String str_password = password.getText().toString().trim();
//        String str_conpass = conpassword.getText().toString().trim();
//        String str_phone = phoNo.getText().toString().trim();
//
//
//
//
//        String checker = "^[9][0-9]{9}$";
//
//
//        if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_phone) || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_conpass) || TextUtils.isEmpty(str_fullname)) {
//            Toast.makeText(signUp.this, "Please filled all the requirements", Toast.LENGTH_SHORT).show();
//        } else if (str_password.length() < 6) {
//            Toast.makeText(signUp.this, "Password must have more than 6 characters", Toast.LENGTH_SHORT).show();
//        } else if (!str_password.equals(str_conpass)){
//            conpassword.setError("Password does not match");
//            Toast.makeText(signUp.this, str_password+" "+str_conpass, Toast.LENGTH_SHORT).show();
//        }else if (str_username.length() < 4) {
//            Toast.makeText(signUp.this, "email is too short", Toast.LENGTH_SHORT).show();
//        } else if (str_phone.length() == 9) {
//            Toast.makeText(signUp.this, "phone No. is too short", Toast.LENGTH_SHORT).show();
//        } else if (!str_phone.matches(checker)){
//            phoNo.requestFocus();
//            phoNo.setError("Correct Format: +63 9xxxxxxxxx");
//        }
//        else {
//
//            String emailfb = str_username+"@epalit.com";
//            String phone = "+63"+str_phone;
//            String profilepic ="gs://e-palit.appspot.com/PostItem/Default/kyo.jpg";
//
//            firebaseAuth.createUserWithEmailAndPassword(emailfb, str_password)
//                    .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                String userId = firebaseAuth.getUid();
//
//                                User user = new User(userId, str_fullname, profilepic, str_username, str_password, phone);
//                                databaseReference.child(userId).setValue(user);
//
//
//
//                                Toast.makeText(signUp.this, "Registered xxxx Successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(signUp.this, MainActivity.class));
//                            } else {
//                                Toast.makeText(signUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    });
//        }
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //facebook signin
        callbackManager.onActivityResult(requestCode, resultCode, data);

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