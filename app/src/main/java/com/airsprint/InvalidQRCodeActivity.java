package com.airsprint;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InvalidQRCodeActivity extends AppCompatActivity {
    TextView Scanbutton;

    ImageView back,bell;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_qr_code);
        Scanbutton=findViewById(R.id.Scanbutton);
        back=findViewById(R.id.back);
        bell = findViewById(R.id.bell);


        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(InvalidQRCodeActivity.this, Notification_Activity.class);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvalidQRCodeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvalidQRCodeActivity.this, QRCodeScannerActivity.class);
                startActivity(intent);
            }
        });

    }
}
