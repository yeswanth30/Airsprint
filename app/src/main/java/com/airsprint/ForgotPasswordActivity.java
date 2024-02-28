package com.airsprint;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.w3c.dom.Text;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView loginButton456,textView12,errorText;
    EditText Emailaddress;

    DatabaseReference usersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_activity);
        loginButton456 = findViewById(R.id.loginButton456);
        Emailaddress = findViewById(R.id.Emailaddress);
        textView12 = findViewById(R.id.textView12);
        errorText = findViewById(R.id.errorText);


        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        loginButton456.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredEmail = Emailaddress.getText().toString().trim();
                if (!validateEmail(enteredEmail)) {
                    // If email format is incorrect, show error message below EditText
                   // Emailaddress.setError("Please enter a valid email");
                    return;
                }

                // Set the background resource for the EditText to reset the border color
               // Emailaddress.setBackgroundResource(R.drawable.edittext_border_red);

                Query query = usersRef.orderByChild("email").equalTo(enteredEmail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // If there is a user with the entered email, proceed to the next activity
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String userEmail = userSnapshot.child("email").getValue(String.class);
                                if (userEmail != null && userEmail.equals(enteredEmail)) {
                                    // Generate OTP
                                    String otp = generateOTP();

                                    // Save OTP to user's data in Firebase
                                    String userId = userSnapshot.getKey();
                                    usersRef.child(userId).child("otp").setValue(otp);

                                    Toast.makeText(ForgotPasswordActivity.this, "OTP sent to your email", Toast.LENGTH_SHORT).show();



                                    // Proceed to the next activity
                                    Intent intent = new Intent(ForgotPasswordActivity.this, EnterOtp.class);
                                    intent.putExtra("email", enteredEmail); // Pass the email to the next activity
                                    startActivity(intent);
                                    return;
                                }
                            }
                    } else {
                            // If no user found with the entered email, show a toast or any appropriate message
                            Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ForgotPasswordActivity", "onCancelled", databaseError.toException());
                        Toast.makeText(ForgotPasswordActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Method to generate a 4-digit OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000; // Generates a random number between 1000 and 9999
        return String.valueOf(otp);
    }
    public Boolean validateEmail(String enteredEmail) {
        String val = Emailaddress.getText().toString().trim();

        if (val.isEmpty()) {
            errorText.setText("Email cannot be empty");
            errorText.setVisibility(View.VISIBLE);
            return false;
        } else if (!val.endsWith("@gmail.com")) {
            errorText.setText("Please enter a valid email address ");
            errorText.setVisibility(View.VISIBLE);
            Emailaddress.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            return false;
        } else {
            errorText.setVisibility(View.GONE); // Hide error message
            Emailaddress.setBackgroundResource(R.drawable.edit_text_border); // Reset border color
            return true;
        }
    }
}
