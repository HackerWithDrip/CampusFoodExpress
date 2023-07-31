package com.example.campusfoodexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsernameInput,edtPasswordInput;
    TextView txtErrorOutputMessage;
    Button btnLogIn,btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set actionbar title Dynamically
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Logging in");
        }

        // get the controls references
        edtUsernameInput = findViewById(R.id.edtUsernameInput);
        edtPasswordInput = findViewById(R.id.edtPasswordInput);
        txtErrorOutputMessage = findViewById(R.id.txtErrorOutputMessage);
        btnLogIn = findViewById(R.id.btnLogIn);


    }

    public void onCancelClicked(View view) {
        Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}