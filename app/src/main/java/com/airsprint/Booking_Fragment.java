package com.airsprint;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airsprint.Adapters.BookingPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class Booking_Fragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_activity, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        // Set up ViewPager
        BookingPagerAdapter adapter = new BookingPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}


