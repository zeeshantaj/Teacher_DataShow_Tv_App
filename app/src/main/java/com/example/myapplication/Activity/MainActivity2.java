package com.example.myapplication.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.BrowseFrameLayout;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Network.NetworkCheckReceiver;
import com.example.myapplication.Fragments.AnnounceScrollFragment;
import com.example.myapplication.Fragments.ClassScrollFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.Key_Set_Fragment;
import com.example.myapplication.R;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;

public class MainActivity2 extends FragmentActivity implements View.OnKeyListener {

    private TextView navHome;
    public TextView navKey;
    private TextView navClassScroll;
    private BrowseFrameLayout navBar;
    private boolean SIDE_MENU = false;
    private final String navName = "Home";
    private final String navName1 = "Set Key";
    private final String navName2 = "Set class scroll time";
    private final String navName3 = "Set Announcement scroll time";
    private NetworkCheckReceiver networkCheckReceiver;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    int type = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navHome = findViewById(R.id.navHome);
        navKey = findViewById(R.id.navKey);
        navClassScroll = findViewById(R.id.navClassScroll);
        navBar = findViewById(R.id.navBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navHome.setText("");
        navKey.setText("");
        navClassScroll.setText("");

        navHome.setOnKeyListener(this);
        navKey.setOnKeyListener(this);
        navClassScroll.setOnKeyListener(this);


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

        networkCheckReceiver = new NetworkCheckReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkCheckReceiver,intentFilter);

        SharedPreferences sharedPreferences1 = getSharedPreferences("showcaseShared", Context.MODE_PRIVATE);
        boolean isTrue = sharedPreferences1.getBoolean("showcase", true);
        if (isTrue){
            showGuide("This is Navigation bar","All the Crucial things that you need for customization are located here", navHome);
        }
        changeFragment(new HomeFragment());

    }

    private void putSharedPreference(boolean bool){
        SharedPreferences sharedPreferences = getSharedPreferences("showcaseShared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("showcase",bool);
        editor.apply();
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
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            type++;

            // Dismiss the previous guide view if it's showing
            if (mGuideView != null && mGuideView.isShowing()) {
                mGuideView.dismiss();
            }

            // Show the guide for the next target
            switch (type) {
                case 1:
                    showGuide("This is where you can set the app key",
                            "You have to set the key here in order to\n receive data from Class Connect Mobile App", navKey);
                    break;
                case 2:
                    showGuide("This is Class scroll time fragment",
                            "This is where you can set the auto scrolling time of Class data", navClassScroll);
                    break;
                case 3:
                    showGuide("This is Home Fragment",
                            "This is where you showed up the received data", navHome);
                    break;
                case 4:
                    putSharedPreference(false);
                    break;
            }
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
                    changeFragment(new Key_Set_Fragment());
                    closeMenu();
                } else if (v.getId() == R.id.navClassScroll) {
                    changeFragment(new ClassScrollFragment());
                    closeMenu();
                }
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
    }

    private void closeMenu() {
        ViewGroup.LayoutParams params = navBar.getLayoutParams();
        params.width = getWidth(5);
        navBar.setLayoutParams(params);
        SIDE_MENU = false;
        navHome.setText("");
        navKey.setText("");
        navClassScroll.setText("");
    }

    private int getWidth(int percent) {
        int width = getResources().getDisplayMetrics().widthPixels;
        return (width * percent) / 100;
    }
    private void showGuide(String title,String content,View targetId){
        if (mGuideView != null) {
            mGuideView.dismiss(); // Dismiss previous guide view
        }

        builder = new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(content)
                .setGravity(Gravity.auto) // optional
                .setDismissType(DismissType.anywhere) // optional - default DismissType.targetView
                .setTargetView(targetId)
                .setContentTextSize(22) // optional
                .setTitleTextSize(24);// optional
//                .setGuideListener(view -> {
//                    // Increment type to show the next target
//                    type++;
//                    switch (type) {
//                        case 1:
//                            showGuide("This is where you can set the app key",
//                                    "You have to set the key here in order to\n receive data from Class Connect Mobile App", navKey);
//                            break;
//                        case 2:
//                            showGuide("This is Class scroll time fragment",
//                                    "This is where you can set the auto scrolling time of Class data", navClassScroll);
//                            break;
//                        case 3:
//                            showGuide("This is Announcement scroll time fragment",
//                                    "This is where you can set the auto scrolling time of announcement data", navAnnounceScroll);
//                            break;
//                        case 4:
//                            showGuide("This is Home Fragment",
//                                    "This is where you showed up the received data", navHome);
//                            break;
//                        case 5:
//                            putSharedPreference(false);
//                            break;
//                    }
//                });

        mGuideView = builder.build();
        mGuideView.show();
//        builder = new GuideView.Builder(this)
//                .setTitle(title)
//                .setContentText(content)
//                .setGravity(Gravity.auto) //optional
//                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
//                .setTargetView(targetId)
//                .setContentTextSize(22)//optional
//                .setTitleTextSize(24)//optional
//                .setGuideListener(view -> {
//                    for (type = 1; type <= 5; type++){
//                        if (type == 1){
//                            showGuide("This is where you can set the app key",
//                                    "You have to set the key here in order to\n receive data from Class Connect Mobile App",navKey);
//                        }
//                        else if (type == 2){
//                            showGuide("This is Class scroll time fragment",
//                                    "This is where you can set the auto scrolling time of Class data",navClassScroll);
//                        }else if (type == 3){
//                            showGuide("This is Announcement scroll time fragment",
//                                    "This is where you can set the auto scrolling time of announcement data",navAnnounceScroll);
//                        }else if (type == 4){
//                            showGuide("This is Home Fragment",
//                                    "This is where you showed up the received data",navHome);
//                        }
//                        if (type == 5){
//                            putSharedPreference(false);
//                        }
//                    }
//
//                    mGuideView = builder.build();
//                    mGuideView.show();
//                });
//                mGuideView = builder.build();
//                mGuideView.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkCheckReceiver);
    }
}

