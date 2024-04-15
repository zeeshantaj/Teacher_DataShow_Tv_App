package com.example.myapplication.Activity;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.BrowseFrameLayout;

import android.content.Context;
import android.content.IntentFilter;
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

public class MainActivity2 extends AppCompatActivity implements View.OnKeyListener {

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

        showGuide();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkCheckReceiver);
    }
    private void showGuide(){
        ShowcaseManager.Builder builder = new ShowcaseManager.Builder();
        builder.context(this)
                .key("KEY")
                .developerMode(true)
                .view(navKey)
                .descriptionTitle("you can either upload image or text data")
                .descriptionText("touch and hold on the image, to remove image\nor clear text to upload image")
                .buttonText("Done")
                .buttonVisibility(true)
                .cancelButtonVisibility(true)
                .cancelButtonColor(getResources().getColor(R.color.white))
                .add()
                .build()
                .show();
    }
}

