package com.example.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bottom_Sheet_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bottom_Sheet_Fragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bottom_Sheet_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bottom_Sheet_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bottom_Sheet_Fragment newInstance(String param1, String param2) {
        Bottom_Sheet_Fragment fragment = new Bottom_Sheet_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextInputEditText enteredKey;
    private Button setKeyBtn;
    private DatabaseReference databaseReference, databaseReference1;
    private String androidId,databaseKey,showKey;
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

    @SuppressLint("HardwareIds")
    private void setKey(){
        androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Tv_keys");
        databaseReference.addValueEventListener(new ValueEventListener() {
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

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showKey = snapshot.child("EnteredKey").getValue(String.class);
                keyTextView.setText(showKey);
                Intent intent = new Intent();
                intent.putExtra("keyFromIntent", databaseKey);
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
                    enteredKey.setError("Key Already Exists try different key to entered ");
                    return;
                }
                else
                {

                    HashMap<String,String> value = new HashMap<>();
                    value.put("EnteredKey",key);

                    databaseReference1.setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getActivity(), "Key set Successfully", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        keyTextView.setText(showKey);
    }
}