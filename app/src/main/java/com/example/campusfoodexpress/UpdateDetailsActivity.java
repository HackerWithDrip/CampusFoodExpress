package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class UpdateDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
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
}