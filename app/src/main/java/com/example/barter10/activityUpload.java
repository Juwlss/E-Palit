package com.example.barter10;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class activityUpload extends AppCompatActivity {

    ImageView upImg1, upImg2, upImg3, upImg4;
    Uri imageUri1, imageUri2, imageUri3, imageUri4, uploadImgdef;
    Button btnUpload;
    Button calendar;
    ProgressDialog progressDialog;
    ArrayList<Uri> Imagelist = new ArrayList<Uri>();
    private int upload_count = 0;
    private String timer;
    private String prodId;
    EditText uploadName, uploadDetails, uploadCondition, uploadValue, uploadPreference, uploadTime, uploadCategory;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    private String category1;
    private String category2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //new Image

        upImg1 = findViewById(R.id.upload_image1);
        upImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activityUpload.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(1);
            }
        });
        upImg2 = findViewById(R.id.upload_image2);
        upImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activityUpload.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(2);
            }
        });
        upImg3 = findViewById(R.id.upload_image3);
        upImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activityUpload.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(3);
            }
        });
        upImg4 = findViewById(R.id.upload_image4);
        upImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activityUpload.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(4);
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
        calendar = findViewById(R.id.btnCalendar);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                .build();

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
        //end calendar


        //spinner list of categories

        Spinner list1 = findViewById(R.id.listCategories1);
        Spinner list2 = findViewById(R.id.listCategories2);
        List<String> categories = new ArrayList<>();
        categories.add(0, "Select");
        categories.add("Technology");
        categories.add("Fashion");
        categories.add("Sports");

        //styling
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        //
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
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
        //styling
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        //
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        list2.setAdapter(dataAdapter2);

        list2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select")) {
                    //do nothing
                } else {
                    category2 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method
            }
        });

        //end of spinner list categories

        //getting details in edit text
        uploadName = findViewById(R.id.txtItemName);
        uploadDetails = findViewById(R.id.txtDetails);
        uploadCondition = findViewById(R.id.txtCondition);
        uploadValue = findViewById(R.id.txtEstimatedValue);
        uploadPreference = findViewById(R.id.txtPreference);

        //setting upload button in default
        uploadImgdef = Uri.parse("android.resource://com.example.uploadpage/drawable/upload_button");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            imageUri1 = data.getData();
            upImg1.setImageURI(imageUri1);
            Imagelist.add(imageUri1);
        }
        if (requestCode == 2) {
            imageUri2 = data.getData();
            upImg2.setImageURI(imageUri2);
            Imagelist.add(imageUri2);
        }
        if (requestCode == 3) {
            imageUri3 = data.getData();
            upImg3.setImageURI(imageUri3);
            Imagelist.add(imageUri3);
        }
        if (requestCode == 4) {
            imageUri4 = data.getData();
            upImg4.setImageURI(imageUri4);
            Imagelist.add(imageUri4);
        }

    }

    private void upload() {
        //image in firebase storage
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Post...");

        //generate unique id in item
        Random random = new Random();
        int id = random.nextInt(100000);
        prodId = Integer.toString(id);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(prodId);

        if (Imagelist.isEmpty()) {
            Toast.makeText(activityUpload.this, "Please Upload a Photo", Toast.LENGTH_SHORT).show();
        } else if (timer == null) {
            Toast.makeText(activityUpload.this, "Please set a timer", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            for (upload_count = 0; upload_count < Imagelist.size(); upload_count++) {
                Uri IndividualImage = Imagelist.get(upload_count);
                StorageReference ImageName = storageReference.child(IndividualImage.getLastPathSegment());

                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        //storing details in realtime database firebase

                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("itemdetails");

                        //getting values in edit text
                        String prod_id = prodId;
                        String itemName = uploadName.getText().toString();
                        String itemDetails = uploadDetails.getText().toString();
                        String itemCondition = uploadCondition.getText().toString();
                        String itemValue = uploadValue.getText().toString();
                        String itemPreference = uploadPreference.getText().toString();
                        String timeLimit = timer;
                        String itemCategory = category1 + " " + category2;

                        DetailHelperClass detailClass = new DetailHelperClass(prod_id, itemName, itemDetails, itemCondition, itemValue, itemPreference, timeLimit, itemCategory);
                        reference.child(prod_id).setValue(detailClass);//setting primary key

                        startActivity(new Intent(activityUpload.this, Home.class));
                        //restart activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
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
}