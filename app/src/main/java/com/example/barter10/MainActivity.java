package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    int passvis;
    EditText username, password;
    Button signin;
    FirebaseAuth firebaseAuth;
    TextView create;
    private ImageView mImage;
    private RelativeLayout layout;

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
                    String email = str_username+"@epalit.com".trim();
                    firebaseAuth.signInWithEmailAndPassword(email,str_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Sign in Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Home.class));
                            }else{
                                Toast.makeText(MainActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "shocks error", Toast.LENGTH_SHORT).show();
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