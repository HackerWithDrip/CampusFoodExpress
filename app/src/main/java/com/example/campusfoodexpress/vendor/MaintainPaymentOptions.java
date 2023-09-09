package com.example.campusfoodexpress.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

public class MaintainPaymentOptions extends AppCompatActivity {
    Switch btnToggleCard,btnToggleCash;
    ConstraintLayout constCardContainer,constCashContainer;
    TextView txtCard,txtCash;

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


        btnToggleCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Change the background color when the switch is checked
                    constCardContainer.setBackgroundColor(Color.parseColor("#2DB83D"));
                    txtCard.setTextColor(Color.WHITE);
                } else {
                    // Change the background color when the switch is unchecked
                    constCardContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCard.setTextColor(Color.BLACK);
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
                } else {
                    // Change the background color when the switch is unchecked
                    constCashContainer.setBackgroundColor(Color.parseColor("#939292"));
                    txtCash.setTextColor(Color.BLACK);
                }
            }
        });

    }

    public void onSaveClicked(View view) {

    }
}