package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Switch;

import com.example.campusfoodexpress.LoginActivity;
import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.dialogs.LoadingDialog;

import database.DatabaseHelper;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodItemAdapter foodItemAdapter;
    DatabaseHelper databaseHelper;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Maintain Menu");

        recyclerView = findViewById(R.id.foodItemRecyclerView);
        databaseHelper = new DatabaseHelper(MenuActivity.this);
        foodItemAdapter = new FoodItemAdapter(this,databaseHelper.getFoodItems(),MenuActivity.this);
        recyclerView.setAdapter(foodItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSaveClicked(View view) {

        for (int i = 0; i < foodItemAdapter.getItemCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            if (itemView != null) {
                Switch btnSwitch = itemView.findViewById(R.id.btnSwitch);
                if (btnSwitch != null) {
                    FoodItem foodItem = databaseHelper.getFoodItems().get(i);
                    foodItem.setSwitchState(btnSwitch.isChecked());
                }
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        loadingDialog = new LoadingDialog(this,"Saving changes...");
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null) {
                    loadingDialog.dismissDialog();
                }
                intent.putExtra("Saving", true);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}