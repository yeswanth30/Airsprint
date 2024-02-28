package com.airsprint.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.airsprint.Step1Fragment;
import com.airsprint.Step3Fragment;
import com.airsprint.Step2Fragment;

public class BookingPagerAdapter extends FragmentPagerAdapter {

    public BookingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
                return new Step1Fragment();
//            case 1:
//                return new Step2Fragment();
//            case 2:
//                return new Step3Fragment();
//            default:
//                return null;
        }


    @Override
    public int getCount() {
        return 1; // Three fragments
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
                return "";
//            case 1:
//                return "Step 2";
//            case 2:
//                return "Step 3";
//            default:
//                return "";
        }
    }

