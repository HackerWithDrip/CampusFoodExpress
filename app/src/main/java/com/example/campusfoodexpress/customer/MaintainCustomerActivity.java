package com.example.campusfoodexpress.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.campusfoodexpress.R;

public class MaintainCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_customer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Account");
    }

    public void onDeleteMyAccountClicked(View view) {
    }
}