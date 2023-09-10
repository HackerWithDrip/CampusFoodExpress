package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

import database.DatabaseHelper;

public class MaintainPaymentOptions extends AppCompatActivity {
    Switch btnToggleCard, btnToggleCash;
    ConstraintLayout constCardContainer, constCashContainer;
    TextView txtCard, txtCash;
    String username, isCardPayment, isCashPayment;
    DatabaseHelper dbHelper;
    PaymentOption paymentOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_payment_options);
        btnToggleCard = findViewById(R.id.btnToggleCard);
        btnToggleCash = findViewById(R.id.btnToggleCash);
        constCardContainer = findViewById(R.id.constCardContainer);
        constCashContainer = findViewById(R.id.constCashContainer);
        txtCard = findViewById(R.id.txtCard);
        txtCash = findViewById(R.id.txtCash);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {

            username = intent.getStringExtra("username");
            dbHelper = new DatabaseHelper(MaintainPaymentOptions.this);
            paymentOption = dbHelper.getPaymentOptions(username);
            if (paymentOption != null) {
                isCardPayment = paymentOption.getIsCardPayment();
                isCashPayment = paymentOption.getIsCashPayment();
                if (isCardPayment.equalsIgnoreCase("true")) {
                    constCardContainer.setBackgroundColor(Color.parseColor("#2DB83D"));
                    txtCard.setTextColor(Color.WHITE);
                    btnToggleCard.setChecked(true);
                } else {
                    constCardContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCard.setTextColor(Color.BLACK);
                    btnToggleCard.setChecked(false);
                }

                if (isCashPayment.equalsIgnoreCase("true")) {
                    constCashContainer.setBackgroundColor(Color.parseColor("#2DB83D"));
                    txtCash.setTextColor(Color.WHITE);
                    btnToggleCash.setChecked(true);
                } else {
                    constCashContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCash.setTextColor(Color.BLACK);
                    btnToggleCash.setChecked(false);
                }
            }
        }
        btnToggleCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Change the background color when the switch is checked
                    constCardContainer.setBackgroundColor(Color.parseColor("#2DB83D"));
                    txtCard.setTextColor(Color.WHITE);
                    isCardPayment = "true";
                } else {
                    // Change the background color when the switch is unchecked
                    constCardContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCard.setTextColor(Color.BLACK);
                    isCardPayment = "false";
                }
            }
        });

        btnToggleCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Change the background color when the switch is checked
                    constCashContainer.setBackgroundColor(Color.parseColor("#2DB83D"));
                    txtCash.setTextColor(Color.WHITE);
                    isCashPayment = "true";
                } else {
                    // Change the background color when the switch is unchecked
                    constCashContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCash.setTextColor(Color.BLACK);
                    isCashPayment = "false";
                }
            }
        });

    }

    public void onSaveClicked(View view) {
        dbHelper.updatePaymentOptions(username, isCardPayment, isCashPayment);
        Intent intent = new Intent(MaintainPaymentOptions.this, MainActivity.class);
        intent.putExtra("Saving", username);
        startActivity(intent);
        finish();
    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(MaintainPaymentOptions.this, MainActivity.class);
        intent.putExtra("Cancelled", username);
        startActivity(intent);
        finish();
    }
}