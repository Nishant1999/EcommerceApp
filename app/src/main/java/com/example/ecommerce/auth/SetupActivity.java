package com.example.ecommerce.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;

public class SetupActivity extends AppCompatActivity {

    private Button registerBN,loginBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initialize();
        setListener();
    }

    private void initialize() {

        registerBN=findViewById(R.id.activity_setup_register_btn);
        loginBN=findViewById(R.id.activity_setup_login_btn);
    }

    private void setListener() {

        registerBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetupActivity.this,RegisterActivity.class));
            }
        });
        loginBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetupActivity.this,LoginActivity.class));
            }
        });
    }
}