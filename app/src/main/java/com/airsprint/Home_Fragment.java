package com.airsprint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airsprint.Adapters.DeliveryAdapter;
import com.airsprint.Models.Delivery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.text.Editable;
import android.text.TextWatcher;
public class Home_Fragment extends Fragment {
    ImageView bell;
    ImageView qrcodeimage;
    RecyclerView recyclerView;
    DeliveryAdapter deliveryAdapter;
    List<Delivery> deliveryList;
    EditText searchtext;
    String bookingId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bell = view.findViewById(R.id.bell);
        qrcodeimage = view.findViewById(R.id.qrcodeimage);
        searchtext = view.findViewById(R.id.searchtext);

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notification_Activity.class);
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

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        deliveryList = new ArrayList<>();
        deliveryAdapter = new DeliveryAdapter(getContext(), deliveryList);
        recyclerView.setAdapter(deliveryAdapter);

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim();
                filterDeliveryList(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Retrieve user's ID from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_details", getActivity().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        // Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("booking");
        Query query = databaseReference.orderByChild("userid").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deliveryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Delivery delivery = dataSnapshot.getValue(Delivery.class);
                    if (delivery != null) {
                        deliveryList.add(delivery);
                        bookingId = delivery.getBookingId();
                    }
                }
                deliveryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchtext != null && TextUtils.isEmpty(searchtext.getText().toString().trim())) {
            deliveryAdapter.setData(deliveryList);
        }
    }

    private void filterDeliveryList(String searchText) {
        List<Delivery> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(searchText)) {
            filteredList.addAll(deliveryList);
        } else {
            for (Delivery delivery : deliveryList) {
                if (delivery.getTrackingId().contains(searchText)) {
                    filteredList.add(delivery);
                }
            }
        }
        deliveryAdapter.setData(filteredList);
    }
}
