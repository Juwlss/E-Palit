
package com.example.barter10.Upload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.barter10.Adapter.UploadListAdapter;
import com.example.barter10.Home;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activityUpload extends AppCompatActivity{

    private static final int REQUEST_CODE_IMAGE = 101;
    private UploadListAdapter uploadListAdapter;
    RecyclerView recyclerView;
    ImageView imageView;
    Button btnUpload;
    Button calendar;
    ProgressDialog progressDialog;
    Uri imageuri;
    ArrayList<String> urlStrings;
    ArrayList<Uri> itemList = new ArrayList<>();
    private int upload_count = 0;
    private String timer;
    private EditText uploadName, uploadLocation, uploadDetails, uploadCondition, uploadValue, uploadPreference;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    private String category1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);



        //new Image
        imageView = findViewById(R.id.image);
        recyclerView = findViewById(R.id.upload_list);
        uploadListAdapter = new UploadListAdapter(itemList);
        recyclerView.setLayoutManager(new GridLayoutManager(activityUpload.this, 2));
        recyclerView.setAdapter(uploadListAdapter);


        if(ContextCompat.checkSelfPermission(activityUpload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activityUpload.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_IMAGE);

        }

        Button btnImg = findViewById(R.id.btn_img);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        //uploading phase
        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        });

        //calendar
        //calendar
        calendar = findViewById(R.id.btnCalendar);
        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(dateValidator);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setCalendarConstraints(constraintBuilder.build());
        MaterialDatePicker materialDatePicker = builder.build();
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        calendar.setText(materialDatePicker.getHeaderText());
                        timer = materialDatePicker.getHeaderText();
                        Toast.makeText(activityUpload.this, materialDatePicker.getHeaderText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





        //spinner list of categories

        //adding
        Spinner list1 = findViewById(R.id.listCategories1);
        List<String> categories = new ArrayList<>();
        categories.add(0, "Select");
        categories.add("Gadget");
        categories.add("Technology");
        categories.add("Fashion");
        categories.add("Sports");
        categories.add("Toy");

        //setting categories in spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list1.setAdapter(dataAdapter);
        list1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //do nothing
                } else {
                    category1 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method
            }
        });

        //getting details in edit text
        uploadName = findViewById(R.id.txtItemName);
        uploadLocation = findViewById(R.id.txtLocation);
        uploadDetails = findViewById(R.id.txtDetails);
        uploadCondition = findViewById(R.id.txtCondition);
        uploadValue = findViewById(R.id.txtEstimatedValue);
        uploadPreference = findViewById(R.id.txtPreference);


    }

    private void uploadImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && null != data){

            if (data.getClipData() != null){
                int x = data.getClipData().getItemCount();
                for (int i=0; i<x; i++){
                    imageuri = data.getClipData().getItemAt(i).getUri();
                    itemList.add(imageuri);
                    uploadListAdapter.notifyDataSetChanged();
                }

            }else{
                imageuri = data.getData();
                itemList.add(imageuri);
                uploadListAdapter.notifyDataSetChanged();
                Toast.makeText(activityUpload.this, "Single", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(activityUpload.this, "Please pick image", Toast.LENGTH_SHORT).show();
        }



    }

    private void upload() {
        //image in firebase storage
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Post...");

        //Getting userID
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        // generating key
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("PendApproval");

        urlStrings = new ArrayList<>();
        String itemKey = reference.push().getKey();


        //referring to storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PendApproval");
        //getting values in edit text
        String postId =itemKey;
        String itemName = uploadName.getText().toString().trim();
        String itemLocation = uploadLocation.getText().toString().trim();
        String itemDetails = uploadDetails.getText().toString().trim();
        String itemCondition = uploadCondition.getText().toString().trim();
        String itemValue = uploadValue.getText().toString().trim();
        String itemPreference = uploadPreference.getText().toString().trim();
        String timeLimit = timer;

        String cat1 = category1;


        if (itemList.isEmpty() || timer == null || TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemDetails)
                || TextUtils.isEmpty(itemCondition) || TextUtils.isEmpty(itemValue) || TextUtils.isEmpty(itemPreference)
                || cat1 == null){
            Toast.makeText(activityUpload.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else {

            for (upload_count=0; upload_count<itemList.size(); upload_count++){
                Uri IndividualImage = itemList.get(upload_count);
                StorageReference ImageName = storageReference.child(IndividualImage.getLastPathSegment());

                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlStrings.add(String.valueOf(uri));
                                if (urlStrings.size() == itemList.size()){
                                    //displaying pictures to upload
                                    storeLink(urlStrings);

                                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                    DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");

                                    postReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String name;
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                                                if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){

                                                    String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                                                    //getting the username of uploader
                                                    name = dataSnapshot.child("username").getValue().toString();
                                                    //uploading to firebase
                                                    Upload upload = new Upload(FirebaseAuth.getInstance().getUid(), uri.toString(), name, Profilepic,itemLocation, itemName, itemCondition, cat1,itemDetails,itemValue,itemPreference,timeLimit);                                                    reference.child(postId).setValue(upload);//setting primary key

                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(activityUpload.this, "Wait for Admin Approval", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(activityUpload.this, Home.class));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activityUpload.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(activityUpload.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

    }

    private void storeLink(ArrayList<String> urlStrings) {
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i <urlStrings.size() ; i++) {
            hashMap.put("ImgLink"+i, urlStrings.get(i));

        }

        progressDialog.dismiss();
        itemList.clear();
    }

}