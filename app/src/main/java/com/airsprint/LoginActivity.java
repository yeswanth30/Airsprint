package com.airsprint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

public class LoginActivity extends AppCompatActivity {

    EditText Emailaddress, loginpassword;
    TextView loginbutton, registertext, errorText,rightTextView637,errorText2;
    SharedPreferences sharedPreferences;
    String passwordFromDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Emailaddress = findViewById(R.id.Emailaddress);
        loginpassword = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        registertext = findViewById(R.id.newuser);
        errorText = findViewById(R.id.errorText);
        errorText2 = findViewById(R.id.errorText2);
        rightTextView637 = findViewById(R.id.rightTextView637);
        ImageView togglePassword = findViewById(R.id.togglePassword);

        Emailaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    Emailaddress.setBackgroundResource(R.drawable.rectangle_signup);
                    errorText.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        loginpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    loginpassword.setBackgroundResource(R.drawable.rectangle_signup);
                    errorText2.setVisibility(View.GONE);
                    errorText2.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        rightTextView637.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        togglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                int inputType = (loginpassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                loginpassword.setInputType(inputType);
                // Move cursor to the end of the text
                loginpassword.setSelection(loginpassword.getText().length());

                // Change the visibility toggle icon
                togglePassword.setImageResource(
                        (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ?
                                R.drawable.visible :
                                R.drawable.hide
                );
            }
        });

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });
    }


    public Boolean validateEmail() {
        String val = Emailaddress.getText().toString().trim();

        if (val.isEmpty()) {
            errorText.setText("Email cannot be empty");
            errorText.setVisibility(View.VISIBLE);
            Emailaddress.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red

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

    public Boolean validatePassword() {
        String val = loginpassword.getText().toString();
        if (val.isEmpty()) {
            errorText2.setText("Password cannot be empty");
            errorText2.setVisibility(View.VISIBLE);
            loginpassword.setBackgroundResource(R.drawable.edittext_border_red);
            return false;
        } else {
            errorText2.setVisibility(View.GONE); // Hide error message
            loginpassword.setBackgroundResource(R.drawable.edit_text_border); // Reset border color
            return true;
        }
    }

    public void checkUser() {
        String userEmail = Emailaddress.getText().toString().trim();
        String userPassword = loginpassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean emailFound = false;
                boolean passwordMatched = false;

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String emailFromDB = userSnapshot.child("email").getValue(String.class);
                    String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                    if (emailFromDB != null && emailFromDB.equals(userEmail)) {
                        // Email found in database
                        emailFound = true;

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            // Password matches, login successful
                            passwordMatched = true;

                            String nameFromDB = userSnapshot.child("name").getValue(String.class);
                            String emailFrom = userSnapshot.child("email").getValue(String.class);
                            String phonefromdb = userSnapshot.child("phone").getValue(String.class);
                            String passwordfromdb = userSnapshot.child("password").getValue(String.class);

                            String userFromDB = userSnapshot.child("username").getValue(String.class);
                            String imageFromDB = userSnapshot.child("imageurl").getValue(String.class);
                            String useridFromDB = userSnapshot.getKey(); // Get the user's unique ID
                            String timestampFromDB = userSnapshot.child("timestamp").getValue(String.class);

                            // Store user details in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", nameFromDB);
                            editor.putString("email", emailFrom);
                            editor.putString("phone", phonefromdb);
                            editor.putString("password", passwordfromdb);
                            editor.putString("username", userFromDB);
                            editor.putString("imageurl", imageFromDB);
                            editor.putString("userid", useridFromDB);
                            editor.putString("timestamp", timestampFromDB);
                            editor.apply();

                            Emailaddress.setText("");
                            loginpassword.setText("");

                            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("fullname", nameFromDB);
                            Log.e("naaaam", nameFromDB);
                            // intent.putExtra("city", cityFromDB);
                            intent.putExtra("username", userFromDB);
                            startActivity(intent);

                            // Set the login status in shared preferences
                            SharedPreferences loginSharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                            SharedPreferences.Editor loginEditor = loginSharedPreferences.edit();
                            loginEditor.putBoolean("isLoggedIn", true);
                            loginEditor.apply();

                            break; // Break the loop once login successful
                        }
                    }
                }

                if (!emailFound) {
                    // Email not found in database
                    errorText.setText("Email doesn't exist");
                    errorText.setVisibility(View.VISIBLE);
                    Emailaddress.setBackgroundResource(R.drawable.edittext_border_red);
                    loginpassword.setBackgroundResource(R.drawable.edit_text_border);
                } else if (!passwordMatched) {
                    // Password incorrect
                    errorText2.setText("Incorrect Password");
                    errorText2.setVisibility(View.VISIBLE);
                    loginpassword.setBackgroundResource(R.drawable.edittext_border_red);
                    Emailaddress.setBackgroundResource(R.drawable.edit_text_border);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", "Database Error: " + error.getMessage());
            }
        });
    }
}