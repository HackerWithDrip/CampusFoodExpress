package com.example.campusfoodexpress.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.campusfoodexpress.R;

public class CustomerDashboardActivity extends AppCompatActivity {
    String loggedInCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Dashboard");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_green)));

        Intent intent = getIntent();
        if(intent!=null){
            loggedInCustomer = intent.getStringExtra("loggedInCustomer");
        }
    }

    public void onMyAccountClicked(View view) {
        Intent intent = new Intent(CustomerDashboardActivity.this,MaintainCustomerActivity.class);
        intent.putExtra("loggedInCustomer",loggedInCustomer);
        startActivity(intent);

    }
}