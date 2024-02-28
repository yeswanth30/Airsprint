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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airsprint.Adapters.NotificationAdapter;
import com.airsprint.Models.NotificationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Notification_Activity extends AppCompatActivity {

    ImageView back,bell;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        back=findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Notification_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");

        Log.e("userid","userid:"+ userid);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<NotificationModel> notificationList = new ArrayList<>();
        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        DatabaseReference notificationRef = FirebaseDatabase.getInstance().getReference("notificationuser");
        Query query = notificationRef.orderByChild("userId").equalTo(userid);
        Log.e("userid","userisdfsdbfiud:"+ userid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String bookingId = snapshot.child("bookingId").getValue(String.class);
                    String notificationMessage = snapshot.child("notificationMessage").getValue(String.class);
                    String time  = snapshot.child("time").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    int status = snapshot.child("status").getValue(int.class);
                    Log.e("userid","yesh:"+ notificationMessage);

                    NotificationModel notification = new NotificationModel(bookingId, notificationMessage,time,date,status);
                    notificationList.add(notification);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching notification data", databaseError.toException());
                // Handle error
            }
        });
    }}










