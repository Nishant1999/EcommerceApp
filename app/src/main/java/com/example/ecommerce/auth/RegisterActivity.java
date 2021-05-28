package com.example.ecommerce.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameET,nameET,userEmailET,userPasswordET;
    private Button createAccountBN;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
        setListener();

    }

    private void initialize() {

        userNameET = findViewById(R.id.activity_register_username_et);
        nameET=findViewById(R.id.activity_register_name_et);
        userEmailET = findViewById(R.id.activity_register_email_et);
        userPasswordET = findViewById(R.id.activity_register_password_et);
        createAccountBN = findViewById(R.id.activity_register_btn);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    private void setListener(){

        createAccountBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateField();
            }
        });
    }

    private void validateField() {
        String userName = userNameET.getText().toString();
        String name=nameET.getText().toString();
        String email = userEmailET.getText().toString();
        String password = userPasswordET.getText().toString();

        if(TextUtils.isEmpty(userName)){
            userNameET.setError("Please Enter Your UserName");
        }
        else if(TextUtils.isEmpty(name)){
            nameET.setError("Please Enter Your Name");
        }
        else if(TextUtils.isEmpty(email)){
            userEmailET.setError("Please Enter Email Address");
        }
        else if(TextUtils.isEmpty(password)){
            userPasswordET.setError("Please Enter Password");
        }
        else{
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait, while checking the credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            registerUserDetails(userName,name,email,password);


        }
    }

    private void registerUserDetails(String userName,String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userId = firebaseAuth.getUid();
                    User user = new User(userId,userName,name,email,password);
                    saveUserDetailsFirestore(user);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Error in Creating Account    ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserDetailsFirestore(User user) {
        firebaseFirestore.collection("Users")
                .document(user.getUserId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Register Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

}