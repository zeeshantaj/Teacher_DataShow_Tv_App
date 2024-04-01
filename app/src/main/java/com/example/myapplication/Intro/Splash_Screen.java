package com.example.myapplication.Intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Activity.MainActivity2;
import com.example.myapplication.R;

public class Splash_Screen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("introShared",MODE_PRIVATE);
                boolean isTrue = sharedPreferences.getBoolean("isIntro",false);
                Intent intent;
                if (isTrue){
                     intent = new Intent(Splash_Screen.this, MainActivity2.class);

                }
                else {
                    intent = new Intent(Splash_Screen.this, Intro_Activity.class);
                }
                startActivity(intent);
                finish();
            }
        },SPLASH_DELAY);


    }
}