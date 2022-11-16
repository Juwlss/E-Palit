package com.example.barter10.Profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.R;
import com.example.barter10.SignInSignUp.MainActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profileSettings extends Fragment {

    private FirebaseAuth firebaseAuth;
    private Button btnInfo;
    private Button btnSec;
    private TextView logout;
    private TextView userName;

    private ImageView picProfile;
    public Uri imageuri;
    private ImageView changeProfilePic;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static final int REQUEST_CODE_IMAGE = 101;

    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        logout = view.findViewById(R.id.btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();
//        btnInfo = view.findViewById(R.id.btnMyInfo);
        btnSec = view.findViewById(R.id.btnSec);
        userName = view.findViewById(R.id.profileUsername);
        picProfile = view.findViewById(R.id.imgProfile);


        changeProfilePic = view.findViewById(R.id.changePicture);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getContext(), gso);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //log out firebase
                FirebaseAuth.getInstance().signOut();

                //log out google account
                signOutGoogle();

                //facebook
                signOutFacebook();
                Toast.makeText(getActivity(), "Log out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

//        btnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                Fragment infoFrag = new infoSettingFragment();
//                fragmentTransaction.replace(R.id.frame_layout, infoFrag);
//                fragmentTransaction.commit();
//            }
//        });

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment securityFrag = new securityFragment();
                fragmentTransaction.replace(R.id.frame_layout, securityFrag);
                fragmentTransaction.commit();
            }
        });

        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadImage();

            }
        });

        //retrieving data from firebase and display it to profile
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");
        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                        Picasso.get()
                                .load(Profilepic)
                                .placeholder(R.drawable.ic_default_picture)
                                .into(picProfile);


                        userName.setText(dataSnapshot.child("username").getValue().toString());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    private void signOutFacebook(){

        LoginManager.getInstance().logOut();
    }

    //signing out google account
    private void signOutGoogle(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    private void uploadImage() {

        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){

            if(data.getData() != null){
                imageuri = data.getData();
                picProfile.setImageURI(imageuri);
                UpdateProfileImage();
            }

        }else{
            Toast.makeText(getContext(), "Please pick image", Toast.LENGTH_SHORT).show();
        }



    }

    private void UpdateProfileImage(){

        Uri IndividualImage = imageuri;
        StorageReference ImageName = storageReference.child("ProfilePicture").child(IndividualImage.getLastPathSegment());

        ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String profilepic = String.valueOf(uri);


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .child("profilepic");

                        reference.setValue(profilepic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }













}