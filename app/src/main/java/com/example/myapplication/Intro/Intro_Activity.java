package com.example.myapplication.Intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.Activity.MainActivity2;
import com.example.myapplication.R;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class Intro_Activity extends FancyWalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Key Set Fragment", "First of all you need to set the key then you start receiving class details and announcement.",R.drawable.key_intro);
        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Home Fragment", "This is where you showed up the received Data",R.drawable.home_intro);
        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("scroll time fragment", "This is where you can set the class & announcement data scroll time.",R.drawable.scroll_time_intro);




        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setIconLayoutParams(500,300,0,0,0,0);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setIconLayoutParams(500,300,0,0,0,0);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setIconLayoutParams(500,300,0,0,0,0);
        List<FancyWalkthroughCard> pages = new ArrayList<>();

        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);

        for (FancyWalkthroughCard page : pages) {
            page.setTitleColor(R.color.black);
            fancywalkthroughCard3.setBackgroundColor(R.color.white);
            page.setDescriptionColor(R.color.black);

        }


        setFinishButtonTitle("Get Started");
        showNavigationControls(true);
        setColorBackground(R.color.cardBg);
        //setImageBackground(R.drawable.restaurant);
        setInactiveIndicatorColor(R.color.grey);
        setActiveIndicatorColor(R.color.color4);
        setOnboardPages(pages);
        oneTimeView();
    }
    private void oneTimeView(){
        SharedPreferences sharedPreferences = getSharedPreferences("introShared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isIntro",true);
        editor.apply();
    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}