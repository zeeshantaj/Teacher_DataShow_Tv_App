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

import com.example.myapplication.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    public HomeFragment(){

    }

    private HomeFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("classScrollTimeShared", Context.MODE_PRIVATE);
        int time = sharedPreferences.getInt("classScrollTime",5);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("announceScrollTimeShared", Context.MODE_PRIVATE);
        int time1 = sharedPreferences1.getInt("announceScrollTime", 5);

        Toast.makeText(getActivity(), "time "+time, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "time "+time1, Toast.LENGTH_SHORT).show();
    }
}
