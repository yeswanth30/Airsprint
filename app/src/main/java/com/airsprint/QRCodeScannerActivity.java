package com.airsprint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class QRCodeScannerActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;
    ImageView back,bell;
    private Handler handler;
    private Runnable runnable;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        barcodeView = findViewById(R.id.barcode_scanner);


        bell = findViewById(R.id.bell);


        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(QRCodeScannerActivity.this, Notification_Activity.class);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.back);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Camera permission has already been granted
            setupBarcodeScanner();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Start a timer to wait for 10 seconds before redirecting to the invalid activity
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                redirectToInvalidActivity();
            }
        };
        handler.postDelayed(runnable, 10000); // Wait for 10 seconds
    }

    private void setupBarcodeScanner() {
        // Set up barcode scanner configuration and start scanning
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() != null) {
                    // Handle barcode result
                    handleBarcodeResult(result.getText());
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // Handle possible result points
            }
        });
    }

    private void handleBarcodeResult(String scannedData) {
        // Cancel the timer as QR code was successfully scanned
        handler.removeCallbacks(runnable);

        // Handle the scanned data here, for example, open a URL
        if (scannedData != null && !scannedData.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedData));
            startActivity(intent);
        } else {
            redirectToInvalidActivity();
        }
    }

    private void redirectToInvalidActivity() {
        Intent intent = new Intent(QRCodeScannerActivity.this, InvalidQRCodeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the timer callbacks to prevent memory leaks
        handler.removeCallbacks(runnable);
    }
}
