package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Bottom_Sheet_Fragment extends BottomSheetDialogFragment {

    public Bottom_Sheet_Fragment() {
        // Required empty public constructor
    }

    private TextInputEditText enteredKey;
    private Button setKeyBtn;
    private DatabaseReference queryReference, insertReference;
    private String androidId,databaseKey, uploadedKey;
    private TextView keyTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom__sheet_, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        enteredKey = view.findViewById(R.id.edKey);
        setKeyBtn = view.findViewById(R.id.setKeyBtn);
        keyTextView = view.findViewById(R.id.showKey);
        //setKey();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setKey();
    }


    private void setKey(){
        androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        insertReference = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
        queryReference = FirebaseDatabase.getInstance().getReference("Tv_keys");
        queryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        databaseKey = dataSnapshot.child("EnteredKey").getValue(String.class);
//
                        Log.d("databaseKey", databaseKey);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        insertReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedKey = snapshot.child("EnteredKey").getValue(String.class);
                keyTextView.setText(uploadedKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error firebase",error.getMessage());
            }
        });


        setKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = enteredKey.getText().toString();
                if (key.isEmpty()){
                    enteredKey.setError("Key field is empty");
                    return;
                }
                if (databaseKey.equals(key)){
                    Toast.makeText(getActivity(), "Key Already exists try different key", Toast.LENGTH_SHORT).show();
                    return;
                }
                    HashMap<String,String> value = new HashMap<>();
                    value.put("EnteredKey",key);

                    insertReference.setValue(value).addOnCompleteListener(task -> {
                        keyTextView.setText(key);
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
        keyTextView.setText(uploadedKey);
    }
}