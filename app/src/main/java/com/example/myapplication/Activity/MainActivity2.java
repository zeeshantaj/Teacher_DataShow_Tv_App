package com.example.myapplication.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.BrowseFrameLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Fragments.AnnounceScrollFragment;
import com.example.myapplication.Fragments.ClassScrollFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.fragment_key_set;
import com.example.myapplication.R;

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

