package com.uae.tambolaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.uae.tambolaapp.helper_classes.Utils;


public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        removeStatusBar();
        Utils.requestFullScreen(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
