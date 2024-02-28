package com.airsprint;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Step1Fragment extends Fragment {
    private EditText sourceEditText;
    private EditText destinationEditText;
    private Spinner destinationSpinner; // Changed from EditText to Spinner

    private EditText detailedAddressEditText;
    private RadioGroup timeRadioGroup;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText widthEditText;
    private Button calculateButton;
    private Button selectDateButton;
    private TextView step1TotalAmountTextView,sourceErrorTextView,WidthErrorTextView,WeightErrorTextView,HeightErrorTextView,detailedErrorTextView;
    private Button selectTimeButton; // New button for saving only
 TextView saveButton;
    // Map to store city prices
    private Map<String, Integer> cityPrices;

    // Firebase
    private DatabaseReference mDatabase;
    private List<String> cityList;


    // Date
    private Calendar calendar;
    private int year, month, day;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step1_fragment, container, false);
        cityList = new ArrayList<>();
        cityList.add("Agra");
        cityList.add("Ahmedabad");
        cityList.add("Allahabad");
        cityList.add("Amritsar");
        cityList.add("Aurangabad");
        cityList.add("Banglore");
        cityList.add("Bhopal");
        cityList.add("Chandigarh");
        cityList.add("Chennai");
        cityList.add("Coimbatore");
        cityList.add("Delhi");
        cityList.add("Faridabad");
        cityList.add("Ghaziabad");
        cityList.add("Goa");
        cityList.add("Gurgaon");
        cityList.add("Guwahati");
        cityList.add("Hyderabad");
        cityList.add("Indore");
        cityList.add("Jaipur");
        cityList.add("Jodhpur");
        cityList.add("Kanpur");
        cityList.add("Kochi");
        cityList.add("Kolkata");
        cityList.add("Lucknow");
        cityList.add("Ludhiana");
        cityList.add("Madurai");
        cityList.add("Mangalore");
        cityList.add("Mumbai");
        cityList.add("Nagpur");
        cityList.add("Nashik");
        cityList.add("Noida");
        cityList.add("Patna");
        cityList.add("Pune");
        cityList.add("Raipur");
        cityList.add("Rajkot");
        cityList.add("Ranchi");
        cityList.add("Shimla");
        cityList.add("Srinagar");
        cityList.add("Surat");
        cityList.add("Thane");
        cityList.add("Vadodara");
        cityList.add("Varanasi");
        cityList.add("Vijayawada");
        cityList.add("Visakhapatnam");

        // Initialize views
        sourceEditText = view.findViewById(R.id.sourceEditText);
