package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Utils.MethodUtils;
import com.example.myapplication.databinding.ScrollTimeLayoutBinding;

public class ClassScrollFragment extends Fragment {

    private ScrollTimeLayoutBinding binding;
    private int count = 5;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ScrollTimeLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();




        binding.increaseBtn.setOnClickListener(v -> {
            if (count == 60){
                Toast.makeText(getActivity(), "Seconds can not be greater than 60", Toast.LENGTH_SHORT).show();
                return;
            }
            count++;
            binding.secTxt.setText(String.valueOf(count));
        });
        binding.decreaseBtn.setOnClickListener(v -> {
            if (count == 5){
                Toast.makeText(getActivity(), "Seconds can not be less than 5", Toast.LENGTH_SHORT).show();
                return;
            }
            
            count--;
            binding.secTxt.setText(String.valueOf(count));
        });
        binding.timeSetBtn.setOnClickListener(v -> {
            int pos = binding.scrollTimeSpinner.getSelectedItemPosition(); 
            if (pos <= 0){
                Toast.makeText(getActivity(), "Please Selected Item \nclass scroll time or announcement scroll time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pos == 1){
                Toast.makeText(getActivity(), "Time Set "+binding.secTxt.getText().toString(), Toast.LENGTH_SHORT).show();
                int time = Integer.parseInt(binding.secTxt.getText().toString());
                MethodUtils.createSharedPreference(getActivity(), "classScrollTimeShared","classScrollTime",time);
            }
            if (pos == 2) {
                Toast.makeText(getActivity(), "Time Set "+binding.secTxt.getText().toString(), Toast.LENGTH_SHORT).show();
                int time = Integer.parseInt(binding.secTxt.getText().toString());
                MethodUtils.createSharedPreference(getActivity(),"announceScrollTimeShared","announceScrollTime",time);
            }
        });
        getClassTime();
        getAnnounceTime();
    }
    private void getClassTime(){
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("classScrollTimeShared", Context.MODE_PRIVATE);
        count = sharedPreferences1.getInt("classScrollTime", 5);
        String classFormattedStr = String.format("%s is preset time for class scroll",count);
        binding.classPresetST.setText(classFormattedStr);
    }
    private void getAnnounceTime(){
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("announceScrollTimeShared", Context.MODE_PRIVATE);
        count = sharedPreferences2.getInt("announceScrollTime", 5);
        String classFormattedStr1 = String.format("%s is preset time for class scroll", count);
        binding.classPresetST.setText(classFormattedStr1);
    }
}
