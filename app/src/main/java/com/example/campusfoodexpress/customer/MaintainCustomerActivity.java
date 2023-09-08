package com.example.campusfoodexpress.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.dialogs.InteractiveDialog;
import com.example.campusfoodexpress.dialogs.LoadingDialog;
import com.example.campusfoodexpress.vendor.UpdateDetailsActivity;
import com.example.campusfoodexpress.vendor.VendorData;

import database.DatabaseHelper;

public class MaintainCustomerActivity extends AppCompatActivity {
    String loggedInCustomer;
    EditText edtCustFname,edtCustLname,edtCustContactNumber;
    DatabaseHelper dbHelper;
    CustomerData customerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_customer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
        Intent intent = getIntent();
        if(intent!=null){
            loggedInCustomer = intent.getStringExtra("loggedInCustomer");
        }
        edtCustFname = findViewById(R.id.edtCustomerFirstName);
        edtCustLname = findViewById(R.id.edtCustomerLastName);
        edtCustContactNumber = findViewById(R.id.edtCustomerContactNumber);

        dbHelper = new DatabaseHelper(MaintainCustomerActivity.this);
        customerData = dbHelper.getCustomerDataByUsername(loggedInCustomer);
        edtCustFname.setText(customerData.getCustomerFirstName());
        edtCustLname.setText(customerData.getCustomerLastName());
        edtCustContactNumber.setText(customerData.getCustomerContactNumber());
    }

    public void onDeleteMyAccountClicked(View view) {
        InteractiveDialog interactiveDialog = new InteractiveDialog(this);
        interactiveDialog.startInteractiveDialog();

        // Set click listener for the "Yes" button in the confirmation dialog
        interactiveDialog.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the confirmation dialog
                interactiveDialog.dismissDialog();
                // Show the loading dialog
                LoadingDialog loadingDialog = new LoadingDialog(MaintainCustomerActivity.this, "Deleting Account...");
                loadingDialog.startLoadingDialog();
                // Delete the account in the background
                if (!loggedInCustomer.equals("")) {
                    DatabaseHelper dbHelper = new DatabaseHelper(MaintainCustomerActivity.this);
                    boolean isDeleted = dbHelper.deleteCustomer(loggedInCustomer);
                    if (isDeleted) {
                        edtCustFname.setText("");
                        edtCustLname.setText("");
                        edtCustContactNumber.setText("");
                        setResult(RESULT_OK);
                        Intent intent = new Intent(MaintainCustomerActivity.this, LoginActivity.class);
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

    public void onSaveCustomerUpdatesClicked(View view) {

        String custFname,custLname,custContactNumber;
        custFname =  edtCustFname.getText().toString().trim();
        custLname =  edtCustLname.getText().toString().trim();
        custContactNumber =  edtCustContactNumber.getText().toString().trim();

        if(custFname.equalsIgnoreCase("")){
            edtCustFname.setError("First name cannot be empty!");
            return;
        }
        if(custLname.equalsIgnoreCase("")){
            edtCustLname.setError("Last name cannot be empty!");
            return;
        }
        if(custContactNumber.equals("")){
            edtCustContactNumber.setError("Contact numbers required");
            return;
        }

        if(!isValidContactNumber(custContactNumber)){
            edtCustContactNumber.setError("Contact number must be 10 digits");
            return;
        }

        dbHelper = new DatabaseHelper(MaintainCustomerActivity.this);
        dbHelper.updateCustomerDetails(loggedInCustomer,
                edtCustFname.getText().toString().trim(),
                edtCustLname.getText().toString().trim(),
                edtCustContactNumber.getText().toString().trim());
        setResult(RESULT_OK);
        Intent intent = new Intent(MaintainCustomerActivity.this,CustomerDashboardActivity.class);
        intent.putExtra("loggedInCustomer",loggedInCustomer);
        startActivity(intent);
        finish();
    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(MaintainCustomerActivity.this,CustomerDashboardActivity.class);
        intent.putExtra("loggedInCustomer",loggedInCustomer);
        startActivity(intent);
        finish();
    }

    // Helper method to validate contact number (assuming 10-digit phone number)
    private boolean isValidContactNumber(String contactNumber) {
        return contactNumber.matches("\\d{10}");
    }
}