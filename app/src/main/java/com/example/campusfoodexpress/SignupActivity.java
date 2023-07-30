package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Signing up...");
        }
    }

    public void onRegisterVendorClicked(View view) {
        Intent intent = new Intent(SignupActivity.this,RegisterVendorActivity.class);
        startActivity(intent);
    }

    public void onRegisterCustomerClicked(View view) {
        //Still to implement this
    }
}