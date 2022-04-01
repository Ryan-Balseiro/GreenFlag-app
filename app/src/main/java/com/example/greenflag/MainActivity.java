package com.example.greenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    //initialize views
    private void initViews(){
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(this::openCreateAccount);

    }

    private void openCreateAccount(View view) {
        Intent intent  = new Intent(this, CreateAccount.class);
        this.startActivity(intent);

    }
}