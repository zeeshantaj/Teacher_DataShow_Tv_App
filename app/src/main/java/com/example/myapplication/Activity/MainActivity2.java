package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.BrowseFrameLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Connectivity_Check.InternetAccessCallBack;
import com.example.myapplication.Connectivity_Check.InternetAccessTask;
import com.example.myapplication.Connectivity_Check.NetworkUtils;
import com.example.myapplication.Fragments.AnnounceScrollFragment;
import com.example.myapplication.Fragments.ClassScrollFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.fragment_key_set;
import com.example.myapplication.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity2 extends FragmentActivity implements View.OnKeyListener {

    private TextView navHome;
    private TextView navKey;
    private TextView navClassScroll;
    private TextView navAnnounceScroll;
    private BrowseFrameLayout navBar;
    private boolean SIDE_MENU = false;
    private final String navName = "Home";
    private final String navName1 = "Set Key";
    private final String navName2 = "Set class scroll time";
    private final String navName3 = "Set Announcement scroll time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navHome = findViewById(R.id.navHome);
        navKey = findViewById(R.id.navKey);
        navClassScroll = findViewById(R.id.navClassScroll);
        navAnnounceScroll = findViewById(R.id.navAnnounceScroll);
        navBar = findViewById(R.id.navBar);


        navHome.setText("");
        navKey.setText("");
        navClassScroll.setText("");
        navAnnounceScroll.setText("");

        navHome.setOnKeyListener(this);
        navKey.setOnKeyListener(this);
        navClassScroll.setOnKeyListener(this);
        navAnnounceScroll.setOnKeyListener(this);

        changeFragment(new HomeFragment());
//        AdView adView = findViewById(R.id.adView);
//        MobileAds.initialize(this, initializationStatus -> {
//            Toast.makeText(this, " successful ", Toast.LENGTH_SHORT).show();
//        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Log.e("MyApp","failedToLoad");
//            }
//
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//
//                Log.e("MyApp","AddLoaded");
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//                Log.e("MyApp","addOpened");
//            }
//        });

        CheckForInternetConnection();
    }
    private void CheckForInternetConnection(){
//        if (NetworkUtils.isNetworkAvailable(this)){
//            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
//            NetworkUtils.hasInternetAccess(new InternetAccessCallBack() {
//                @Override
//                public void onInternetAccessResult(boolean hasInternetAccess) {
//                    if (hasInternetAccess){
//                        Toast.makeText(MainActivity2.this, "You Are Online", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        // no service
//                        Toast.makeText(MainActivity2.this, "no internet service", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }else {
//            // no internet connected
//            Toast.makeText(MainActivity2.this, "internet is turned off  ", Toast.LENGTH_SHORT).show();
//        }

//        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (isConnected) {
//            // do something
//            Toast.makeText(MainActivity2.this, "Connected ", Toast.LENGTH_SHORT).show();
//        } else {
//            // show an error message or do something else
//            Toast.makeText(MainActivity2.this, "not connected", Toast.LENGTH_SHORT).show();
//        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                // Device's internet is turned on
                Toast.makeText(MainActivity2.this, "Device's internet is turned on", Toast.LENGTH_SHORT).show();
                NetworkUtils.hasInternetAccess(new InternetAccessCallBack() {
                    @Override
                    public void onInternetAccessResult(boolean hasInternetAccess) {
                        if (hasInternetAccess){
                            Toast.makeText(MainActivity2.this, "service available", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity2.this, "service not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Device's internet is turned off
                Toast.makeText(MainActivity2.this, "Device's internet is turned off", Toast.LENGTH_SHORT).show();

                // Show dialog to prompt user to turn on internet
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setMessage("Internet is turned off. Do you want to turn it on?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            // Error checking internet connection
            Toast.makeText(MainActivity2.this, "Error checking internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && SIDE_MENU) {
            SIDE_MENU = false;
            closeMenu();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (v.getId() == R.id.navHome) {
                    changeFragment(new HomeFragment());
                    closeMenu();
                } else if (v.getId() == R.id.navKey) {
                    changeFragment(new fragment_key_set());
                    closeMenu();
                } else if (v.getId() == R.id.navClassScroll) {
                    changeFragment(new ClassScrollFragment());
                    closeMenu();
                } else if (v.getId() == R.id.navAnnounceScroll) {
                    changeFragment(new AnnounceScrollFragment());
                    closeMenu();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (!SIDE_MENU) {
                    openMenu();
                    SIDE_MENU = true;
                }
                break;
        }
        return false;
    }
    private void openMenu() {
        ViewGroup.LayoutParams params = navBar.getLayoutParams();
        params.width = getWidth(21);
        navBar.setLayoutParams(params);

        navHome.setText(navName);
        navKey.setText(navName1);
        navClassScroll.setText(navName2);
        navAnnounceScroll.setText(navName3);
    }

    private void closeMenu() {
        ViewGroup.LayoutParams params = navBar.getLayoutParams();
        params.width = getWidth(5);
        navBar.setLayoutParams(params);
        SIDE_MENU = false;
        navHome.setText("");
        navKey.setText("");
        navClassScroll.setText("");
        navAnnounceScroll.setText("");
    }

    private int getWidth(int percent) {
        int width = getResources().getDisplayMetrics().widthPixels;
        return (width * percent) / 100;
    }
}

