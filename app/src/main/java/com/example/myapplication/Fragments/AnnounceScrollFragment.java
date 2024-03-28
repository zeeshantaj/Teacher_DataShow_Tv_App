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

import com.example.myapplication.databinding.ScrollTimeLayoutBinding;

public class AnnounceScrollFragment extends Fragment {
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
            Toast.makeText(getActivity(), "Time Set "+binding.secTxt.getText().toString(), Toast.LENGTH_SHORT).show();

            int time = Integer.parseInt(binding.secTxt.getText().toString());

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("announceScrollTimeShared", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("announceScrollTime",time);
            editor.apply();
        });
    }
}
