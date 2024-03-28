package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ScrollTimeLayoutBinding;

public class ClassScrollFragment extends Fragment {

    private ScrollTimeLayoutBinding binding;
    private int count = 3;
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
            count++;
            binding.secTxt.setText(String.valueOf(count));
        });
        binding.decreaseBtn.setOnClickListener(v -> {
            count--;
            binding.secTxt.setText(String.valueOf(count));
        });
        binding.timeSetBtn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Time "+binding.secTxt.getText().toString(), Toast.LENGTH_SHORT).show();
        });
    }
}
