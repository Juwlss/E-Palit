package com.example.barter10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdatePostFragment extends Fragment {

    private String u_timer;
    private EditText u_uploadName, u_uploadLocation, u_uploadDetails, u_uploadCondition, u_uploadValue, u_uploadPreference;
    private Button update;
    private Button u_calendar;
    private String u_category1;
    private RecyclerView u_recyclerView;
    private ImageView u_imageView;
    private DatabaseReference databaseReference;
    private Spinner itemCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_post, container, false);



        //Calendar
        u_calendar = view.findViewById(R.id.u_btnCalendar);
        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(dateValidator);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setCalendarConstraints(constraintBuilder.build());
        MaterialDatePicker materialDatePicker = builder.build();
        u_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        u_calendar.setText(materialDatePicker.getHeaderText());

                    }
                });
            }
        });


        //adding
        itemCategory = view.findViewById(R.id.u_listCategories1);
        List<String> categories = new ArrayList<>();
        categories.add(0, "Select");
        categories.add("Gadget");
        categories.add("Technology");
        categories.add("Fashion");
        categories.add("Sports");
        categories.add("Toy");

        //setting categories in spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(dataAdapter);
        itemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //do nothing
                } else {
                    u_category1 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method
            }
        });

        //getting details in edit text
        u_uploadName = view.findViewById(R.id.u_txtItemName);
        u_uploadLocation = view.findViewById(R.id.u_txtLocation);
        u_uploadDetails = view.findViewById(R.id.u_txtDetails);
        u_uploadCondition = view.findViewById(R.id.u_txtCondition);
        u_uploadValue = view.findViewById(R.id.u_txtEstimatedValue);
        u_uploadPreference = view.findViewById(R.id.u_txtPreference);
        update = view.findViewById(R.id.update);


        String getKey = getArguments().getString("passKey");//For Bundle
        String getUid = getArguments().getString("passUid");//For Bundle

        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(getKey);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String iName  = snapshot.child("itemName").getValue().toString();
                String loc  = snapshot.child("location").getValue().toString();
                String img  = snapshot.child("imageUrl").getValue().toString();
                String iCondition  = snapshot.child("itemCondition").getValue().toString();
                String iDetails  = snapshot.child("itemDetails").getValue().toString();
                String value  = snapshot.child("itemValue").getValue().toString();
                String pref  = snapshot.child("itemPreference").getValue().toString();
                String timer  = snapshot.child("timer").getValue().toString();
                u_uploadName.setText(iName);;
                u_uploadLocation.setText(loc);
                u_uploadCondition.setText(iCondition);
                u_uploadDetails.setText(iDetails);
                u_uploadValue.setText(value);
                u_uploadPreference.setText(pref);
                u_calendar.setText(timer);

//                Picasso.get()
//                        .load(img)
//                        .placeholder(R.drawable.ic_baseline_image_24)
//                        .fit()
//                        .into(postImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePost(getKey, getUid);
            }
        });


        return view;
    }

    private void updatePost(String getKey, String getUid) {
        String itemName = u_uploadName.getText().toString().trim();
        String itemDetails = u_uploadDetails.getText().toString().trim();
        String location = u_uploadLocation.getText().toString().trim();
        String condition = u_uploadCondition.getText().toString().trim();
        String value = u_uploadValue.getText().toString().trim();
        String preference = u_uploadPreference.getText().toString().trim();
        String categories = u_category1.toString();


        HashMap hashMap = new HashMap();
        hashMap.put("itemName", itemName);
        hashMap.put("itemDetails", itemDetails);
        hashMap.put("location", location);
        hashMap.put("itemCondition", condition);
        hashMap.put("itemValue", value);
        hashMap.put("itemPreference", preference);
        hashMap.put("category1", categories);


        databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getContext(), "Post has been updated!", Toast.LENGTH_SHORT).show();
                Fragment mFragment = new FullPostFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ItemKey", getKey);
                bundle.putString("uId", getUid);
                mFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();

            }
        });


    }
}