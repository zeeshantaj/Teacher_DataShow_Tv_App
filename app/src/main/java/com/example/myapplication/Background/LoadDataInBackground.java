package com.example.myapplication.Background;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapter.TeacherDataRecyclerAdapter;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.ViewModel.TeacherDataViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoadDataInBackground extends AsyncTask<Void, Void, Void> {

    private DatabaseReference keyReference;
    private TeacherDataViewModel viewModel;
    private Activity activity;
    private boolean isClassDataAvailable;
    private TeacherDataRecyclerAdapter classDataAdapter;
    private ViewPager2 classDataVP;
    private int classScrollTime;
    private Handler classSliderHandler;
    private Runnable classDataSlider;
    private LifecycleOwner lifecycleOwner;
    public LoadDataInBackground(LifecycleOwner lifecycleOwner, DatabaseReference keyReference, TeacherDataViewModel viewModel,
                            boolean isClassDataAvailable, TeacherDataRecyclerAdapter classDataAdapter,
                            ViewPager2 classDataVP, int classScrollTime, Handler classSliderHandler, Runnable classDataSlider) {
        this.lifecycleOwner = lifecycleOwner;
        this.keyReference = keyReference;
        this.viewModel = viewModel;
        this.isClassDataAvailable = isClassDataAvailable;
        this.classDataAdapter = classDataAdapter;
        this.classDataVP = classDataVP;
        this.classScrollTime = classScrollTime;
        this.classSliderHandler = classSliderHandler;
        this.classDataSlider = classDataSlider;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbKey = snapshot.child("EnteredKey").getValue(String.class);
                if (dbKey != null) {
                    viewModel.getTeacherDataList(dbKey).observe(lifecycleOwner, new Observer<List<DataModel>>() {
                        @Override
                        public void onChanged(List<DataModel> newDataList) {
                            if (!newDataList.isEmpty()) {
                                isClassDataAvailable = true;
                                classDataVP.setVisibility(View.VISIBLE);
                            } else {
                                isClassDataAvailable = false;
                                classDataVP.setVisibility(View.GONE);
                            }
                            classDataAdapter.setData(newDataList);
                            showNoDataImageView();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        classDataVP.setAdapter(classDataAdapter);
        setViewPagerProperties(classDataVP, classScrollTime, classSliderHandler, classDataSlider);
    }

    private void showNoDataImageView() {
        // Implement your logic to show or hide the no data image view

    }

    private void setViewPagerProperties(ViewPager2 viewPager2, int time, Handler handler, Runnable runnable) {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.15f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, time);
            }
        });
    }
}
