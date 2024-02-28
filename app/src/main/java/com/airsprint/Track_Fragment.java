package com.airsprint;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Track_Fragment extends Fragment {
    EditText searchtext;
    TextView RecipientName, trackingid, source, destination, selecteddate, textView1, textView1222,textView124,textView12344;
    DatabaseReference databaseReference;
    String bookingId;
    String userid;
    TextView textView122233,textView1233344;

    TextView leftTextView;

    View dividerView,dividerView1,dividerView2,newView;

    TextView FromTextView,ToTextView,Recipient;

    TextView EstDelivery,textView2,textView11112,textView1111222;


    ImageView imageView,imageView11,imageView1133,bell,qrcodeimage;

    ImageView imageViewsss,imageViewsskks,imageViewsskksju;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.track_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchtext = view.findViewById(R.id.searchtext);

        bell = view.findViewById(R.id.bell);
        qrcodeimage = view.findViewById(R.id.qrcodeimage);

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), Notification_Activity.class);
                startActivity(intent);
            }
        });

        qrcodeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRCodeScannerActivity.class);
                startActivity(intent);
            }
        });




        RecipientName = view.findViewById(R.id.RecipientName);
        textView2 = view.findViewById(R.id.textView2);

        imageView=view.findViewById(R.id.imageView);
        imageView11=view.findViewById(R.id.imageView11);
        imageView1133=view.findViewById(R.id.imageView1133);

//        imageViewsss=view.findViewById(R.id.imageViewsss);
//        imageViewsskks=view.findViewById(R.id.imageViewsskks);
//        imageViewsskksju=view.findViewById(R.id.imageViewsskksju);





        textView11112=view.findViewById(R.id.textView11112);
        textView1111222=view.findViewById(R.id.textView1111222);
        trackingid = view.findViewById(R.id.trackingid);
        source = view.findViewById(R.id.source);
        destination = view.findViewById(R.id.destination);
        selecteddate = view.findViewById(R.id.selecteddate);
        textView1 = view.findViewById(R.id.textView1);
        textView1222 = view.findViewById(R.id.textView1222);
        textView124 = view.findViewById(R.id.textView124);
        textView12344=view.findViewById(R.id.textView12344);
        textView122233=view.findViewById(R.id.textView122233);
        textView1233344=view.findViewById(R.id.textView1233344);
        leftTextView=view.findViewById(R.id.leftTextView);
        leftTextView.setVisibility(View.GONE);
        dividerView=view.findViewById(R.id.dividerView);
        dividerView1=view.findViewById(R.id.dividerView1);
        dividerView2=view.findViewById(R.id.dividerView2);
        FromTextView=view.findViewById(R.id.FromTextView);
        ToTextView=view.findViewById(R.id.ToTextView);
        Recipient=view.findViewById(R.id.Recipient);
        newView=view.findViewById(R.id.newView);
        EstDelivery=view.findViewById(R.id.EstDelivery);
        dividerView.setVisibility(View.GONE);
        dividerView1.setVisibility(View.GONE);
        dividerView2.setVisibility(View.GONE);
        newView.setVisibility(View.GONE);
        FromTextView.setVisibility(View.GONE);
        ToTextView.setVisibility(View.GONE);
        Recipient.setVisibility(View.GONE);
        EstDelivery.setVisibility(View.GONE);


        textView1.setVisibility(View.GONE);
        textView124.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);



        textView12344.setVisibility(View.GONE);
        textView122233.setVisibility(View.GONE);
        textView11112.setVisibility(View.GONE);
        textView1222.setVisibility(View.GONE);
        textView1233344.setVisibility(View.GONE);
        textView1111222.setVisibility(View.GONE);


        imageView.setVisibility(View.GONE);
        imageView11.setVisibility(View.GONE);
        imageView1133.setVisibility(View.GONE);


