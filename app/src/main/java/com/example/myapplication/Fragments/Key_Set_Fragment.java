package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Animation.ShakeAnimation;
import com.example.myapplication.Utils.MethodUtils;
import com.example.myapplication.databinding.FragmentKeyKeySetBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Key_Set_Fragment extends Fragment {

    public Key_Set_Fragment() {
        // Required empty public constructor
    }

    private DatabaseReference queryReference, insertReference;
    private String databaseKey,uploadedKey;
    private FragmentKeyKeySetBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKeyKeySetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        setKey();
    }


    private void setKey(){
        String androidId = MethodUtils.getSystemUid(getActivity());
        insertReference = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
        queryReference = FirebaseDatabase.getInstance().getReference("Tv_keys");
        queryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        databaseKey = dataSnapshot.child("EnteredKey").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        insertReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedKey = snapshot.child("EnteredKey").getValue(String.class);
                binding.showKey.setText(uploadedKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error firebase",error.getMessage());
                Toast.makeText(getActivity(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        binding.setKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = binding.edKey.getText().toString();
                if (key.isEmpty()){
                    ShakeAnimation.setAnimation(getActivity(), binding.edKey);
                    binding.edKey.setError("Key field is empty");
                    return;
                }
                if (databaseKey.equals(key)){
                    Toast.makeText(getActivity(), "Key Already exists try different key", Toast.LENGTH_SHORT).show();
                    return;
                }
                    HashMap<String,String> value = new HashMap<>();
                    value.put("EnteredKey",key);

                    insertReference.setValue(value).addOnCompleteListener(task -> {
                        binding.showKey.setText(key);
                        Toast.makeText(getActivity(), "key set successfully", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.showKey.setText(uploadedKey);
    }
}