package com.example.iyeppeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public  class SplashScreen extends AppCompatActivity {

    ImageView img;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.imgSplash);
        TV  = findViewById(R.id.idTV);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.blink);
        img.startAnimation(anim);
        TV.startAnimation(anim2);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), OnBoarding1.class));
            }
        }, 5000L); //5000 L = 5 detik
    }
}