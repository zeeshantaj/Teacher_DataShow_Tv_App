package com.example.myapplication.Intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Activity.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityIntroBinding;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class Intro_Activity extends FancyWalkthroughActivity {

    ActivityIntroBinding binding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityIntroBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Key Set Fragment", "First of all you need to set the key then you start receiving class details and announcement.",R.drawable.baseline_home_24);
        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Home Fragment", "This is where you showed up the received Data",R.drawable.baseline_home_24);
        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("Class scroll time fragment", "This is where you can set the class data scroll time.",R.drawable.baseline_home_24);
        FancyWalkthroughCard fancywalkthroughCard4 = new FancyWalkthroughCard("Announcement scroll time fragment", "This is where you can set the class data scroll time.",R.drawable.baseline_home_24);

        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard4.setBackgroundColor(R.color.white);
        fancywalkthroughCard4.setIconLayoutParams(300,300,0,0,0,0);
        List<FancyWalkthroughCard> pages = new ArrayList<>();

        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);
        pages.add(fancywalkthroughCard4);

        for (FancyWalkthroughCard page : pages) {
            page.setTitleColor(R.color.black);
            fancywalkthroughCard4.setBackgroundColor(R.color.white);
            page.setDescriptionColor(R.color.black);

        }


        setFinishButtonTitle("Get Started");
        showNavigationControls(true);
        setColorBackground(R.color.cardBg);
        //setImageBackground(R.drawable.restaurant);
        setInactiveIndicatorColor(R.color.grey);
        setActiveIndicatorColor(R.color.color4);
        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(this, MainActivity2.class));
    }
}