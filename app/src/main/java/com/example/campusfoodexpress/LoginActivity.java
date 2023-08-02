package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        if(actionBar!=null){
            actionBar.setTitle("Login");
        }

        // get the controls references
        edtUsernameInput = findViewById(R.id.edtUsernameInput);
        edtPasswordInput = findViewById(R.id.edtPasswordInput);
        txtErrorOutputMessage = findViewById(R.id.txtErrorOutputMessage);
        btnLogIn = findViewById(R.id.btnLogIn);
        DB = new DatabaseHelper(this);

    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
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
            Boolean checkVendorPassword = DB.checkUsernamePassword(username,password);
            if(checkVendorPassword){
                Toast.makeText(this,"Logged in...",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else {
                txtErrorOutputMessage.setText("Invalid username or password!");
                Toast.makeText(this,"Log in failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}