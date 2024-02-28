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


public class NewPasswordActivity extends AppCompatActivity {

    TextView loginbutton,textView12,errorText;
    EditText conforimpassword;
    EditText newpassword;

    DatabaseReference usersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpassword_activity);
        loginbutton=findViewById(R.id.loginbutton);
        conforimpassword=findViewById(R.id.conforimpassword);
        newpassword=findViewById(R.id.newpassword);
        textView12=findViewById(R.id.textView12);
        errorText=findViewById(R.id.errorText);
        ImageView togglePassword = findViewById(R.id.togglePassword);
        ImageView togglePasswordnew = findViewById(R.id.togglePasswordnew);

        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        togglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                int inputType = (conforimpassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                conforimpassword.setInputType(inputType);
                // Move cursor to the end of the text
                conforimpassword.setSelection(conforimpassword.getText().length());

                // Change the visibility toggle icon
                togglePassword.setImageResource(
                        (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                                R.drawable.visible :
                                R.drawable.hide
                );
            }
        });


        togglePasswordnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                int inputType = (newpassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                newpassword.setInputType(inputType);
                // Move cursor to the end of the text
                newpassword.setSelection(newpassword.getText().length());

                // Change the visibility toggle icon
                togglePasswordnew.setImageResource(
                        (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                                R.drawable.visible :
                                R.drawable.hide
                );
            }
        });
        String email = getIntent().getStringExtra("email");
        // Reference to the Firebase database
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPassword = newpassword.getText().toString().trim();
                String confirmPassword = conforimpassword.getText().toString().trim();

                if (newPassword.equals(confirmPassword)) {
                    // Reset EditText field appearance
                    conforimpassword.setError(null);
                    conforimpassword.setBackgroundResource(R.drawable.edit_text_border);
                    newpassword.setBackgroundResource(R.drawable.edit_text_border);
                    // Find the user by their email
                    final String email = getIntent().getStringExtra("email");
                    Query query = usersRef.orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    // Update the password in the Firebase database
                                    String userId = userSnapshot.getKey();
                                    usersRef.child(userId).child("password").setValue(newPassword);

                                    // Redirect the user to the LoginActivity
                                    Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Finish this activity to prevent the user from going back to it using the back button
                                    return;
                                }
                            } else {
                                // If no user found with the email, show a toast
                                Toast.makeText(NewPasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("NewPasswordActivity", "onCancelled", databaseError.toException());
                            Toast.makeText(NewPasswordActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // If passwords do not match, show a toast
                    Toast.makeText(NewPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    errorText.setText("Passwords do not match");
                    conforimpassword.setBackgroundResource(R.drawable.edittext_border_red);
                    newpassword.setBackgroundResource(R.drawable.edittext_border_red);
                }
            }
        });
    }
}

