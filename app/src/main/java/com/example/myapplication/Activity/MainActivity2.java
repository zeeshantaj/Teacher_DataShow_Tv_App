package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.TeacherDataRecyclerAdapter;
import com.example.myapplication.Fragments.Bottom_Sheet_Fragment;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TeacherDataViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {
        private RecyclerView itemRecycler;
        private TeacherDataRecyclerAdapter recyclerAdapter;
        private List<DataModel> teacherDataList;
        private DatabaseReference databaseReference;

        private TextView uploadText;
        private String dbKey;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            itemRecycler = findViewById(R.id.recyclerView);
            recyclerAdapter = new TeacherDataRecyclerAdapter(new ArrayList<>());
            itemRecycler.setAdapter(recyclerAdapter);


            // Get showKey value
            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
            keyReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dbKey = snapshot.child("EnteredKey").getValue(String.class);
                    if (dbKey != null) {
                        // Initialize ViewModel and observe data changes
                        TeacherDataViewModel viewModel = new ViewModelProvider(MainActivity2.this).get(TeacherDataViewModel.class);
                        viewModel.getTeacherDataList(dbKey).observe(MainActivity2.this, new Observer<List<DataModel>>() {
                            @Override
                            public void onChanged(List<DataModel> newDataList) {
                                recyclerAdapter.setData(newDataList);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error: " + error.getMessage());
                }
            });


            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            itemRecycler.setLayoutManager(layoutManager);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.keyIcon){
            Toast.makeText(this, "CLicked", Toast.LENGTH_SHORT).show();
            // Get the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bottom_Sheet_Fragment fragment = new Bottom_Sheet_Fragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null); // Add the fragment to the back stack
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);

    }
}
