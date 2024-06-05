package com.duogwas.mynote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.duogwas.mynote.R;

public class SplashScreen extends AppCompatActivity {
    TextView tvAppName;
    LottieAnimationView animationView;
    Animation top_anim, bottom_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        initView();
        connectAnimation();
        nextScreen();
    }

    private void initView() {
        tvAppName = findViewById(R.id.tvAppName);
        animationView = findViewById(R.id.animationView);
        top_anim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
    }

    private void connectAnimation() {
        animationView.setAnimation(top_anim);
        tvAppName.setAnimation(bottom_anim);
    }

    private void nextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3200);
    }
}