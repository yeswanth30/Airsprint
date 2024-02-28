package com.airsprint;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class Step3Fragment extends Fragment {

    private DatabaseReference mDatabase;
    private TextView mSenderNameTextView;
    private TextView mRecipientNameTextView;
    private TextView mSenderMobileTextView;
    private TextView mRecipientMobileTextView;
    private TextView mSenderMessageTextView;
    private TextView mRecipientMessageTextView;
    private TextView mPackageTypeTextView;
    private TextView FromTextAddress;
    private TextView ToTextViewAddress;
    private TextView selecteddate;
    private TextView Dimensions;
    private TextView totalAmountTextView;
    private TextView Additionalamountmode;
    private Spinner deliveryModeSpinner;
    private ArrayAdapter<CharSequence> deliveryModeAdapter;
    private TextView saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.step3_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSenderNameTextView = view.findViewById(R.id.SenderName);
        mRecipientNameTextView = view.findViewById(R.id.RecipientName);
        mSenderMobileTextView = view.findViewById(R.id.SenderMobile);
        mRecipientMobileTextView = view.findViewById(R.id.RecipientMobile);
        mSenderMessageTextView = view.findViewById(R.id.SenderMessage);
        mRecipientMessageTextView = view.findViewById(R.id.RecipientMessage);
        mPackageTypeTextView = view.findViewById(R.id.Pacakgetype);
        FromTextAddress = view.findViewById(R.id.FromTextAddress);
        Dimensions = view.findViewById(R.id.Dimensions);
        ToTextViewAddress = view.findViewById(R.id.ToTextViewAddress);
        selecteddate = view.findViewById(R.id.selecteddate);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        saveButton = view.findViewById(R.id.saveButton);
        deliveryModeSpinner = view.findViewById(R.id.deliveryModeSpinner);
        Additionalamountmode = view.findViewById(R.id.Additionalamountmode);

        deliveryModeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.delivery_modes_array, android.R.layout.simple_spinner_item);
        deliveryModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryModeSpinner.setAdapter(deliveryModeAdapter);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("bookingId")) {
            String bookingId = bundle.getString("bookingId");
            if (bookingId != null) {
                queryBookingDetails(bookingId);

            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdditionalDetails();
            }
        });
    }

    private void queryBookingDetails(String bookingId) {
        DatabaseReference bookingRef = mDatabase.child("booking").child(bookingId);
        bookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String senderName = dataSnapshot.child("senderName").getValue(String.class);
                    String recipientName = dataSnapshot.child("receiverName").getValue(String.class);
                    String senderMobile = dataSnapshot.child("senderMobile").getValue(String.class);
                    String recipientMobile = dataSnapshot.child("receiverMobile").getValue(String.class);
                    String senderMessage = dataSnapshot.child("senderMessage").getValue(String.class);
                    String recipientMessage = dataSnapshot.child("receiverMessage").getValue(String.class);
                    String packageType = dataSnapshot.child("packageType").getValue(String.class);
                    String FromTextAddress1 = dataSnapshot.child("source").getValue(String.class);
                    String ToTextViewAddress1 = dataSnapshot.child("destination").getValue(String.class);
                    String selecteddate1 = dataSnapshot.child("selectedDate").getValue(String.class);
                    String Additionalamountmodei = dataSnapshot.child("additionalAmountMode").getValue(String.class);
                    Long height = dataSnapshot.child("height").getValue(Long.class);
                    Long weight = dataSnapshot.child("weight").getValue(Long.class);
                    Long width = dataSnapshot.child("width").getValue(Long.class);
                    String ToTextViewAddressss11 = dataSnapshot.child("detailedAddress").getValue(String.class);

                    mSenderNameTextView.setText(senderName);
                    mRecipientNameTextView.setText(recipientName);
                    mSenderMobileTextView.setText(senderMobile);
                    mRecipientMobileTextView.setText(recipientMobile);
                    mSenderMessageTextView.setText(senderMessage);
                    mRecipientMessageTextView.setText(recipientMessage);
                    String packageId = "<b>Package Type:</b> " + packageType;
                    mPackageTypeTextView.setText(Html.fromHtml(packageId));
                    FromTextAddress.setText(FromTextAddress1);
                    ToTextViewAddress.setText(ToTextViewAddress1);
                    selecteddate.setText(selecteddate1);
                    Additionalamountmode.setText(Additionalamountmodei);

                    if (ToTextViewAddress1 != null && ToTextViewAddressss11 != null) {
                        String destinationAddress = ToTextViewAddress1 + ", " + ToTextViewAddressss11;
                        ToTextViewAddress.setText(destinationAddress);
                    }
                    else {
                      //  ToTextViewAddressss.setText("");
                    }

                    if (height != null && weight != null && width != null) {
                        String dimensions = "<b>Height:</b> " + height + " cm, <b>Weight:</b> " + weight + " cm, <b>Width:</b> " + width + " cm";
                        Dimensions.setText(Html.fromHtml(dimensions));
                    }


                    int destinationPrice = dataSnapshot.child("destinationPrice").getValue(Integer.class);
                    int additionalCharge = dataSnapshot.child("additionalCharge").getValue(Integer.class);
                    int weightCharge = dataSnapshot.child("weightCharge").getValue(Integer.class);
                    int totalAmount = destinationPrice + additionalCharge + weightCharge;
                    int gst = 0;
                    totalAmount += gst;
                    String totalAmountText = "<b>Destination:</b> " + ToTextViewAddress1 +
                            "<br><b>Destination Price:</b> " + destinationPrice +
                            "<br><b>Additional Charge:</b> " + additionalCharge +
                            "<br><b>Weight Charge:</b> " + weightCharge +
                            "<br><b>GST:</b> " + gst +
                            "<br><b>Total Amount:</b> Rs " + totalAmount;

                    totalAmountTextView.setText(Html.fromHtml(totalAmountText));
                    totalAmountTextView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void saveAdditionalDetails() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("bookingId")) {
            String bookingId = bundle.getString("bookingId");
            if (bookingId != null) {

                String deliveryMode = deliveryModeSpinner.getSelectedItem().toString();
                String userId = getUserIDFromSharedPreferences();
                String timeStamp = getCurrentTimeStamp("HH:mm");
                String dateStamp = getCurrentTimeStamp("dd-MM-yyyy");
                String totalAmountToBePaid = totalAmountTextView.getText().toString();
                String trackingId = generateTrackingID();

                DatabaseReference bookingRef = mDatabase.child("booking").child(bookingId);
                bookingRef.child("deliveryMode").setValue(deliveryMode);
                bookingRef.child("userid").setValue(userId);
                bookingRef.child("timeStamp").setValue(timeStamp);
                bookingRef.child("dateStamp").setValue(dateStamp);
                bookingRef.child("totalAmountToBePaid").setValue(totalAmountToBePaid);
                bookingRef.child("trackingId").setValue(trackingId);
                bookingRef.child("status").setValue("0");
                bookingRef.child("Deliveryboy").setValue("0");

                String additionalMessage = "Have a great day! Hope You Are Doing Well";
                String notificationMessage = "Booking done!  " + additionalMessage;
                sendFCMNotification(notificationMessage, bookingId, trackingId, userId);

                storeNotificationData(bookingId, trackingId, userId, 0, notificationMessage);
                sendNotification(notificationMessage);

                Toast.makeText(getActivity(), "Booking details saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
             //   listenForNotificationStatusChanges(bookingId);
            }
        }
    }

    private String getUserIDFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_details", MODE_PRIVATE);
        return sharedPreferences.getString("userid", "");
    }

    private String generateTrackingID() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 7; i++) {
            char c = characters.charAt(random.nextInt(characters.length()));
            sb.append(c);
        }
        return "#" + sb.toString();
    }

    private String getCurrentTimeStamp(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

    private void sendFCMNotification(String message, String bookingId, String trackingId, String userId) {
        String notificationMessage = "Booking done! Tracking ID: " + trackingId + "\n" +
                "Booking ID: " + bookingId + "\n" +
                "User ID: " + userId + "\n" +
                message;

        sendNotification(notificationMessage);
    }

    private void storeNotificationData(String bookingId, String trackingId, String userId, int status, String notificationMessage) {
        DatabaseReference notificationRef = mDatabase.child("notificationuser").push();
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("bookingId", bookingId);
        notificationData.put("trackingId", trackingId);
        notificationData.put("userId", userId);
        String timeStamp = getCurrentTimeStamp("dd-MM-yyyy hh:mm a");

        String[] parts = timeStamp.split(" ");
        String date = parts[0];
        String time = parts[1] + " " + parts[2];

        notificationData.put("time", time);
        notificationData.put("date", date);
        notificationData.put("notificationMessage", notificationMessage);
        notificationData.put("status", status);

        notificationRef.setValue(notificationData);
    }

    private void sendNotification(String message) {
        // Create a NotificationChannel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an intent to open the app when the notification is tapped
        Intent intent = new Intent(requireContext(), Step3Fragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Optional flags to clear activity stack

        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "channel_id")
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Order Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, builder.build());
    }



}
