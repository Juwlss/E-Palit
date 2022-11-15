package com.example.barter10.BottomNavigation;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.barter10.Adapter.MessageListAdapter;
import com.example.barter10.CallBack;
import com.example.barter10.CallBackItemTouch;
import com.example.barter10.Model.Chat;
import com.example.barter10.Model.Chatlist;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MessageFragment extends Fragment implements CallBackItemTouch {



    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
    private List<User> mUser;
    private DatabaseReference reference,chatreceive;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout layout;

    String userid;
    private List<Chat> usersList;
    private List<Chatlist> messagelists;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        layout = view.findViewById(R.id.RLM);

        recyclerView = view.findViewById(R.id.mes_rv);

        ItemTouchHelper.Callback callback = new CallBack(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUser = new ArrayList<>();
        messagelists = new ArrayList<>();
        usersList = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(getContext(), mUser);
        recyclerView.setAdapter(messageListAdapter);




        userid = getActivity().getIntent().getStringExtra("userid");




        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(FirebaseAuth.getInstance().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagelists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                    messagelists.add(chatlist);
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
                    for (Chatlist chatlist : messagelists){
                        if (user.getUserID().equals(chatlist.getId())){
                            mUser.add(user);
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
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }




    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mUser.add(newPosition,mUser.remove(oldPosition));
        messageListAdapter.notifyItemMoved(oldPosition,newPosition);
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int position) {
        String uid = mUser.get(viewHolder.getAdapterPosition()).getUserID();
        String name = mUser.get(viewHolder.getAdapterPosition()).getUsername();

        final User deletedItem = mUser.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        messageListAdapter.removeItem(viewHolder.getAdapterPosition());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uid);

        reference.removeValue();



//        Snackbar snackbar = Snackbar.make(layout, name+" removed...", Snackbar.LENGTH_LONG);
//        snackbar.setAction("UNDO", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageListAdapter.restoreItem(deletedItem,deletedIndex);
//            }
//        });
//
//        snackbar.setActionTextColor(Color.GREEN);
//        snackbar.show();


    }
}