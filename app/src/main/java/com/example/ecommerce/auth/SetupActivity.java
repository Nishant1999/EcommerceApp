package com.example.ecommerce.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.ecommerce.R;

public class SetupActivity extends AppCompatActivity {

    private Button registerBN,loginBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initialize();
    }

    private void initialize() {

        registerBN=findViewById(R.id.activity_setup_register_btn);
        loginBN=findViewById(R.id.activity_setup_login_btn);
    }
}