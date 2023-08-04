package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_green)));

            Toast.makeText(this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
        }


    }

    public void onLogOutClicked(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"You are now offline!",Toast.LENGTH_LONG).show();
    }

    public void onUpdateDetailsClicked(View view) {
        Intent intent = new Intent(this,UpdateDetailsActivity.class);
        startActivity(intent);

    }
}