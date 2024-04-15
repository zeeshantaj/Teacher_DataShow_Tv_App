package com.example.myapplication.Activity;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
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

import com.erkutaras.showcaseview.ShowcaseManager;
import com.example.myapplication.Network.NetworkCheckReceiver;
import com.example.myapplication.Fragments.AnnounceScrollFragment;
import com.example.myapplication.Fragments.ClassScrollFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.fragment_key_set;
import com.example.myapplication.R;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class MainActivity2 extends FragmentActivity implements View.OnKeyListener {

    private TextView navHome;
    public TextView navKey;
    private TextView navClassScroll;
    private TextView navAnnounceScroll;
    private BrowseFrameLayout navBar;
    private boolean SIDE_MENU = false;
    private final String navName = "Home";
    private final String navName1 = "Set Key";
    private final String navName2 = "Set class scroll time";
    private final String navName3 = "Set Announcement scroll time";
    private NetworkCheckReceiver networkCheckReceiver;

    private int type = 1;

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

        networkCheckReceiver = new NetworkCheckReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkCheckReceiver,intentFilter);
        SharedPreferences sharedPreferences1 = getSharedPreferences("showcaseShared", Context.MODE_PRIVATE);
        boolean isTrue = sharedPreferences1.getBoolean("showcase", true);
        if (isTrue){

        }
        showGuide("This is Navigation bar","All the Crucial things that you need for customization are located here", navHome,type);
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
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT
            ||keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN){

        //`    showGuide("This is Navigation bar","All the Crucial things that you need for customization are located here", navBar,type);
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
                    type++;
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
    private void showGuide(String title,String content,View targetId,int type){

        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(content)
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(targetId)
                .setGuideListener(view -> {
                    if (type == 1){
                        showGuide("This is where you can set the app key",
                                "You have to set the key here in order to\n receive data from Class Connect Mobile App",navKey,2);
                    }
                    else if (type == 2){
                        showGuide("This is Class scroll time fragment",
                                "This is where you can set the auto scrolling time of Class data",navClassScroll,3);
                    }else if (type == 3){
                        showGuide("This is Announcement scroll time fragment",
                                "This is where you can set the auto scrolling time of announcement data",navAnnounceScroll,4);
                    }else if (type == 4){
                        showGuide("This is Home Fragment",
                                "This is where you showed up the received data",navHome,5);
                    }
                    if (type == 5){
                        putSharedPreference(false);
                    }
                })
                .setContentTextSize(22)//optional
                .setTitleTextSize(24)//optional
                .build()
                .show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkCheckReceiver);
    }
}

