package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.dialogs.LoadingDialog;

import java.util.List;

import database.DatabaseHelper;

public class MenuActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    LoadingDialog loadingDialog;
    String username;
    TextView txtBurger,txtGrilledChicken,txtFishAndChips,txtKota,txtPie,txtWings,txtOG;
    Switch swtBurger,swtGrilledChicken,swtFishAndChips,swtKota,swtPie,swtWings,swtOG;
    List<FoodItem> foodItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Maintain Menu");

        Intent intent = getIntent();

        txtBurger = findViewById(R.id.txtFoodItemBurger);
        txtGrilledChicken = findViewById(R.id.txtFoodItemGrilledChicken);
        txtFishAndChips = findViewById(R.id.txtFoodItemFishAndChips);
        txtKota = findViewById(R.id.txtFoodItemKota);
        txtPie = findViewById(R.id.txtFoodItemPie);
        txtWings = findViewById(R.id.txtFoodItemWings);
        txtOG = findViewById(R.id.txtFoodItemOG);

        swtBurger = findViewById(R.id.btnSwitchBurger);
        swtGrilledChicken = findViewById(R.id.btnSwitchGrilledChicken);
        swtFishAndChips = findViewById(R.id.btnSwitchFishAndChips);
        swtKota = findViewById(R.id.btnSwitchKota);
        swtPie = findViewById(R.id.btnSwitchPie);
        swtWings = findViewById(R.id.btnSwitchWings);
        swtOG = findViewById(R.id.btnSwitchOG);



        if(intent.hasExtra("username"))
            username = intent.getStringExtra("username");

        if(username != null){
            databaseHelper = new DatabaseHelper(MenuActivity.this);
            foodItemList =  databaseHelper.getVendorMenu(username);
            for(int i = 0;i<foodItemList.size();i++){
                int id = foodItemList.get(i).getId();
                Boolean isAvailable = foodItemList.get(i).isFoodItemAvailable();
                switch (id){
                    case 1:
                        swtBurger.setChecked(isAvailable);
                        if(isAvailable)
                            txtBurger.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtBurger.setTextColor(Color.GRAY);
                        break;
                    case 2:
                        swtGrilledChicken.setChecked(isAvailable);
                        if(isAvailable)
                            txtGrilledChicken.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtGrilledChicken.setTextColor(Color.GRAY);
                        break;
                    case 3:
                        swtFishAndChips.setChecked(isAvailable);
                        if(isAvailable)
                            txtFishAndChips.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtFishAndChips.setTextColor(Color.GRAY);
                        break;
                    case 4:
                        swtKota.setChecked(isAvailable);
                        if(isAvailable)
                            txtKota.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtKota.setTextColor(Color.GRAY);
                        break;
                    case 5:
                        swtPie.setChecked(isAvailable);
                        if(isAvailable)
                            txtPie.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtPie.setTextColor(Color.GRAY);
                        break;
                    case 6:
                        swtWings.setChecked(isAvailable);
                        if(isAvailable)
                            txtWings.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtWings.setTextColor(Color.GRAY);
                        break;
                    case 7:
                        swtOG.setChecked(isAvailable);
                        if(isAvailable)
                            txtOG.setTextColor(Color.parseColor("#2db83d"));
                        else
                            txtOG.setTextColor(Color.GRAY);
                        break;
                }
            }

        }

        swtBurger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Change the background color when the switch is checked
                    txtBurger.setTextColor(Color.parseColor("#2db83d"));
                } else {
                    txtBurger.setTextColor(Color.GRAY);
                }
            }
        });

        swtGrilledChicken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Change the background color when the switch is checked
                    txtGrilledChicken.setTextColor(Color.parseColor("#2db83d"));
                } else {
                    txtGrilledChicken.setTextColor(Color.GRAY);
                }
            }
        });

    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Cancelled", username);
        startActivity(intent);
        finish();
    }

    public void onSaveClicked(View view) {

        for(int i = 0;i<foodItemList.size();i++){
            int id = foodItemList.get(i).getId();
            String isAvailable = "false";
            switch (id){
                case 1:
                    if(swtBurger.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Burger",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Burger",isAvailable);
                    }
                    break;
                case 2:
                    if(swtGrilledChicken.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Grilled Chicken",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Grilled Chicken",isAvailable);
                    }
                    break;
                case 3:
                    if(swtFishAndChips.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Fish and Chips",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Fish and Chips",isAvailable);
                    }
                    break;
                case 4:
                    if(swtKota.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Kota",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Kota",isAvailable);
                    }
                    break;
                case 5:
                    if(swtPie.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Pie",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Pie",isAvailable);
                    }
                    break;
                case 6:
                    if(swtWings.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Wings and Chips",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Wings and Chips",isAvailable);
                    }
                    break;
                case 7:
                    if(swtOG.isChecked()) {
                        isAvailable = "true";
                        databaseHelper.updateMenu(username,String.valueOf(id),"Stuff for the OG",isAvailable);
                    }
                    else {
                        databaseHelper.updateMenu(username,String.valueOf(id),"Stuff for the OG",isAvailable);
                    }
                    break;
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
                intent.putExtra("Saving", username);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}