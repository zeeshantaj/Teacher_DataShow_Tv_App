package com.example.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.View;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MethodUtils {
    public static int getRadonColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);
    }

    public static void createSharedPreference(Context context,String sharedName,String editorName,int time){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(editorName,time);
        editor.apply();
    }
    public static String getSystemUid(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
