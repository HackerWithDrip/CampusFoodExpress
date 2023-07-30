package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterVendorActivity extends AppCompatActivity {
    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,
            edtClosestBuilding,edtBusinessDescription,edtUsername,edtPassword,edtConfirmPassword;
    TextView txtErrorOutputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vendor);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Registering Vendor...");
        }
    }
}