package com.example.campusfoodexpress.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.campusfoodexpress.R;
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

    }

    public void onSaveCustomerUpdatesClicked(View view) {
        dbHelper = new DatabaseHelper(MaintainCustomerActivity.this);
        dbHelper.updateCustomerDetails(loggedInCustomer,
                edtCustFname.getText().toString().trim(),
                edtCustLname.getText().toString().trim(),
                edtCustContactNumber.getText().toString().trim());
        setResult(RESULT_OK);
        Intent intent = new Intent(MaintainCustomerActivity.this,CustomerDashboardActivity.class);
        intent.putExtra("loggedInCustomer",loggedInCustomer);
        startActivity(intent);
//        finish();
    }

}