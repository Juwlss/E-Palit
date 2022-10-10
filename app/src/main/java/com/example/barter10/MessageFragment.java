package com.example.barter10;

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
import com.example.barter10.Model.Message;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private String[] receivername;
    int[] imageResourceID = {R.drawable.vincentuser, R.drawable.kyouser,R.drawable.paulouser,R.drawable.daleuser};
    private RecyclerView recyclerView;


    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.trade_rv);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**dataInitialized();
        recyclerView = view.findViewById(R.id.mes_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MessageListAdapter messageListAdapter = new MessageListAdapter(getContext(), messageArrayList);
        recyclerView.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();**/

        RecyclerView recyclerView = view.findViewById(R.id.mes_rv);


        setUpMessage();

        MessageListAdapter adapter = new MessageListAdapter(getContext(), messageArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpMessage(){
        String[] chatName = getResources().getStringArray(R.array.messagelist);
        String[] chatMessage = getResources().getStringArray(R.array.conversation);


        for(int i=0; i <chatName.length; i++){
            messageArrayList.add(new Message(chatName[i], chatMessage[i], imageResourceID[i]));
        }

    }

    //trying to display
    /**private void replaceFragment(MessageFragment fragment) {
     FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
     fragmentTransaction.replace(R.id.frame_layout,fragment);
     fragmentTransaction.commit();
     }**/

    /**private void dataInitialized() {

        messageArrayList = new ArrayList<>();
        receivername = new String[]{
                getString(R.string.user1),
                getString(R.string.user2),
                getString(R.string.user3),
                getString(R.string.user4),
        };

        imageResourceID = new int[]{
            R.drawable.vincentuser,
                R.drawable.kyouser,
            R.drawable.paulouser,
            R.drawable.daleuser

        };

        for (int i=0; i < receivername.length; i++){
            Message message = new Message(receivername[i], imageResourceID[i]);
            messageArrayList.add(message);
        }

    }**/
}