package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusfoodexpress.customer.CustomerDashboardActivity;
import com.example.campusfoodexpress.vendor.MainActivity;

import database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsernameInput,edtPasswordInput;
    TextView txtErrorOutputMessage;
    Button btnLogIn,btnCancel;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Orange)));


        // get the controls references
        edtUsernameInput = findViewById(R.id.edtUsernameInput);
        edtPasswordInput = findViewById(R.id.edtPasswordCustomer);
        txtErrorOutputMessage = findViewById(R.id.txtErrorOutputMessageCustomer);
        btnLogIn = findViewById(R.id.btnLogIn);
        DB = new DatabaseHelper(this);

    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void onLogInClicked(View view) {
        String username = edtUsernameInput.getText().toString();
        String password = edtPasswordInput.getText().toString();
        if(username.equals("") || password.equals("")){
           txtErrorOutputMessage.setText("Please enter all fields!");
            Toast.makeText(this,"Log in failed!",Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkVendorPassword = DB.checkVendorUsernamePassword(username,password);
            Boolean checkCustomerPassword = DB.checkCustomerUsernamePassword(username,password);
            if(checkVendorPassword){
                //Create animation for logging in
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("loggedInVendor",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
            else if(checkCustomerPassword){
                Intent intent = new Intent(this, CustomerDashboardActivity.class);
                intent.putExtra("loggedInCustomer",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
            else {
                txtErrorOutputMessage.setText("Invalid username or password!");
                Toast.makeText(this,"Log in failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}