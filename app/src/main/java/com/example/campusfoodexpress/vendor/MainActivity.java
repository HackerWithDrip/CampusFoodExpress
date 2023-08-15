package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.campusfoodexpress.dialogs.LoadingDialog;
import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;

import database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String loggedInVendor;
    LoadingDialog loadingDialog;
    Button btnPaymentOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vendor);
        Intent intent = getIntent();
        btnPaymentOptions = findViewById(R.id.btnMaintainPaymentOptions);

        ActionBar actionBar = getSupportActionBar();
        if(intent.hasExtra("Title") && intent.getStringExtra("Title").equals("Register Vendor")){
            actionBar.setTitle("Dashboard");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
            loggedInVendor = intent.getStringExtra("loggedInVendor");
            loadingDialog = new LoadingDialog(MainActivity.this,"Registering in...");
            loadingDialog.startLoadingDialog();
            loadingDialog.showSuccessMessage("Registered successfully!");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismissDialog();
                }
            },2500);
        }else if(intent.hasExtra("Title") && intent.getStringExtra("Title").equals("Log in")){
            actionBar.setTitle("Dashboard");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
            if(intent!=null){
                loggedInVendor = intent.getStringExtra("loggedInVendor");
                loadingDialog = new LoadingDialog(MainActivity.this,"Logging in...");
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.showSuccessMessage("Logged in successfully!");
                    }
                },2500);

            }
        }

    }

    public void onLogOutClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        loadingDialog = new LoadingDialog(MainActivity.this,"Logging Out...");
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent.putExtra("Title","VendorDashboard");
                startActivity(intent);
                loadingDialog.dismissDialog();
                finish();
            }
        },2000);
    }

    public void onUpdateDetailsClicked(View view) {
        Intent intent = new Intent(MainActivity.this,UpdateDetailsActivity.class);
        intent.putExtra("username",loggedInVendor );
        startActivity(intent);
    }

    public void onPaymentOptionsClicked(View view) {
        Intent intent = new Intent(MainActivity.this,MaintainPaymentOptions.class);
        intent.putExtra("username",loggedInVendor );
        startActivity(intent);
    }
}