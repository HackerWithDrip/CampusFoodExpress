package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.campusfoodexpress.EqualSpaceItemDecoration;
import com.example.campusfoodexpress.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;

public class CurrentOrdersActivity extends AppCompatActivity {
    private List<OrderDetails> orders;
    private OrderAdapter adapter;
    String username;
    private final int CONFIRM_ORDER = 111;
    private final int CANCEL_ORDER = 222;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Orders");

        dbHelper = new DatabaseHelper(CurrentOrdersActivity.this);
        Intent intent = getIntent();

        if(intent.hasExtra("username"))
            username = intent.getStringExtra("username");

        if(username !=null){
            orders = dbHelper.getOrders(username);
            adapter = new OrderAdapter(this,orders,CurrentOrdersActivity.this);

            RecyclerView lstContacts = findViewById(R.id.lstOrders);
            RecyclerView.LayoutManager layoutManager;

            layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

            lstContacts.setLayoutManager(layoutManager);
            lstContacts.setAdapter(adapter);

            lstContacts.addItemDecoration(new EqualSpaceItemDecoration(15));
        }
    }


}