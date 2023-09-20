package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.campusfoodexpress.dialogs.LoadingDialog;
import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;

import java.time.LocalDateTime;

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
        if(intent.hasExtra("Title") && intent.getStringExtra("Title").equals("Log in")){
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
        }else if(intent.hasExtra("Saving")){
            loggedInVendor = intent.getStringExtra("Saving");
            loadingDialog = new LoadingDialog(MainActivity.this,"Saving...");
            loadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.showSuccessMessage("Saved!");
                }
            },2000);
        }
        else if(intent.hasExtra("Cancelled")){
            Log.i("HERE","CANCELLED CLICKED");
            loggedInVendor = intent.getStringExtra("Cancelled");
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


    public void onMaintainMenuClicked(View view) {
        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
        intent.putExtra("username",loggedInVendor );
        startActivity(intent);
    }

    public void onViewCurrentOrdersLicked(View view) {
        Intent intent = new Intent(MainActivity.this,CurrentOrdersActivity.class);
        intent.putExtra("username",loggedInVendor );
        startActivity(intent);
    }
}