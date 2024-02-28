package com.airsprint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EnterOtp extends AppCompatActivity {

    TextView loginButton123, textView1122, textView12, Resendotp, errorText;
    EditText editText1, editText2, editText3, editText4;

    DatabaseReference usersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterotp_activity);

        loginButton123 = findViewById(R.id.loginButton123);
        textView1122 = findViewById(R.id.textView1122);
        textView12 = findViewById(R.id.textView12);
        Resendotp = findViewById(R.id.Resendotp);
        errorText = findViewById(R.id.errorText);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);

        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterOtp.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        String email = getIntent().getStringExtra("email");
        textView1122.setText(email);

        // Reference to the Firebase database
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        loginButton123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered OTP
                String enteredOtp = editText1.getText().toString().trim() +
                        editText2.getText().toString().trim() +
                        editText3.getText().toString().trim() +
                        editText4.getText().toString().trim();

                // Get the correct OTP from the database for the respective user
                Query query = usersRef.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String correctOtp = userSnapshot.child("otp").getValue(String.class);

                                // Check if entered OTP matches the correct OTP
                                if (enteredOtp.equals(correctOtp)) {
                                    // Proceed to the next activity
                                    Intent intent = new Intent(EnterOtp.this, NewPasswordActivity.class);
                                    intent.putExtra("email", email); // Pass the email to the next activity
                                    startActivity(intent);
                                    return;
                                } else {
                                    // If the entered OTP is incorrect, show a toast and set error message
                                    errorText.setText("Incorrect OTP");
                                    errorText.setVisibility(View.VISIBLE);
                                    setEditTextError();
                                    return;
                                }
                            }
                        } else {
                            // If user data not found, show a toast or handle appropriately
                            Toast.makeText(EnterOtp.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("EnterOtp", "onCancelled", databaseError.toException());
                        Toast.makeText(EnterOtp.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newotp = generateOTP();

                Query query = usersRef.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String userId = userSnapshot.getKey();
                                usersRef.child(userId).child("otp").setValue(newotp);
                            }
                            Toast.makeText(EnterOtp.this, "New OTP sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EnterOtp.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("EnterOtp", "onCancelled", databaseError.toException());
                        Toast.makeText(EnterOtp.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Method to generate a 4-digit OTP
    private String generateOTP() {
        int otp = (int) (Math.random() * 9000) + 1000; // Generates a random number between 1000 and 9999
        return String.valueOf(otp);
    }

    // Method to set error state for EditText fields
    private void setEditTextError() {
        editText1.setBackgroundResource(R.drawable.edittext_border_red);
        editText2.setBackgroundResource(R.drawable.edittext_border_red);
        editText3.setBackgroundResource(R.drawable.edittext_border_red);
        editText4.setBackgroundResource(R.drawable.edittext_border_red);
    }
}
