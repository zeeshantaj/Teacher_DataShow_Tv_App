package com.example.myapplication.Animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myapplication.R;


public class ShakeAnimation {
    public static void setAnimation(Context context, View view){
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_animation);
        view.startAnimation(shake);
    }
}
