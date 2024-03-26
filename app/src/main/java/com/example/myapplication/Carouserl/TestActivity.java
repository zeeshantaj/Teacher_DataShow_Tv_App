package com.example.myapplication.Carouserl;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapter.AnnouncementAdapter;
import com.example.myapplication.Adapter.TeacherDataRecyclerAdapter;
import com.example.myapplication.Model.AnnouncementModel;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.ViewModel.TeacherDataViewModel;
import com.example.myapplication.databinding.ActivityTestBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {



    private TeacherDataRecyclerAdapter classDataAdapter;
    private AnnouncementAdapter announcementAdapter;
    private Handler sliderHandler = new Handler();
    private ActivityTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        classDataAdapter = new TeacherDataRecyclerAdapter(new ArrayList<>());
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(),this);
        String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
        keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String dbKey = snapshot.child("EnteredKey").getValue(String.class);
                if (dbKey != null) {
                    // Initialize ViewModel and observe data changes
                    TeacherDataViewModel viewModel = new ViewModelProvider(TestActivity.this).get(TeacherDataViewModel.class);
                    viewModel.getTeacherDataList(dbKey).observe(TestActivity.this, new Observer<List<DataModel>>() {
                        @Override
                        public void onChanged(List<DataModel> newDataList) {
                            if (!newDataList.isEmpty()){
                                binding.noDataToshowImg.setVisibility(View.GONE);
                            }
                            else {
                                binding.noDataToshowImg.setVisibility(View.VISIBLE);
                            }
                            classDataAdapter.setData(newDataList);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });

        keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbKey = snapshot.child("EnteredKey").getValue(String.class);
                if (dbKey != null) {
                    // Initialize ViewModel and observe data changes
                    TeacherDataViewModel viewModel = new ViewModelProvider(TestActivity.this).get(TeacherDataViewModel.class);
                    viewModel.getAnnouncementData(dbKey).observe(TestActivity.this, new Observer<List<AnnouncementModel>>() {
                        @Override
                        public void onChanged(List<AnnouncementModel> announcementModels) {
                            announcementAdapter.setData(announcementModels);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });

        binding.classDataVP.setAdapter(classDataAdapter);
        binding.classDataVP.setClipToPadding(false);
        binding.classDataVP.setClipChildren(false);
        binding.classDataVP.setOffscreenPageLimit(5);
        binding.classDataVP.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.classDataVP.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        binding.classDataVP.setPageTransformer(compositePageTransformer);
        binding.classDataVP.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });

    }
    private void getAnnouncementData(){

    }


    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
//            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            int currentItem = binding.classDataVP.getCurrentItem();
            int itemCount = binding.classDataVP.getAdapter().getItemCount();
            if (currentItem < itemCount - 1) {
                binding.classDataVP.setCurrentItem(currentItem + 1);
            } else {
                binding.classDataVP.setCurrentItem(0);
            }

            // Repeat this runnable after a delay
            sliderHandler.postDelayed(this, 3000);
        }
    };
}