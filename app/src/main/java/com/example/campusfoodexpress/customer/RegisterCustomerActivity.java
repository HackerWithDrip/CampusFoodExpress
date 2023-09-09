package com.example.campusfoodexpress.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.SignupActivity;

import database.DatabaseHelper;

public class RegisterCustomerActivity extends AppCompatActivity {
    TextView txtErrorOutputMessageCustomer;
    DatabaseHelper DB;
    EditText edtFirstName,edtLastName,edtCustomerContactNumber,edtCustomerUsername,edtCustomerPassword,edtConfirmCustomerPassword;
    String firstName,lastName,customerContactNumber,customerUsername,customerPassword,confirmCustomerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Register Customer");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Orange)));
        }

        txtErrorOutputMessageCustomer = findViewById(R.id.txtErrorOutputMessageCustomer);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtCustomerContactNumber = findViewById(R.id.edtContactNumberCustomer);
        edtCustomerUsername = findViewById(R.id.edtUsernameCustomer);
        edtCustomerPassword = findViewById(R.id.edtPasswordCustomer);
        edtConfirmCustomerPassword = findViewById(R.id.edtConfirmPasswordCustomer);
        DB = new DatabaseHelper(this);
    }

    public void onSubmitClicked(View view) {
        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        customerContactNumber = edtCustomerContactNumber.getText().toString().trim();
        customerUsername = edtCustomerUsername.getText().toString().trim();
        customerPassword = edtCustomerPassword.getText().toString();
        confirmCustomerPassword = edtConfirmCustomerPassword.getText().toString();

        if (customerPassword.equals("") || customerUsername.trim().equals("") ||
                lastName.trim().equals("") || firstName.trim().equals("") ||
                customerContactNumber.trim().equals("") || confirmCustomerPassword.equals("")) {
            txtErrorOutputMessageCustomer.setText("All fields are required");
            return;
        }

        // Validate password confirmation
        if (!customerPassword.equals(confirmCustomerPassword)) {
            txtErrorOutputMessageCustomer.setText("Passwords do not match!");
            return;
        }

        // Validate contactNumber (assuming 10-digit phone number)
        if (!isValidContactNumber(customerContactNumber)) {
            edtCustomerContactNumber.setError("Invalid contact number format (10 digits required)");
            txtErrorOutputMessageCustomer.setText("Invalid contact number format (10 digits required)");
            return;
        } else {
            edtCustomerContactNumber.setError(null); // Clear any previous error
        }

        // Validate username and password (customize the criteria as needed)
        if (!isValidUsername(customerUsername)) {
            edtCustomerUsername.setError("Invalid username format (minimum length: 5 characters)");
            txtErrorOutputMessageCustomer.setText("Invalid username format (minimum length: 5 characters)");
            return;
        } else {
            edtCustomerUsername.setError(null); // Clear any previous error
        }

        if (!isValidPassword(customerPassword)) {
            edtCustomerPassword.setError("Invalid password format (minimum length: 8 characters)");
            txtErrorOutputMessageCustomer.setText("Invalid password format (minimum length: 8 characters)");
            return;
        } else {
            edtCustomerPassword.setError(null); // Clear any previous error
        }

        txtErrorOutputMessageCustomer.setText("");

        // Register the user
        Boolean checkCustomer = DB.checkUsername(customerUsername);
        if (!checkCustomer) {
            Boolean isInserted = DB.insertCustomerData(customerUsername, customerPassword, firstName, lastName, customerContactNumber);
            if (isInserted) {
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CustomerDashboardActivity.class);
                intent.putExtra("loggedInCustomer",customerUsername);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Registered Failed!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User: " + customerUsername + " already exists", Toast.LENGTH_SHORT).show();
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


    public void onCancelClicked(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}