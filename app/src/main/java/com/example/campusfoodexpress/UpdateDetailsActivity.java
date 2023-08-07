package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import database.DatabaseHelper;

public class UpdateDetailsActivity extends AppCompatActivity {

    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,edtClosestBuilding,edtBusinessDescription;
    Button btnDeleteAccount,btnSave,btnCancel;
    String businessID,businessName,businessContactNumber,businessLocation,businessHours,businessBio,openingTime,closingTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtContactNumber = findViewById(R.id.edtContactNumber);
        pickStartTime = findViewById(R.id.pickStartTime);
        pickEndTime = findViewById(R.id.pickEndTime);
        edtClosestBuilding = findViewById(R.id.edtClosestBuilding);
        edtBusinessDescription = findViewById(R.id.edtBusinessDescription);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        getAndSetIntentData();

        btnSave.setOnClickListener(view->{
            DatabaseHelper dbHelper = new DatabaseHelper(UpdateDetailsActivity.this);
            dbHelper.updateData(businessID, edtBusinessName.getText().toString().trim(),
                    edtContactNumber.getText().toString().trim(),
                    edtBusinessDescription.getText().toString().trim(),edtClosestBuilding.getText().toString().trim(),businessHours);
            setResult(RESULT_OK); // Set the result to indicate success
            finish();
        });


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
    }

    public void onCancelClicked(View view) {  //Implement this
    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("businessID") && getIntent().hasExtra("businessName") &&
                getIntent().hasExtra("businessContactNumber") && getIntent().hasExtra("businessLocation") &&
                getIntent().hasExtra("businessBio")) {

            //Getting data from intent
            businessID = getIntent().getStringExtra("businessID");
            businessName = getIntent().getStringExtra("businessName");
            businessContactNumber = getIntent().getStringExtra("businessContactNumber");
            businessLocation = getIntent().getStringExtra("businessLocation");
            businessHours = getIntent().getStringExtra("businessHours");

            //Setting intent data
            edtBusinessName.setText(businessName);
            edtContactNumber.setText(businessContactNumber);
            edtClosestBuilding.setText(businessLocation);
            edtBusinessDescription.setText(businessBio);
            businessHours = pickStartTime.toString() +"to "+pickEndTime.toString();
            //pickStartTime.setText();
            //*****************************************************************************************NEED TO IMPLEMENT BUSINESS HOURS



        }else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }
}