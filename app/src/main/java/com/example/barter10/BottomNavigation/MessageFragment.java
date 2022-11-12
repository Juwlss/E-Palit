package com.example.barter10.BottomNavigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.MessageListAdapter;
import com.example.barter10.Model.Chat;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {



    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
    private List<User> mUser;
    private DatabaseReference reference,chatreceive;
    private FirebaseAuth firebaseAuth;


    String userid;
    private List<Chat> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.mes_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mUser = new ArrayList<>();
        usersList = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(getContext(), mUser);
        recyclerView.setAdapter(messageListAdapter);

        userid = getActivity().getIntent().getStringExtra("userid");

        //sender chatlist
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Chat chat = dataSnapshot.getValue(Chat.class);
                    usersList.add(chat);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

    private void chatList() {


        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);


                    for (Chat chat : usersList){
                        if (user.getUserID().equals(chat.getSender())
                                && chat.getReceiver().equals(FirebaseAuth.getInstance().getUid())){


                            if(!user.getUserID().equals(FirebaseAuth.getInstance().getUid())){
                                mUser.add(user);
                            }


                        }else if(user.getUserID().equals(chat.getReceiver())
                                && chat.getSender().equals(FirebaseAuth.getInstance().getUid())){


                            if(!user.getUserID().equals(FirebaseAuth.getInstance().getUid())){
                                mUser.add(user);
                            }


                        }

                    }


                }
                messageListAdapter = new MessageListAdapter(getContext(), mUser);
                recyclerView.setAdapter(messageListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        DatabaseReference Receiver = FirebaseDatabase.getInstance().getReference("users");
//        Receiver.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUser.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    User user = dataSnapshot.getValue(User.class);
//
//                    for (Rchatlist rchatlist : rchatlists){
//                        if (user.getUserID().equals(rchatlist.getId())){
//                            mUser.add(user);
//                        }
//                    }
//
//                }
//                messageListAdapter = new MessageListAdapter(getContext(), mUser);
//                recyclerView.setAdapter(messageListAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }



}