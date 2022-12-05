package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Adapter.ChatAdapter;
import com.example.barter10.Model.Chat;
import com.example.barter10.Model.User;
import com.example.barter10.Profile.visitprofile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView username,rating;
    private DatabaseReference reference;
    private Toolbar toolbar;
    private RelativeLayout visitProfile,sendMessage;

    private ImageButton btnSend;
    private EditText textSend;

    String userid;
    ChatAdapter chatAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.messsage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //recycler view
        recyclerView = findViewById(R.id.chats_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mChat = new ArrayList<>();


        //user
        profileImage = findViewById(R.id.image_message);
        username = findViewById(R.id.username_message);
        rating = findViewById(R.id.rating);

        //message
        sendMessage = findViewById(R.id.send_message);
        btnSend = findViewById(R.id.btn_send);
        textSend = findViewById(R.id.text_send);
        visitProfile = findViewById(R.id.m_visitProfile);

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userid = getIntent().getStringExtra("userid");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textSend.getText().toString();
                if(!msg.equals("")){
                    sendMessage(FirebaseAuth.getInstance().getUid(), userid, msg);
                }else{
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                textSend.setText("");
            }
        });

        visitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("uid", userid);
                editor.apply();

                recyclerView.setVisibility(View.GONE);
                sendMessage.setVisibility(View.GONE);
                getSupportActionBar().hide();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.messageContainer, new visitprofile()).commit();


            }
        });


        reference = FirebaseDatabase.getInstance().getReference("users").child(userid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                Picasso.get()
                        .load(user.getProfilepic())
                        .placeholder(R.drawable.ic_default_picture)
                        .into(profileImage);

                rating.setText("Rating: "+user.getRating()+"/5");
                readMessage(FirebaseAuth.getInstance().getUid(), userid, user.getProfilepic());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);


        //add user to chat fragment
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(FirebaseAuth.getInstance().getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference recRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(FirebaseAuth.getInstance().getUid());

        recRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    recRef.child("id").setValue(FirebaseAuth.getInstance().getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }











    private void readMessage(final String myid, final String userid, String imageurl){

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if ((chat.getReceiver().equals(myid) && chat.getSender().equals(userid))
                            || (chat.getReceiver().equals(userid) && chat.getSender().equals(myid))){


                        mChat.add(chat);
                    }



                    chatAdapter = new ChatAdapter(MessageActivity.this, mChat, imageurl);
                    recyclerView.setAdapter(chatAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}