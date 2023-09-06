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

        DatabaseHelper dbHelper = new DatabaseHelper(MaintainCustomerActivity.this);
        CustomerData customerData = dbHelper.getCustomerDataByUsername(loggedInCustomer);
        edtCustFname.setText(customerData.getCustomerFirstName());
    }

    public void onDeleteMyAccountClicked(View view) {
    }
}