package com.airsprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airsprint.Models.BookingDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Step2Fragment extends Fragment {

    private EditText senderNameEditText;
    private EditText senderMobileEditText;
    private EditText senderMessageEditText;
    private EditText receiverNameEditText;
    private EditText receiverMobileEditText;
    private EditText receiverMessageEditText;
    private Spinner packageTypeSpinner;
    private TextView saveButton;

    // Firebase
    private DatabaseReference mDatabase;
    private String bookingId; // Store booking ID

    TextView sendernameErrorTextView,sendermobileErrorTextView,sendermessageErrorTextView,RecievernameErrorTextView,RecievermobileErrorTextView,RecieverMessageErrorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.step2_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        senderNameEditText = view.findViewById(R.id.senderNameEditText);
        senderMobileEditText = view.findViewById(R.id.senderMobileEditText);
        senderMessageEditText = view.findViewById(R.id.senderMessageEditText);
        receiverNameEditText = view.findViewById(R.id.receiverNameEditText);
        receiverMobileEditText = view.findViewById(R.id.receiverMobileEditText);
        receiverMessageEditText = view.findViewById(R.id.receiverMessageEditText);
        packageTypeSpinner = view.findViewById(R.id.packageTypeSpinner);
        saveButton = view.findViewById(R.id.saveButton);
        sendernameErrorTextView = view.findViewById(R.id.sendernameErrorTextView);
        sendermobileErrorTextView = view.findViewById(R.id.sendermobileErrorTextView);
        sendermessageErrorTextView = view.findViewById(R.id.sendermessageErrorTextView);
        RecievernameErrorTextView = view.findViewById(R.id.RecievernameErrorTextView);
        RecievermobileErrorTextView = view.findViewById(R.id.RecievermobileErrorTextView);
        RecieverMessageErrorTextView = view.findViewById(R.id.RecieverMessageErrorTextView);


        senderNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    senderNameEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    sendernameErrorTextView.setVisibility(View.GONE);
                    sendernameErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        senderMobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    senderMobileEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    sendermobileErrorTextView.setVisibility(View.GONE);
                    sendermobileErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        senderMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    senderMessageEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    sendermessageErrorTextView.setVisibility(View.GONE);
                    sendermessageErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        receiverNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    receiverNameEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    RecievernameErrorTextView.setVisibility(View.GONE);
                    RecievernameErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        receiverMobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    receiverMobileEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    RecievermobileErrorTextView.setVisibility(View.GONE);
                    RecievermobileErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        receiverMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    receiverMessageEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    RecieverMessageErrorTextView.setVisibility(View.GONE);
                    RecieverMessageErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Populate package type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.package_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packageTypeSpinner.setAdapter(adapter);

        // Retrieve booking ID from SharedPreferences
        bookingId = getBookingIdFromSharedPreferences();
        Log.d("Step2Fragment", "Booking ID retrieved from SharedPreferences: " + bookingId);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBookingDetails();
            }
        });
    }

    private void saveBookingDetails() {
        String senderName = senderNameEditText.getText().toString().trim();
        String senderMobile = senderMobileEditText.getText().toString().trim();
        String senderMessage = senderMessageEditText.getText().toString().trim();
        String receiverName = receiverNameEditText.getText().toString().trim();
        String receiverMobile = receiverMobileEditText.getText().toString().trim();
        String receiverMessage = receiverMessageEditText.getText().toString().trim();
        String packageType = packageTypeSpinner.getSelectedItem().toString();

        if (!validateSenderDetails(senderName, senderMobile, senderMessage)) {
            return; // If sender details are invalid, return
        }

        // Validate receiver details
        if (!validateReceiverDetails(receiverName, receiverMobile, receiverMessage)) {
            return; // If receiver details are invalid, return
        }

        if (bookingId == null || bookingId.isEmpty()) {
            // Generate a new booking ID
            bookingId = mDatabase.child("booking").push().getKey();
            saveBookingIdToSharedPreferences(bookingId); // Save booking ID to SharedPreferences
        }

        // Store booking details in Firebase under the same key
        BookingDetails bookingDetails = new BookingDetails(senderName, senderMobile, senderMessage,
                receiverName, receiverMobile, receiverMessage, packageType);

        if (bookingId != null) {
            mDatabase.child("booking").child(bookingId).child("senderName").setValue(senderName);
            mDatabase.child("booking").child(bookingId).child("senderMobile").setValue(senderMobile);
            mDatabase.child("booking").child(bookingId).child("senderMessage").setValue(senderMessage);
            mDatabase.child("booking").child(bookingId).child("receiverName").setValue(receiverName);
            mDatabase.child("booking").child(bookingId).child("receiverMobile").setValue(receiverMobile);
            mDatabase.child("booking").child(bookingId).child("receiverMessage").setValue(receiverMessage);
            mDatabase.child("booking").child(bookingId).child("packageType").setValue(packageType);

            // Pass booking ID to Step3Fragment
            Bundle bundle = new Bundle();
            bundle.putString("bookingId", bookingId);

            // Create an instance of Step3Fragment
            Step3Fragment step3Fragment = new Step3Fragment();
            step3Fragment.setArguments(bundle);

            // Navigate to Step3Fragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, step3Fragment)
                    .addToBackStack(null)
                    .commit();

            Toast.makeText(getContext(), "Booking details saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to save booking details", Toast.LENGTH_SHORT).show();
        }
    }

    // Retrieve booking ID from SharedPreferences
    private String getBookingIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BookingPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("bookingId", null);
    }

    // Save booking ID to SharedPreferences
    private void saveBookingIdToSharedPreferences(String bookingId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BookingPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bookingId", bookingId);
        editor.apply();
    }
    private boolean validateSenderDetails(String senderName, String senderMobile, String senderMessage) {
        boolean isValid = true;

        if (senderName.isEmpty()) {
            sendernameErrorTextView.setText("Please enter SenderName");
            sendernameErrorTextView.setVisibility(View.VISIBLE);
            senderNameEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            sendernameErrorTextView.setVisibility(View.GONE);
            senderNameEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (senderMobile.isEmpty()) {
            sendermobileErrorTextView.setText("Please enter SenderMoblie");
            sendermobileErrorTextView.setVisibility(View.VISIBLE);
            senderMobileEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            sendermobileErrorTextView.setVisibility(View.GONE);

            senderMobileEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (senderMessage.isEmpty()) {
            sendermessageErrorTextView.setText("Please enter SenderMessage");
            sendermessageErrorTextView.setVisibility(View.VISIBLE);
            senderMessageEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            sendermessageErrorTextView.setVisibility(View.GONE);
            senderMessageEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (!isValid) {
          //  Toast.makeText(getContext(), "Please fill in all sender details", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    // Validate receiver details
    private boolean validateReceiverDetails(String receiverName, String receiverMobile, String receiverMessage) {
        boolean isValid = true;

        if (receiverName.isEmpty()) {
            RecievernameErrorTextView.setText("Please enter RecieverName");
            RecievernameErrorTextView.setVisibility(View.VISIBLE);
            receiverNameEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            RecievernameErrorTextView.setVisibility(View.GONE);
            receiverNameEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (receiverMobile.isEmpty()) {
            RecievermobileErrorTextView.setText("Please enter RecieverMoblie");
            RecievermobileErrorTextView.setVisibility(View.VISIBLE);
            receiverMobileEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            RecievermobileErrorTextView.setVisibility(View.GONE);
            receiverMobileEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (receiverMessage.isEmpty()) {
            RecieverMessageErrorTextView.setText("Please enter RecierverMessage");
            RecieverMessageErrorTextView.setVisibility(View.VISIBLE);
            receiverMessageEditText.setBackgroundResource(R.drawable.edittext_border_red);
            isValid = false;
        } else {
            RecieverMessageErrorTextView.setVisibility(View.GONE);
            receiverMessageEditText.setBackgroundResource(R.drawable.edit_text_border);
        }

        if (!isValid) {
           // Toast.makeText(getContext(), "Please fill in all receiver details", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }}