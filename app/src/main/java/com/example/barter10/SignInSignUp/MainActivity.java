package com.example.barter10.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Home;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.example.barter10.ForgotPassword.forgotPass;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private CallbackManager callbackManager;
    private static final String TAG = "FacebookAuthentication";

    private ImageButton  btnGoogle, btnFacebook;

    int passvis;
    EditText username, password;
    Button signin;
    FirebaseAuth firebaseAuth;
    TextView create;
    private ImageView mImage;
    private RelativeLayout layout;
    private float rating;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.logo);
        mImage.setAnimation(AnimationUtils.loadAnimation(this, R.anim.resize));

        layout = findViewById(R.id.signUpInput);

        //logo shrink
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(mImage, "scaleX", 0.4f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(mImage, "scaleY", 0.4f);
        scaleDownX.setDuration(700);
        scaleDownY.setDuration(700);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

        //move up
        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(mImage, "translationY", -750);
        moveUpY.setDuration(700);

        AnimatorSet moveUp = new AnimatorSet();
        moveUp.play(moveUpY);
        moveUp.start();

        //move up
        ObjectAnimator moveUpY1 = ObjectAnimator.ofFloat(layout, "translationY", -950);
        moveUpY1.setDuration(700);

        AnimatorSet moveUp1 = new AnimatorSet();
        moveUp1.play(moveUpY1);
        moveUp1.start();


        create = findViewById(R.id.lg_create);
        signin = findViewById(R.id.lg_signin);
        ImageButton passtoggle = findViewById(R.id.vis_off);
        password = findViewById(R.id.lgin_pass);
        username = findViewById(R.id.lgin_username);
        passtoggle.setVisibility(View.GONE);

        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        //sign in
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_username = username.getText().toString();
                String str_password = password.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("users");


                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(MainActivity.this, "Please filled all the requirements", Toast.LENGTH_SHORT).show();
                }
                else{

                   databaseReference.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                               if (dataSnapshot.child("username").getValue().toString().equals(str_username)){

                                   String email = dataSnapshot.child("email").getValue().toString();

                                   firebaseAuth.signInWithEmailAndPassword(email,str_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                           if(task.isSuccessful()){
                                               startActivity(new Intent(MainActivity.this, Home.class));
                                           }
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(MainActivity.this, "Sign in Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   });


                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signUp.class));
            }
        });



        //auto sign in
        /**remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView.isChecked()){
        SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "true");
        editor.apply();
        Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
        }else if(!buttonView.isChecked()){
        SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
        Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
        }
        }
        });**/

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

                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
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
                    Toast.makeText(MainActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }


    private void updateUI(FirebaseUser user) {
        if(user!=null){

            assert user != null;

            User user1 = new User();



            DatabaseReference ratingReference = FirebaseDatabase.getInstance().getReference("users");
            ratingReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userrating = snapshot.getValue(User.class);

                    if (snapshot.child(user.getUid()).exists()){


                        startActivity(new Intent(MainActivity.this, Home.class));
                    }else if (!snapshot.child(user.getUid()).exists()){

                        user1.setUserID(user.getUid());
                        user1.setUsername(user.getDisplayName());
                        user1.setProfilepic(user.getPhotoUrl().toString());
                        user1.setRating(userrating.getRating());

                        firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(user1);
                        startActivity(new Intent(MainActivity.this, Home.class));
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }

    }


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

                            assert user != null;
                            User user1 = new User();



                            DatabaseReference ratingReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                            ratingReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userrating = snapshot.getValue(User.class);

                                    if (snapshot.exists()){
                                        Toast.makeText(MainActivity.this, "sign in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Home.class);
                                        intent.putExtra("username", user.getDisplayName());
                                        intent.putExtra("profilepic", user.getPhotoUrl().toString());

                                        startActivity(intent);

                                    }else if (!snapshot.exists()){
                                        user1.setUserID(user.getUid());
                                        user1.setUsername(user.getDisplayName());
                                        user1.setProfilepic(user.getPhotoUrl().toString());
                                        user1.setRating(0);
                                        firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(user1);

                                        Toast.makeText(MainActivity.this, "sign in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Home.class);
                                        intent.putExtra("username", user.getDisplayName());
                                        intent.putExtra("profilepic", user.getPhotoUrl().toString());


                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }


    //signing in going to home page

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    public void forgotPass(View view) {
        startActivity(new Intent(MainActivity.this, forgotPass.class));
    }
}