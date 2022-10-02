package com.example.barter10;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class forgotpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        Button btnforgot = findViewById(R.id.btn_forgotpassword);
        ImageView btnback = findViewById(R.id.icon_close);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgotpassword.this, otp.class));
                Toast.makeText(forgotpassword.this, "Sending", Toast.LENGTH_SHORT).show();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgotpassword.this, MainActivity.class));
                Toast.makeText(forgotpassword.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
    }
}