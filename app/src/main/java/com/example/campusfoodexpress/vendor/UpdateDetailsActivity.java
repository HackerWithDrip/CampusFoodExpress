package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.SignupActivity;
import com.example.campusfoodexpress.WelcomeActivity;
import com.example.campusfoodexpress.dialogs.LoadingDialog;

import database.DatabaseHelper;

public class UpdateDetailsActivity extends AppCompatActivity {

    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,edtClosestBuilding,edtBusinessDescription;
    Button btnDeleteAccount,btnSave,btnCancel;
    String businessID,businessName,businessContactNumber,businessLocation,businessHours,businessDescription,pickOpeningTime,closingTime,username = "";
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_vendor);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        edtBusinessName = findViewById(R.id.edtFirstName);
        edtContactNumber = findViewById(R.id.edtContactNumberCustomer);
        pickStartTime = findViewById(R.id.edtLastName);
        pickEndTime = findViewById(R.id.pickEndTime);
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

        if (openingTime.isEmpty() || closingTime.isEmpty()) {
            Toast.makeText(this, "Please select both opening and closing times", Toast.LENGTH_SHORT).show();
            return;
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
        setResult(RESULT_OK); // Set the result to indicate success
        loadingDialog = new LoadingDialog(UpdateDetailsActivity.this, "Updating and Saving...");
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.showSuccessMessage("Saved Successfully!");
            }
        },2500);
    }

    
    public void onCancelClicked(View view) {  //Implement this
    }

    public void onDeleteAccountClicked(View view) {
        if (!username.equals("")){
            DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
           boolean isDeleted =  dbHelper.deleteVendor(username);
           if(isDeleted){
               Toast.makeText(this, "Account Deleted successfully!", Toast.LENGTH_LONG).show();
               edtBusinessName.setText("");
               edtContactNumber.setText("");
               edtClosestBuilding.setText("");
               edtBusinessDescription.setText("");
               pickStartTime.setText("");
               pickEndTime.setText("");
               setResult(RESULT_OK);
               Intent intent = new Intent(this, WelcomeActivity.class);
               startActivity(intent);
               finish();
           }
        }
    }
}