//        imageViewsss.setVisibility(View.GONE);
//        imageViewsskks.setVisibility(View.GONE);
//        imageViewsskksju.setVisibility(View.GONE);

        hideTextViews();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_details", MODE_PRIVATE);
        userid = sharedPreferences.getString("userid", "");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("booking");

        ImageView searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trackingId = searchtext.getText().toString().trim();
                if (!trackingId.isEmpty()) {
                    fetchBookingInformation(trackingId);
                } else {
                    // Show toast indicating empty tracking ID
                    showToast("Please enter a Tracking ID");
                }
            }
        });

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                String trackingId = s.toString().trim();
                if (trackingId.isEmpty()) {
                    // If tracking ID is empty, hide all TextViews
                    hideTextViews();
                } else {
                    // If a tracking ID is entered, fetch and display booking information
                    fetchBookingInformation(trackingId);
                }
            }
        });
    }

    private void fetchBookingInformation(String trackingId) {

        Log.d("Track_Fragment", "fetchBookingInformation: Tracking ID: " + trackingId);

        leftTextView.setVisibility(View.GONE);
        dividerView.setVisibility(View.GONE);
        dividerView1.setVisibility(View.GONE);
        dividerView2.setVisibility(View.GONE);
        newView.setVisibility(View.GONE);
        FromTextView.setVisibility(View.GONE);
        ToTextView.setVisibility(View.GONE);
        Recipient.setVisibility(View.GONE);
        EstDelivery.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView124.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView12344.setVisibility(View.GONE);
        textView122233.setVisibility(View.GONE);
        textView11112.setVisibility(View.GONE);
        textView1222.setVisibility(View.GONE);
        textView1233344.setVisibility(View.GONE);
        textView1111222.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        imageView11.setVisibility(View.GONE);

        imageView1133.setVisibility(View.GONE);

//        imageViewsss.setVisibility(View.GONE);
//        imageViewsskks.setVisibility(View.GONE);
//        imageViewsskksju.setVisibility(View.GONE);

        Query query = databaseReference.orderByChild("trackingId").equalTo(trackingId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Fetching booking information
                        String recipientName = snapshot.child("receiverName").getValue(String.class);
                        String trackingIdValue = snapshot.child("trackingId").getValue(String.class);
                        String sourceValue = snapshot.child("source").getValue(String.class);
                        String destinationValue = snapshot.child("destination").getValue(String.class);
                        String selectedDateValue = snapshot.child("selectedDate").getValue(String.class);
                        String detailedAddressValue = snapshot.child("detailedAddress").getValue(String.class);
                        bookingId = snapshot.child("bookingId").getValue(String.class);
                        Log.d("Track_Fragment", "Booking ID: " + bookingId);


                        // Displaying booking information
                        RecipientName.setText(recipientName);
                        trackingid.setText(trackingIdValue);
                        source.setText(sourceValue);
                        String destinationText = destinationValue + ", " + detailedAddressValue;
                        destination.setText(destinationText);
                        selecteddate.setText(selectedDateValue);

                        // Form the child node using booking ID and user ID
                        String childNode = bookingId + "_" + userid;
                        Log.d("childNode", "Booking ID: " + bookingId);



                        // Fetch and display detailed status information
                        DatabaseReference detailStatusRef = FirebaseDatabase.getInstance().getReference().child("detail_status").child(childNode);
                        detailStatusRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot detailSnapshot) {
                                if (detailSnapshot.exists()) {
                                    for (DataSnapshot statusSnapshot : detailSnapshot.getChildren()) {
                                        String status = statusSnapshot.child("status").getValue(String.class);
                                        String time = statusSnapshot.child("date").getValue(String.class);

                                        // Check if the status is "Delivered"
                                        if (status != null) {
                                            if (status.equals("Delivered")) {
                                                // Display delivered status and time
                                                textView1.setText(status);
                                                textView124.setText(time);
                                                textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_green));
                                                textView1.setVisibility(View.VISIBLE);
                                                textView124.setVisibility(View.VISIBLE);
                                                imageView.setImageResource(R.drawable.greentick);
                                                imageView.setVisibility(View.VISIBLE);
                                            }

                                        else if (status.equals("On the Way")) {
                                                // Display On the Way status and time
                                                textView1222.setText(status);
                                                textView12344.setText(time);
                                                textView1222.setVisibility(View.VISIBLE);
                                                textView12344.setVisibility(View.VISIBLE);

                                                imageView11.setImageResource(R.drawable.blacktick);
                                                imageView11.setVisibility(View.VISIBLE);
                                            } else if (status.equals("Parcel Collected")) {
                                                // Display Parcel Collected status and time
                                                textView122233.setText(status);
                                                textView1233344.setText(time);
                                                textView122233.setVisibility(View.VISIBLE);
                                                textView1233344.setVisibility(View.VISIBLE);

                                                imageView1133.setImageResource(R.drawable.blacktick);
                                                imageView1133.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                } else {
                                    // No detailed status found for the booking ID
                                    imageView.setImageResource(R.drawable.orange);
                                    imageView1133.setImageResource(R.drawable.orange);
                                    imageView11.setImageResource(R.drawable.orange);





                                    showToast("No detailed status found for this booking ID");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                                showToast("Database Error: " + error.getMessage());
                            }
                        });

                        // Make TextViews visible
                        RecipientName.setVisibility(View.VISIBLE);
                        trackingid.setVisibility(View.VISIBLE);
                        source.setVisibility(View.VISIBLE);
                        destination.setVisibility(View.VISIBLE);
                        selecteddate.setVisibility(View.VISIBLE);

                        leftTextView.setVisibility(View.VISIBLE);
                        dividerView.setVisibility(View.VISIBLE);
                        dividerView1.setVisibility(View.VISIBLE);
                        dividerView2.setVisibility(View.VISIBLE);
                        newView.setVisibility(View.VISIBLE);
                        FromTextView.setVisibility(View.VISIBLE);
                        ToTextView.setVisibility(View.VISIBLE);
                        Recipient.setVisibility(View.VISIBLE);
                        EstDelivery.setVisibility(View.VISIBLE);

                        textView1.setVisibility(View.VISIBLE);
                        textView124.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);

                        textView12344.setVisibility(View.VISIBLE);
                        textView122233.setVisibility(View.VISIBLE);
                        textView11112.setVisibility(View.VISIBLE);
                        textView1222.setVisibility(View.VISIBLE);
                        textView1233344.setVisibility(View.VISIBLE);
                        textView1111222.setVisibility(View.VISIBLE);

                        imageView.setVisibility(View.VISIBLE);
                        imageView11.setVisibility(View.VISIBLE);
                        imageView1133.setVisibility(View.VISIBLE);

//                        imageViewsss.setVisibility(View.VISIBLE);
//                        imageViewsskks.setVisibility(View.VISIBLE);
//                        imageViewsskksju.setVisibility(View.VISIBLE);

                        return; // Exit loop after finding the first matching tracking ID
                    }
                } else {
                    // Show toast for invalid tracking ID
                    // showToast("Invalid Tracking ID");
                    // Hide TextViews
                    hideTextViews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                showToast("Database Error: " + databaseError.getMessage());
                // Hide TextViews
                hideTextViews();
            }
        });
    }


    private void hideTextViews() {
        RecipientName.setVisibility(View.GONE);
        trackingid.setVisibility(View.GONE);
        source.setVisibility(View.GONE);
        destination.setVisibility(View.GONE);
        selecteddate.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView1222.setVisibility(View.GONE);

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