//        destinationEditText = view.findViewById(R.id.destinationEditText);
        destinationSpinner = view.findViewById(R.id.destinationSpinner); // Changed from EditText to Spinner


        detailedAddressEditText = view.findViewById(R.id.detailedAddressEditText);
        timeRadioGroup = view.findViewById(R.id.timeRadioGroup);
        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        widthEditText = view.findViewById(R.id.widthEditText);
        selectTimeButton = view.findViewById(R.id.selectTimeButton);
        selectDateButton = view.findViewById(R.id.selectDateButton);
        step1TotalAmountTextView = view.findViewById(R.id.step1TotalAmountTextView);
        sourceErrorTextView = view.findViewById(R.id.sourceErrorTextView);
        detailedErrorTextView = view.findViewById(R.id.detailedErrorTextView);
        HeightErrorTextView = view.findViewById(R.id.HeightErrorTextView);
        WeightErrorTextView = view.findViewById(R.id.WeightErrorTextView);
        WidthErrorTextView = view.findViewById(R.id.WidthErrorTextView);

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    sourceEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    sourceErrorTextView.setVisibility(View.GONE);
                    sourceErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        detailedAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    detailedAddressEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    detailedErrorTextView.setVisibility(View.GONE);
                    detailedErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    heightEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    HeightErrorTextView.setVisibility(View.GONE);
                    HeightErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    weightEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    WeightErrorTextView.setVisibility(View.GONE);
                    WeightErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        widthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    widthEditText.setBackgroundResource(R.drawable.rectangle_signup);
                    WidthErrorTextView.setVisibility(View.GONE);
                    WidthErrorTextView.setVisibility(View.GONE); // If previously shown
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        saveButton = view.findViewById(R.id.saveButton); // Initialize save button

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize city prices
        initializeCityPrices();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(adapter);
        // Initialize date picker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);



        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        timeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.scheduleRadioButton) {
                    selectTimeButton.setVisibility(View.VISIBLE);
                    selectDateButton.setVisibility(View.VISIBLE);
                } else {
                    selectTimeButton.setVisibility(View.GONE);
                    selectDateButton.setVisibility(View.GONE);
                }
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        // Set onClickListener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebaseAndNavigate();
            }
        });

        return view;
    }



    private void initializeCityPrices() {
        cityPrices = new HashMap<>();
        cityPrices.put("Agra", 450);
        cityPrices.put("Ahmedabad", 400);
        cityPrices.put("Allahabad", 480);
        cityPrices.put("Amritsar", 320);
        cityPrices.put("Aurangabad", 230);
        cityPrices.put("Banglore", 300);
        cityPrices.put("Bhopal", 470);
        cityPrices.put("Chandigarh", 410);
        cityPrices.put("Chennai", 250);
        cityPrices.put("Coimbatore", 360);
        cityPrices.put("Delhi", 300);
        cityPrices.put("Faridabad", 190);
        cityPrices.put("Ghaziabad", 480);
        cityPrices.put("Goa", 290);
        cityPrices.put("Gurgaon", 370);
        cityPrices.put("Guwahati", 210);
        cityPrices.put("Hyderabad", 100);
        cityPrices.put("Indore", 480);
        cityPrices.put("Jaipur", 340);
        cityPrices.put("Jodhpur", 250);
        cityPrices.put("Kanpur", 420);
        cityPrices.put("Kochi", 170);
        cityPrices.put("Kolkata", 250);
        cityPrices.put("Lucknow", 330);
        cityPrices.put("Ludhiana", 210);
        cityPrices.put("Madurai", 160);
        cityPrices.put("Mangalore", 280);
        cityPrices.put("Mumbai", 300);
        cityPrices.put("Nagpur", 270);
        cityPrices.put("Nashik", 380);
        cityPrices.put("Noida", 480);
        cityPrices.put("Patna", 220);
        cityPrices.put("Pune", 290);
        cityPrices.put("Raipur", 370);
        cityPrices.put("Rajkot", 470);
        cityPrices.put("Ranchi", 390);
        cityPrices.put("Shimla", 370);
        cityPrices.put("Srinagar", 370);
        cityPrices.put("Surat", 240);
        cityPrices.put("Thane", 210);
        cityPrices.put("Vadodara", 160);
        cityPrices.put("Varanasi", 340);
        cityPrices.put("Vijayawada", 320);
        cityPrices.put("Visakhapatnam", 130);
    }


    private void saveToFirebaseAndNavigate() {
        if (validateFields()) {
            if (validateWeight()) {
                // Check if any radio button is selected
                if (timeRadioGroup.getCheckedRadioButtonId() == -1) {
                    // No radio button is selected, show a toast message
                    Toast.makeText(getActivity(), "Please select a delivery option", Toast.LENGTH_SHORT).show();
                    return; // Exit method
                }
                // Continue processing if a radio button is selected
                if (timeRadioGroup.getCheckedRadioButtonId() == R.id.expressRadioButton) {
                    // For express delivery, store current timestamp as selected date
                    String selectedDate = getCurrentTimestamp("dd/MM/yyyy ");
                    String selectedtime = getCurrentTimestamp("HH:mm:ss");
                    if (storeBookingDetails(selectedDate, selectedtime)) {
                        // Booking details were successfully stored, navigate to Step2Fragment
                        passBookingIdToStep2Fragment();
                    }
                } else {
                    // For schedule delivery, check if a date is selected
                    if (!selectDateButton.getText().toString().isEmpty()) {
                        String selectedDate = selectDateButton.getText().toString().trim();
                        String selectedtime = selectTimeButton.getText().toString().trim();
                        // Store selected date in Firebase
                        if (storeBookingDetails(selectedDate, selectedtime)) {
                            // Booking details were successfully stored, navigate to Step2Fragment
                            passBookingIdToStep2Fragment();
                        }
                    } else {
                        // If date is not selected, show a toast
                        Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // If weight is not valid, show a toast
                Toast.makeText(getActivity(), "Weight cannot exceed 100", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If fields are not valid, show a toast
            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }



    // Get price for the entered destination
    private int getDestinationPrice(String destination) {
        // Check if the entered destination exists in the city prices map
        if (cityPrices.containsKey(destination)) {
            return cityPrices.get(destination);
        } else {
            // If the entered destination does not exist, show a toast message
            Toast.makeText(getActivity(), "Selected city does not have parcel delivery service", Toast.LENGTH_SHORT).show();
            return 0; // Default price if destination is not found
        }
    }

    // Calculate price based on weight
    private int calculateWeightCharge(int weight) {
        // Determine the weight range and calculate the charge accordingly
        if (weight >= 1 && weight <= 10) {
            return 100;
        } else if (weight > 10 && weight <= 20) {
            return 200;
        } else if (weight > 20 && weight <= 30) {
            return 300;
        } else if (weight > 30 && weight <= 40) {
            return 400;
        } else if (weight > 40 && weight <= 50) {
            return 500;
        } else if (weight > 50 && weight <= 60) {
            return 600;
        } else if (weight > 60 && weight <= 70) {
            return 700;
        } else if (weight > 70 && weight <= 80) {
            return 800;
        } else if (weight > 80 && weight <= 90) {
            return 900;
        } else if (weight > 90 && weight <= 100) {
            return 1000;
        } else {
            // Weight above 40 not accepted, return 0 or handle it accordingly
            return 0;
        }
    }


    private void openDatePicker() {
        // Get today's date
        Calendar todayCalendar = Calendar.getInstance();

        // Create date picker dialog with minimum date set to today
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                dateSetListener,
                year, month, day);

        // Set the minimum date for the date picker
        datePickerDialog.getDatePicker().setMinDate(todayCalendar.getTimeInMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }


    // Date set listener for date picker
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Update selected date
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Display selected date
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            selectDateButton.setText(selectedDate);
        }
    };


    // Store booking details in Firebase and return bookingId
    private boolean storeBookingDetails(String selectedDate,String selectedTime) {
        String enteredDestination = destinationSpinner.getSelectedItem().toString().trim();
        if (!cityPrices.containsKey(enteredDestination)) {
            // Destination city is not listed, return false indicating failure
            Toast.makeText(getActivity(), "Selected city does not have parcel delivery service", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Get entered details
        String enteredSource = sourceEditText.getText().toString().trim();
       // String enteredDestination = destinationEditText.getText().toString().trim();
        String enteredDetailedAddress = detailedAddressEditText.getText().toString().trim();
        int height = Integer.parseInt(heightEditText.getText().toString().trim());
        int weight = Integer.parseInt(weightEditText.getText().toString().trim());
        int width = Integer.parseInt(widthEditText.getText().toString().trim());

        // Get prices
        int destinationPrice = getDestinationPrice(enteredDestination);
        int additionalCharge = timeRadioGroup.getCheckedRadioButtonId() == R.id.expressRadioButton ? 500 : 300;
        int weightCharge = calculateWeightCharge(weight);
        int totalAmount = destinationPrice + additionalCharge + weightCharge;

        // Get additional amount mode
        String additionalAmountMode;
        if (timeRadioGroup.getCheckedRadioButtonId() == R.id.expressRadioButton) {
            additionalAmountMode = "Express";
        } else {
            additionalAmountMode = "Schedule";
        }

        // Store in Firebase
        String bookingId = mDatabase.child("booking").push().getKey();
        saveBookingIdToSharedPreferences(bookingId);

        Map<String, Object> bookingDetails = new HashMap<>();
        bookingDetails.put("bookingId", bookingId);
        bookingDetails.put("source", enteredSource);
        bookingDetails.put("destination", enteredDestination);
        bookingDetails.put("detailedAddress", enteredDetailedAddress);
        bookingDetails.put("height", height);
        bookingDetails.put("weight", weight);
        bookingDetails.put("width", width);
        bookingDetails.put("destinationPrice", destinationPrice);
        bookingDetails.put("additionalCharge", additionalCharge);
        bookingDetails.put("weightCharge", weightCharge);
        bookingDetails.put("totalAmount", totalAmount);
        bookingDetails.put("selectedDate", selectedDate);
        bookingDetails.put("selectedTime", selectedTime); // Store selected time

        bookingDetails.put("additionalAmountMode", additionalAmountMode);

        mDatabase.child("booking").child(bookingId).setValue(bookingDetails);
        Toast.makeText(getActivity(), "Booking details saved successfully", Toast.LENGTH_SHORT).show();

        return true;

    }

    // Save booking ID to SharedPreferences
    private void saveBookingIdToSharedPreferences(String bookingId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BookingPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bookingId", bookingId);
        editor.apply();
    }

    // Navigate to Step2Fragment only if destination city exists in city prices map
    private void passBookingIdToStep2Fragment() {
        String bookingId = ""; // You need to generate the bookingId here or get it from somewhere else
        Bundle bundle = new Bundle();
        bundle.putString("bookingId", bookingId); // Pass the booking ID to Step2Fragment

        // Create an instance of Step2Fragment and set the arguments
        Step2Fragment step2Fragment = new Step2Fragment();
        step2Fragment.setArguments(bundle);

        // Perform the fragment transaction
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, step2Fragment)
                .addToBackStack(null)
                .commit();
    }

    // Validate weight input
    private boolean validateWeight() {
        try {
            int weight = Integer.parseInt(weightEditText.getText().toString().trim());
            return weight <= 100;
        } catch (NumberFormatException e) {
            return false; // Handle non-integer input
        }
    }


    // Validate input fields
    // Validate input fields
    private boolean validateFields() {
        boolean isValid = true;

        // Reset border color and error messages
        sourceEditText.setBackgroundResource(R.drawable.edit_text_border);
        detailedAddressEditText.setBackgroundResource(R.drawable.edit_text_border);
        heightEditText.setBackgroundResource(R.drawable.edit_text_border);
        weightEditText.setBackgroundResource(R.drawable.edit_text_border);
        widthEditText.setBackgroundResource(R.drawable.edit_text_border);

        // Check source field
        String source = sourceEditText.getText().toString().trim();
        if (source.isEmpty()) {
            sourceErrorTextView.setText("Please enter source destination");
            sourceErrorTextView.setVisibility(View.VISIBLE);
            sourceEditText.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            isValid = false;
        } else {
            sourceErrorTextView.setVisibility(View.GONE);
            sourceEditText.setBackgroundResource(R.drawable.edit_text_border); // Reset background to default
        }


        String destination = destinationSpinner.getSelectedItem().toString().trim();

        String detailedAddress = detailedAddressEditText.getText().toString().trim();
        if (detailedAddress.isEmpty()) {
            detailedErrorTextView.setText("Please enter source destination detailed address");
            detailedErrorTextView.setVisibility(View.VISIBLE);
            detailedAddressEditText.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            isValid = false;
        }
        else {
            detailedErrorTextView.setVisibility(View.GONE);
            detailedAddressEditText.setBackgroundResource(R.drawable.edit_text_border); // Reset background to default
        }

        // Check height field
        String height = heightEditText.getText().toString().trim();
        if (height.isEmpty()) {
            HeightErrorTextView.setText("Please enter Height");
            HeightErrorTextView.setVisibility(View.VISIBLE);
            heightEditText.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            isValid = false;
        }
        else {
            HeightErrorTextView.setVisibility(View.GONE);
            heightEditText.setBackgroundResource(R.drawable.edit_text_border); // Reset background to default
        }


        // Check weight field
        String weight = weightEditText.getText().toString().trim();
        if (weight.isEmpty()) {
            WeightErrorTextView.setText("Please enter Weight");
            WeightErrorTextView.setVisibility(View.VISIBLE);
            weightEditText.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            isValid = false;
        }
        else {
            WeightErrorTextView.setVisibility(View.GONE);
            weightEditText.setBackgroundResource(R.drawable.edit_text_border); // Reset background to default
        }

        // Check width field
        String width = widthEditText.getText().toString().trim();
        if (width.isEmpty()) {
            WidthErrorTextView.setText("Please enter Width");
            WidthErrorTextView.setVisibility(View.VISIBLE);
            widthEditText.setBackgroundResource(R.drawable.edittext_border_red); // Change border color to red
            isValid = false;
        }
        else {
            WidthErrorTextView.setVisibility(View.GONE);
            widthEditText.setBackgroundResource(R.drawable.edit_text_border); // Reset background to default
        }

        // Show error message if any field is empty
        if (!isValid) {
            Toast.makeText(getActivity(), "Please fill all the required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }


    private void openTimePicker() {
        final Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Format the selected time
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                // Set the selected time to the button text
                selectTimeButton.setText(selectedTime);
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    private String getCurrentTimestamp(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }
}





