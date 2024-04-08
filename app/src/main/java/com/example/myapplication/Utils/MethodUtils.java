package com.example.myapplication.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Network.NetworkUtils;
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
    public static void checkInternet(Context context, View rootView){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                // Device's internet is turned on

                NetworkUtils.hasInternetAccess(hasInternetAccess -> {
                    if (hasInternetAccess){
                        Toast.makeText(context, "Internet has service", Toast.LENGTH_SHORT).show();

                    }else {
                        Snackbar.make(rootView.findViewById(android.R.id.content), "You Lost Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Check Again", view -> {
                                    // Retry checking internet connectivity
                                    checkInternet(context,rootView);
                                }).show();

                    }
                });

            } else {
                // Device's internet is turned off
                // Show dialog to prompt user to turn on internet
                MethodUtils.showAlertDialogue(context);

            }
        } else {
            // Error checking internet connection
            Toast.makeText(context, "Error checking internet connection", Toast.LENGTH_SHORT).show();
        }

    }
    public static void showAlertDialogue(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Internet is turned off. Do you want to turn it on?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
