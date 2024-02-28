package com.airsprint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Profile_Fragment extends Fragment {

    TextView logout, Nametextview, Phonetextview, Emailtextview, Usernametextview, passwordtextview;
    ImageView leftImageView, edit;
    ProgressBar progressBar;
    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logout = view.findViewById(R.id.logout);
        edit = view.findViewById(R.id.edit);
        Nametextview = view.findViewById(R.id.Nametextview);
        Phonetextview = view.findViewById(R.id.Phonetextview);
        Emailtextview = view.findViewById(R.id.Emailtextview);
        Usernametextview = view.findViewById(R.id.Usernametextview);
        passwordtextview = view.findViewById(R.id.passwordtextview);
        leftImageView = view.findViewById(R.id.leftImageView);
        progressBar = view.findViewById(R.id.progressBar);

        logout.setOnClickListener(v -> logoutUser());

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), EditProfileActivity.class);
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
        });

        loadProfile(); // Load profile details when the fragment is created
    }

    private void loadProfile() {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar while loading
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_details", MODE_PRIVATE);
        String Name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String username = sharedPreferences.getString("username", "");
        String phone = sharedPreferences.getString("phone", "");
        String password = sharedPreferences.getString("password", "");
        String imageurl = sharedPreferences.getString("imageurl", "");

        Nametextview.setText(Name);
        Phonetextview.setText(phone);
        Emailtextview.setText(email);
        Usernametextview.setText(username);
        passwordtextview.setText(password);

        if (imageurl != null && !imageurl.isEmpty()) {
            Picasso.get().load(imageurl)
                    .placeholder(R.drawable.authorrr) // Placeholder image while loading
                    .error(R.drawable.authorrr) // Image to show if loading fails
                    .into(leftImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Hide progress bar when image successfully loaded
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            // Hide progress bar when image loading fails
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            // Hide progress bar if no image URL is available
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Handle the updated data received from EditProfileActivity
            loadProfile(); // Reload profile details including image
        }
    }

    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> logout());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Clear all preferences, including the switch state
        editor.clear();
        editor.apply();

        // Navigate to the login page
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        requireActivity().finish();
    }
}
