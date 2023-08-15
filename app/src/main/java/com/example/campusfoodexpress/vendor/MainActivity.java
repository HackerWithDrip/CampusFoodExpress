package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;

import database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String loggedInVendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vendor);
        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar.equals("Registering Vendor")){
            actionBar.setTitle("Dashboard");
            Toast.makeText(this,"Welcome!",Toast.LENGTH_LONG).show();
        }else{
            actionBar.setTitle("Dashboard");
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_green)));
            if(intent!=null)
                loggedInVendor = intent.getStringExtra("loggedInVendor");

            Toast.makeText(this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
        }


    }

    public void onLogOutClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"You are now offline!",Toast.LENGTH_LONG).show();
    }

    public void onUpdateDetailsClicked(View view) {
        Intent intent = new Intent(MainActivity.this,UpdateDetailsActivity.class);
        intent.putExtra("username",loggedInVendor );
        startActivity(intent);

    }
}