package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.campusfoodexpress.R;

import database.DatabaseHelper;

public class UpdateDetailsActivity extends AppCompatActivity {

    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,edtClosestBuilding,edtBusinessDescription;
    Button btnDeleteAccount,btnSave,btnCancel;
    String businessID,businessName,businessContactNumber,businessLocation,businessHours,businessDescription,pickOpeningTime,closingTime;

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

            String username = intent.getStringExtra("username");
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
//        getAndSetIntentData();

//        btnSave.setOnClickListener(view->{
//            DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
//            dbHelper.updateData(businessID, edtBusinessName.getText().toString().trim(),
//                    edtContactNumber.getText().toString().trim(),edtClosestBuilding.getText().toString().trim(),
//                    edtBusinessDescription.getText().toString().trim(),pickStartTime.getText().toString(),pickEndTime.getText().toString().trim());
//            setResult(RESULT_OK); // Set the result to indicate success
//            finish();
//        });


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

//        String openingTime = pickStartTime.getText().toString().trim();
//        String closingTime = pickEndTime.getText().toString().trim();
//
//        if (openingTime.isEmpty() || closingTime.isEmpty()) {
//            Toast.makeText(this, "Please select both opening and closing times", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
//        dbHelper.updateData(
//                businessID,
//                edtBusinessName.getText().toString().trim(),
//                edtContactNumber.getText().toString().trim(),
//                edtClosestBuilding.getText().toString().trim(),
//                edtBusinessDescription.getText().toString().trim(),
//                openingTime,
//                closingTime
//        );
//
//        setResult(RESULT_OK); // Set the result to indicate success
//        finish();
    }

    public void onCancelClicked(View view) {  //Implement this
    }


    void getAndSetIntentData(){

        Log.i("hasExtra",String.valueOf(getIntent().hasExtra("businessContactNumber")));

        if(getIntent().hasExtra("businessID") && getIntent().hasExtra("businessName") &&
                getIntent().hasExtra("businessContactNumber") &&
                getIntent().hasExtra("businessDescription")) {

            //Getting data from intent
            //businessID = getIntent().getStringExtra("businessID");
            businessName = getIntent().getStringExtra("businessName");
            businessContactNumber = getIntent().getStringExtra("businessContactNumber");
            businessDescription = getIntent().getStringExtra("businessDescription");
//            businessLocation = getIntent().getStringExtra("businessLocation");
//            businessHours = getIntent().getStringExtra("businessHours");

            //Setting intent data
            edtBusinessName.setText(businessName);
            edtContactNumber.setText(businessContactNumber);
//            edtClosestBuilding.setText(businessLocation);
            edtBusinessDescription.setText(businessDescription);
//            businessHours = pickStartTime.toString() +"to "+pickEndTime.toString();
            //pickStartTime.setText();
            //*****************************************************************************************NEED TO IMPLEMENT BUSINESS HOURS



        }else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteAccountClicked(View view) {
    }
}