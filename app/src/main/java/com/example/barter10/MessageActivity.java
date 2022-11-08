package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.barter10.Adapter.ChatAdapter;
import com.example.barter10.Model.Chat;
import com.example.barter10.Model.User;
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


    private ImageButton btnSend;
    private EditText textSend;

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
        btnSend = findViewById(R.id.btn_send);
        textSend = findViewById(R.id.text_send);


//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String userid = getIntent().getStringExtra("userid");

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

                        Toast.makeText(MessageActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                        mChat.add(chat);
                    }

//                    Toast.makeText(MessageActivity.this, chat.getReceiver()+"!@#!@#"+userid, Toast.LENGTH_SHORT).show();


                    chatAdapter = new ChatAdapter(MessageActivity.this, mChat, imageurl);
                    recyclerView.setAdapter(chatAdapter);

                }

                //pang pa error
//                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}