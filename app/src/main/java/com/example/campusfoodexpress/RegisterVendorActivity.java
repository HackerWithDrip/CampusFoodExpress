package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import database.DatabaseHelper;

public class RegisterVendorActivity extends AppCompatActivity {
    EditText edtBusinessName,edtContactNumber,pickStartTime,pickEndTime,
            edtClosestBuilding,edtBusinessDescription,edtUsername,edtPassword,edtConfirmPassword;
    TextView txtErrorOutputMessage;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vendor);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Registering Vendor");
        }

        // get the controls references
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtContactNumber = findViewById(R.id.edtContactNumber);
        pickStartTime = findViewById(R.id.pickStartTime);
        pickEndTime = findViewById(R.id.pickEndTime);
        edtClosestBuilding = findViewById(R.id.edtClosestBuilding);
        edtBusinessDescription = findViewById(R.id.edtBusinessDescription);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPasswordInput);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        txtErrorOutputMessage = findViewById(R.id.txtErrorOutputMessage);
        DB = new DatabaseHelper(this);
    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(RegisterVendorActivity.this,SignupActivity.class);
        startActivity(intent);
    }

    public void onSubmitClicked(View view) {
        String businessName = edtBusinessName.getText().toString();
        String contactNumber = edtContactNumber.getText().toString();
        String businessHours = "Operational Hours: " + pickStartTime.getText().toString() + " To " + pickEndTime.getText().toString();
        String businessLocation = edtClosestBuilding.getText().toString();
        String businessBio = edtBusinessDescription.getText().toString();;
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        // let's validate the data
        if(password.trim().equals(confirmPassword.trim()) && !password.equals("") && !username.equals("") &&
                !businessHours.equals("") && !businessName.equals("") && !contactNumber.equals("") && !businessLocation.equals("") && !businessBio.equals("")){
            txtErrorOutputMessage.clearComposingText();
            // register the user
            Boolean checkVendor = DB.checkUsername(username);
            if(!checkVendor){
                Boolean isInserted = DB.insertVendorData(username,password,businessName,contactNumber,businessHours,businessLocation,businessBio);
                if(isInserted){
                    Toast.makeText(this,"Registered successfully!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    return;
                }
            }

        }else{
            if(password.equals("") || username ==null ||
                    businessHours==null || businessName==null || contactNumber==null || businessLocation ==null || businessBio ==null){
                txtErrorOutputMessage.setText("All fields are required");
                return;
            }

            if(!password.equals(confirmPassword))
                txtErrorOutputMessage.setText("Passwords do not match!");


        }

    }

}