package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar.equals("Registering Vendor")){
            actionBar.setTitle("Dashboard");
            Toast.makeText(this,"Welcome!",Toast.LENGTH_LONG).show();
        }else{
            actionBar.setTitle("Dashboard");
            Toast.makeText(this,"Logged in successfully!",Toast.LENGTH_LONG).show();
        }


        // I will start here
    }
}