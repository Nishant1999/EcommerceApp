package com.example.ecommerce.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;

public class SetupActivity extends AppCompatActivity {

    private ImageView imageIconIV;
    private LinearLayout linearLayout;
    private Button registerBN,loginBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initialize();
        setListener();


    }

    private void initialize() {

        imageIconIV=findViewById(R.id.activity_setup_icon_iv);
        linearLayout=findViewById(R.id.activity_setup_linear_layout);
        registerBN=findViewById(R.id.activity_setup_register_btn);
        loginBN=findViewById(R.id.activity_setup_login_btn);

        linearLayout.animate().alpha(0f).setDuration(1);
        TranslateAnimation animation=new TranslateAnimation(0,0,0,-1500);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        imageIconIV.setAnimation(animation);

    }

    private void setListener() {


        registerBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetupActivity.this,RegisterActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        loginBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetupActivity.this,LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
                imageIconIV.clearAnimation();
                imageIconIV.setVisibility(View.INVISIBLE);
                linearLayout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}