package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.erkutaras.showcaseview.ShowcaseManager;
import com.example.myapplication.Activity.MainActivity2;
import com.example.myapplication.Adapter.AnnouncementAdapter;
import com.example.myapplication.Adapter.TeacherDataRecyclerAdapter;
import com.example.myapplication.Model.AnnouncementModel;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.MethodUtils;
import com.example.myapplication.ViewModel.TeacherDataViewModel;
import com.example.myapplication.databinding.HomeFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragment() {
    }
    private HomeFragmentBinding binding;
    private TeacherDataRecyclerAdapter classDataAdapter;
    private AnnouncementAdapter announcementAdapter;
    private Handler classSliderHandler = new Handler();
    private Handler announceSlideHandler = new Handler();
    private DatabaseReference keyReference;
    private boolean isClassDataAvailable = false;
    private boolean isAnnounceDataAvailable = false;
    private int classScrollTime ;
    private int announceScrollTime;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("classScrollTimeShared", Context.MODE_PRIVATE);
        classScrollTime = sharedPreferences.getInt("classScrollTime", 5);
        classScrollTime *= 1000;

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("announceScrollTimeShared", Context.MODE_PRIVATE);
        announceScrollTime = sharedPreferences1.getInt("announceScrollTime", 5);
        announceScrollTime *= 1000;

        classDataAdapter = new TeacherDataRecyclerAdapter(new ArrayList<>(),getActivity());
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(), getActivity());
        //String androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String androidId = MethodUtils.getSystemUid(getActivity());
        keyReference = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);

        getAnnouncementData();
        getClassData();
    }
        private void getClassData() {
        keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbKey = snapshot.child("EnteredKey").getValue(String.class);
                if (dbKey != null) {
                    // Initialize ViewModel and observe data changes
                    if (getActivity() != null) {

                        TeacherDataViewModel viewModel = new ViewModelProvider(getActivity()).get(TeacherDataViewModel.class);
                        viewModel.getTeacherDataList(dbKey).observe(getActivity(), new Observer<List<DataModel>>() {
                            @Override
                            public void onChanged(List<DataModel> newDataList) {
                                if (!newDataList.isEmpty()) {
                                    isClassDataAvailable = true;
                                    binding.classDataVP.setVisibility(View.VISIBLE);
                                } else {
                                    isClassDataAvailable = false;
                                    binding.classDataVP.setVisibility(View.GONE);
                                }
                                classDataAdapter.setData(newDataList);
                                showNoDataImageView();
                            }

                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
        binding.classDataVP.setAdapter(classDataAdapter);
        setViewPagerProperties(binding.classDataVP, classScrollTime, classSliderHandler, classDataSlider);


//        TeacherDataViewModel viewModel = new ViewModelProvider(getActivity()).get(TeacherDataViewModel.class);
//        new LoadDataInBackground(this,keyReference,viewModel,isClassDataAvailable,classDataAdapter,binding.classDataVP,classScrollTime,classSliderHandler,classDataSlider);
    }

    private void getAnnouncementData() {
        keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbKey = snapshot.child("EnteredKey").getValue(String.class);
                if (dbKey != null) {
                    // Initialize ViewModel and observe data changes
                    if (getActivity() != null) {
                        TeacherDataViewModel viewModel = new ViewModelProvider(getActivity()).get(TeacherDataViewModel.class);
                        viewModel.getAnnouncementData(dbKey).observe(getActivity(), new Observer<List<AnnouncementModel>>() {
                            @Override
                            public void onChanged(List<AnnouncementModel> announcementModels) {
                                if (!announcementModels.isEmpty()) {
                                    isAnnounceDataAvailable = true;
                                    binding.announceDataVP.setVisibility(View.VISIBLE);
                                } else {
                                    isAnnounceDataAvailable = false;
                                    binding.announceDataVP.setVisibility(View.GONE);
                                }
                                announcementAdapter.setData(announcementModels);
                                showNoDataImageView();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
        binding.announceDataVP.setAdapter(announcementAdapter);
        setViewPagerProperties(binding.announceDataVP,announceScrollTime,announceSlideHandler,announceDataSlider);
    }

    private void showNoDataImageView() {
        if (!isClassDataAvailable && !isAnnounceDataAvailable) {
            // Show image view
            binding.noDataToshowImg.setVisibility(View.VISIBLE);
        } else {
            // Hide image view
            binding.noDataToshowImg.setVisibility(View.GONE);
        }
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

    private Runnable classDataSlider = new Runnable() {
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
            classSliderHandler.postDelayed(this, classScrollTime);
        }
    };
   private Runnable announceDataSlider = new Runnable() {
        @Override
        public void run() {
//            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            int currentItem = binding.announceDataVP.getCurrentItem();
            int itemCount = binding.announceDataVP.getAdapter().getItemCount();
            if (currentItem < itemCount - 1) {
                binding.announceDataVP.setCurrentItem(currentItem + 1);
            } else {
                binding.announceDataVP.setCurrentItem(0);
            }

            // Repeat this runnable after a delay
            announceSlideHandler.postDelayed(this, announceScrollTime);
        }
    };

}
