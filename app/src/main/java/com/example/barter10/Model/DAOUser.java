package com.example.barter10.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUser {
    private DatabaseReference databaseReference;
    public DAOUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(User.class.getSimpleName());
    }

    public Task<Void> add (User user){
        if (user == null){
            databaseReference.push().setValue(user);
        }

        return databaseReference.push().setValue(user);
    }
}
