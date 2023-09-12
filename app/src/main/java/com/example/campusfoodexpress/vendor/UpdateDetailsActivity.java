package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.WelcomeActivity;
import com.example.campusfoodexpress.dialogs.InteractiveDialog;
import com.example.campusfoodexpress.dialogs.LoadingDialog;

import database.DatabaseHelper;

public class UpdateDetailsActivity extends AppCompatActivity {

    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,edtClosestBuilding,edtBusinessDescription;
    Button btnDeleteAccount,btnSave,btnCancel;
    String businessID,businessName,businessContactNumber,businessLocation,businessHours,businessDescription,pickOpeningTime,closingTime,username = "";
    LoadingDialog loadingDialog;
    LoadingDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_vendor);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtContactNumber = findViewById(R.id.edtContactNumberCustomer);
        pickStartTime = findViewById(R.id.OpeningTime);
        pickEndTime = findViewById(R.id.closingTime);
        edtClosestBuilding = findViewById(R.id.edtClosestBuilding);
        edtBusinessDescription = findViewById(R.id.edtBusinessDescriptionUpdate);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {

            username = intent.getStringExtra("username");
            DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
            VendorData vendorData = dbHelper.getVendorDataByUsername(username);
            businessID = String.valueOf(vendorData.getBusinessID());
            if (vendorData != null) {
                String businessHours = vendorData.getBusinessHours();
                String[] parts = businessHours.split(" To "); // Split the string into parts based on " To "

                if (parts.length == 2) {
                    String closingTime = parts[1]; // The second part should be the closing time
                    String openingTime = parts[0].replace("Operational Hours: ", ""); // Remove the prefix
                    pickStartTime.setText(openingTime);
                    pickEndTime.setText(closingTime);
                }
                edtBusinessName.setText(vendorData.getBusinessName());
                edtContactNumber.setText(vendorData.getBusinessContactNumber());
                edtClosestBuilding.setText(vendorData.getBusinessLocation());
                edtBusinessDescription.setText(vendorData.getBusinessBio());

            }
        }

    }

    public void onTimeInputClicked(View view) {
        EditText currentTimeClicked = (EditText) view;
        int hour = 0; // Default hour value
        int minute = 0; // Default minute value

        // Get the current time from the EditText, if available
        String currentTime = currentTimeClicked.getText().toString();
        if (!currentTime.isEmpty()) {
            String[] parts = currentTime.split(":");
            if (parts.length == 2) {
                hour = Integer.parseInt(parts[0]);
                minute = Integer.parseInt(parts[1]);
            }
        }

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Update the EditText with the selected time
                        String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        currentTimeClicked.setText(formattedTime);
                    }
                },
                hour,
                minute,
                DateFormat.is24HourFormat(this)
        );

        timePickerDialog.show();
    }

    public void onSaveClicked(View view) { //Implement this

        String openingTime = pickStartTime.getText().toString().trim();
        String closingTime = pickEndTime.getText().toString().trim();

        if(edtBusinessName.getText().toString().equals("")){
            edtBusinessName.setError("Business name is required!");
            return;
        }
        else {
            edtBusinessName.setError(null);
        }

        if(edtContactNumber.getText().toString().trim().equals("")){
            edtContactNumber.setError("Business contact number is required!");
            return;
        }
        else {
            edtContactNumber.setError(null);
        }

        if(pickStartTime.getText().toString().trim().equals("")){
            pickStartTime.setError("Opening time is required!");
            return;
        }
        else {
            pickStartTime.setError(null);
        }

        if(pickEndTime.getText().toString().trim().equals("")){
            pickEndTime.setError("Closing time is required!");
            return;
        }
        else {
            pickEndTime.setError(null);
        }
        if(edtBusinessDescription.getText().toString().trim().equals("")){
            edtBusinessDescription.setError("Business description is required!");
            return;
        }
        else {
            edtBusinessDescription.setError(null);
        }
        if(edtClosestBuilding.getText().toString().trim().equals("")){
            edtClosestBuilding.setError("Business location is required!");
            return;
        }
        else {
            edtClosestBuilding.setError(null);
        }

        // Check if startTime is not greater or equal to endTime
        if (isStartTimeAfterEndTime(pickStartTime.getText().toString().trim(),pickEndTime.getText().toString().trim())) {

            pickStartTime.setError("Start time cannot be after or equal to end time");
            pickEndTime.setError("End time cannot be before or equal to start time");
            return;
        } else {
            pickStartTime.setError(null); // Clear any previous error
            pickEndTime.setError(null); // Clear any previous error
        }


        // Validate contactNumber (assuming 10-digit phone number)
        if (!isValidContactNumber(edtContactNumber.getText().toString().trim())) {
            edtContactNumber.setError("Invalid contact number format (10 digits required)");
            return;
        } else {
            edtContactNumber.setError(null); // Clear any previous error
        }

        DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
        dbHelper.updateVendorDetails(
                username,
                edtBusinessName.getText().toString().trim(),
                edtContactNumber.getText().toString().trim(),
                edtClosestBuilding.getText().toString().trim(),
                edtBusinessDescription.getText().toString().trim(),
                openingTime,
                closingTime
        );
       Intent intent = new Intent(UpdateDetailsActivity.this,MainActivity.class);
       intent.putExtra("Saving",username);
       startActivity(intent);
       finish();
    }

    private boolean isValidContactNumber(String contactNumber) {
        return contactNumber.matches("\\d{10}");
    }

    private boolean isStartTimeAfterEndTime(String startTime, String endTime) {
        try {
            // Parse time strings to get hours and minutes
            String[] startTimeParts = startTime.split(":");
            String[] endTimeParts = endTime.split(":");
            int startHour = Integer.parseInt(startTimeParts[0]);
            int startMinute = Integer.parseInt(startTimeParts[1]);
            int endHour = Integer.parseInt(endTimeParts[0]);
            int endMinute = Integer.parseInt(endTimeParts[1]);

            // Compare hours and minutes
            if (startHour > endHour) {
                return true;
            } else if (startHour == endHour && startMinute >= endMinute) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true; // In case of any parsing error, consider the times invalid
        }
    }
    
    public void onCancelClicked(View view) {
        Intent intent = new Intent(UpdateDetailsActivity.this, MainActivity.class);
        intent.putExtra("Cancelled",username);
        startActivity(intent);
        finish();
    }

    public void onDeleteAccountClicked(View view) {
        InteractiveDialog interactiveDialog = new InteractiveDialog(this);
        interactiveDialog.startInteractiveDialog();

        // Set click listener for the "Yes" button in the confirmation dialog
        interactiveDialog.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the confirmation dialog
                interactiveDialog.dismissDialog();
                // Show the loading dialog
                LoadingDialog loadingDialog = new LoadingDialog(UpdateDetailsActivity.this, "Deleting Account...");
                loadingDialog.startLoadingDialog();
                // Delete the account in the background
                if (!username.equals("")) {
                    DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
                    boolean isDeleted = dbHelper.deleteVendor(username);
                    boolean paymentReset = dbHelper.deletePaymentOptions(username);
                    boolean removeMenu = dbHelper.deleteVendorMenu(username);
                    if (isDeleted && paymentReset && removeMenu) {
                        edtBusinessName.setText("");
                        edtContactNumber.setText("");
                        edtClosestBuilding.setText("");
                        edtBusinessDescription.setText("");
                        pickStartTime.setText("");
                        pickEndTime.setText("");
                        setResult(RESULT_OK);
                        Intent intent = new Intent(UpdateDetailsActivity.this, WelcomeActivity.class);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Clear input fields
                                loadingDialog.dismissDialog();
                                Toast.makeText(view.getContext(),"Account Deleted successfully!",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                        }, 5000);
                    }
                }
            }
        });

        interactiveDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked "No," dismiss the confirmation dialog
                interactiveDialog.dismissDialog();
            }
        });
    }
}