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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.WelcomeActivity;
import com.example.campusfoodexpress.dialogs.LoadingDialog;
import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.SignupActivity;

import database.DatabaseHelper;

public class RegisterVendorActivity extends AppCompatActivity {
    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,
            edtClosestBuilding,edtBusinessDescription,edtUsername,edtPassword,edtConfirmPassword;
    TextView txtErrorOutputMessage;
    DatabaseHelper DB;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vendor);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Register Vendor");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Orange)));
        }

        // get the controls references
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtContactNumber = findViewById(R.id.edtContactNumberCustomer);
        pickStartTime = findViewById(R.id.OpeningTime);
        pickEndTime = findViewById(R.id.closingTime);
        edtClosestBuilding = findViewById(R.id.edtClosestBuilding);
        edtBusinessDescription = findViewById(R.id.edtBusinessDescriptionUpdate);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        txtErrorOutputMessage = findViewById(R.id.txtErrorOutputMessageCustomer);
        DB = new DatabaseHelper(this);
    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(RegisterVendorActivity.this, SignupActivity.class);
        startActivity(intent);
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

    public void onSubmitClicked(View view) {
        String businessName = edtBusinessName.getText().toString().trim();
        String contactNumber = edtContactNumber.getText().toString().trim();
        String businessHours = pickStartTime.getText().toString() + " To " + pickEndTime.getText().toString();
        String businessLocation = edtClosestBuilding.getText().toString();
        String businessBio = edtBusinessDescription.getText().toString();
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        String startTime = pickStartTime.getText().toString();
        String endTime = pickEndTime.getText().toString();
        // Check for empty fields
        if (password.trim().equals("") || username.trim().equals("") ||
                businessHours.trim().equals("") || businessName.trim().equals("") ||
                contactNumber.trim().equals("") || businessLocation.trim().equals("") ||
                businessBio.trim().equals("")) {
            txtErrorOutputMessage.setText("All fields are required");
            return;
        }

        // Check if startTime is not greater or equal to endTime
        if (isStartTimeAfterEndTime(startTime, endTime)) {
            txtErrorOutputMessage.setText("Start time cannot be after or equal to closing time");
            pickStartTime.setError("Start time cannot be after or equal to end time");
            pickEndTime.setError("End time cannot be before or equal to start time");
            return;
        } else {
            txtErrorOutputMessage.setError(null); // Clear any previous error
            pickStartTime.setError(null); // Clear any previous error
            pickEndTime.setError(null); // Clear any previous error
        }

        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            txtErrorOutputMessage.setText("Passwords do not match!");
            return;
        }

        // Validate contactNumber (assuming 10-digit phone number)
        if (!isValidContactNumber(contactNumber)) {
            edtContactNumber.setError("Invalid contact number format (10 digits required)");
            txtErrorOutputMessage.setText("Invalid contact number format (10 digits required)");
            return;
        } else {
            edtContactNumber.setError(null); // Clear any previous error
        }

        // Validate username and password (customize the criteria as needed)
        if (!isValidUsername(username)) {
            edtUsername.setError("Invalid username format (minimum length: 5 characters)");
            txtErrorOutputMessage.setText("Invalid username format (minimum length: 5 characters)");
            return;
        } else {
            edtUsername.setError(null); // Clear any previous error
        }

        if (!isValidPassword(password)) {
            edtPassword.setError("Invalid password format (minimum length: 8 characters)");
            txtErrorOutputMessage.setText("Invalid password format (minimum length: 8 characters)");
            return;
        } else {
            edtPassword.setError(null); // Clear any previous error
        }

        txtErrorOutputMessage.setText("");

        // Register the user
        Boolean checkVendor = DB.checkUsername(username);
        if (!checkVendor) {
            Boolean isInserted = DB.insertVendorData(username, password, businessName, contactNumber, businessHours, businessLocation, businessBio);
            if (isInserted) {
                DB.insertVendorPaymentOptions(username,"false","false");
                Intent intent = new Intent(this, LoginActivity.class);
                loadingDialog = new LoadingDialog(RegisterVendorActivity.this,"Registering...");
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        intent.putExtra("Title","Register Vendor");
                        startActivity(intent);
                    }
                },3500);
            } else {
                Toast.makeText(this, "Registered Failed!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "User: " + username + " already exists", Toast.LENGTH_LONG).show();
        }
    }
    // Helper method to validate contact number (assuming 10-digit phone number)
    private boolean isValidContactNumber(String contactNumber) {
        return contactNumber.matches("\\d{10}");
    }

    // Helper method to validate time format (assuming HH:mm format)


    // Helper method to validate username (customize the criteria as needed)
    private boolean isValidUsername(String username) {
        return username.length() >= 5;
    }

    // Helper method to validate password (customize the criteria as needed)
    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    // Helper method to check if startTime is after endTime
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

}