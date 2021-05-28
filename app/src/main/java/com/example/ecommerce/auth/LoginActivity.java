package com.example.ecommerce.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmailET,userPasswordET;
    private Button loginBN;
    private TextView registerUserTV;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        setListener();
    }

    private void initialize(){

        userEmailET = findViewById(R.id.activity_login_email_address_et);
        userPasswordET = findViewById(R.id.activity_login_password_et);
        loginBN = findViewById(R.id.activity_login_btn);
        registerUserTV=findViewById(R.id.activity_login_register_user_tv);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setListener(){

        registerUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        loginBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateField();
            }
        });
    }

    private void validateField() {
        String email = userEmailET.getText().toString();
        String password = userPasswordET.getText().toString();

        if(TextUtils.isEmpty(email)){
            userEmailET.setError("Please Enter Email Address");
        }
        else if(TextUtils.isEmpty(password)){
            userPasswordET.setError("Please Enter Password");
        }
        else {
            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Please wait, while checking the credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            loginUser(email,password);

        }
    }

    private void loginUser(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Incorrect Login Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}