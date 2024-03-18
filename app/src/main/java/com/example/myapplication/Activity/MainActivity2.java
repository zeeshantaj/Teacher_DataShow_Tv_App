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

    //        teacherDataList = new ArrayList<>();
    //        itemRecycler = findViewById(R.id.recyclerView);
    //        uploadText = findViewById(R.id.uploadDataText);
    //
    //
    //
    //
    //
    //        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
    //        itemRecycler.setLayoutManager(layoutManager);
    //
    //
    //
    //        DataModel dataModel = new DataModel("Teacher 1", "Subject 1", "Department 1", "Topic 1", "Room 1", "1", "07:10:00:PM");
    //        DataModel dataModel1 = new DataModel("Teacher 2", "Subject 2", "Department 2", "Topic 2", "Room 2", "2", "07:09:00:PM");
    //        DataModel dataModel2 = new DataModel("Teacher 3", "Subject 3", "Department 3", "Topic 3", "Room 3", "3", "07:08:00:PM");
    //        DataModel dataModel3 = new DataModel("Teacher 4", "Subject 4", "Department 4", "Topic 4", "Room 4", "4", "07:07:00:PM");
    //
    //        teacherDataList.add(dataModel);
    //        teacherDataList.add(dataModel1);
    //        teacherDataList.add(dataModel2);
    //        teacherDataList.add(dataModel3);
    //
    //
    //        // Create and set the RecyclerAdapter with the sample data
    //        recyclerAdapter = new RecyclerAdapter(this, teacherDataList);
    //        itemRecycler.setAdapter(recyclerAdapter);
    //        if (recyclerAdapter.getItemCount() <= 0){
    //            uploadText.setVisibility(View.VISIBLE);
    //        }
    //        uploadText.setVisibility(View.GONE);

            // Initialize Firebase Database reference
    //        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher_Data");
    //
    //        // Add a ValueEventListener to listen for changes in the database
    //        databaseReference.addValueEventListener(new ValueEventListener() {
    //            @Override
    //            public void onDataChange(@NonNull DataSnapshot snapshot) {
    //                // Clear the existing data to avoid duplicates
    //                recyclerAdapter.clearData();
    //
    //                // Iterate through the dataSnapshot and get the DataModel objects
    //                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
    //                    DataModel dataModel = dataSnapshot.getValue(DataModel.class);
    //                    recyclerAdapter.addData(dataModel);
    //                }
    //
    //                // Notify the adapter that the data has changed
    //                recyclerAdapter.notifyDataSetChanged();
    //            }
    //
    //            @Override
    //            public void onCancelled(@NonNull DatabaseError error) {
    //                // Handle any errors
    //                Log.e("Firebase", "Error: " + error.getMessage());
    //            }
    //        });




    //        DataModel dataModel = new DataModel("Teacher 1", "Subject 1", "Department 1", "Topic 1", "Room 1", "1", "07:10:00:PM");
    //        DataModel dataModel1 = new DataModel("Teacher 2", "Subject 2", "Department 2", "Topic 2", "Room 2", "2", "07:09:00:PM");
    //        DataModel dataModel2 = new DataModel("Teacher 3", "Subject 3", "Department 3", "Topic 3", "Room 3", "3", "07:08:00:PM");
    //        DataModel dataModel3 = new DataModel("Teacher 4", "Subject 4", "Department 4", "Topic 4", "Room 4", "4", "07:07:00:PM");
    //
    //        teacherDataList.add(dataModel);
    //        teacherDataList.add(dataModel1);
    //        teacherDataList.add(dataModel2);
    //        teacherDataList.add(dataModel3);
    //        // Show the loading layout initially
    //        recyclerAdapter = new RecyclerAdapter(this,teacherDataList);
    ////        recyclerAdapter.addLoading(); // Show the loading layout
    ////        recyclerAdapter.addAll(sampleData);
    //        itemRecycler.setAdapter(recyclerAdapter);

            // Simulate data fetching delay with Handler (you should replace this with your actual data fetching)
    //        new Handler().postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                // Once you have the actual data, remove the loading layout and add the data to the adapter
    //
    //                recyclerAdapter.removeLoading(); // Remove the loading layout
    //               // teacherDataList.addAll(getSampleData()); // Replace getSampleData() with your actual data retrieval method
    //                teacherDataList.addAll(sampleData);
    //                recyclerAdapter.addAll(teacherDataList); // Add the actual data to the adapter
    //                recyclerAdapter.notifyDataSetChanged();
    //            }
    //        }, 2000); // 2 seconds delay (you should replace this with the actual data fetching process)

       //     getRecyclerData();
        }

        private void getRecyclerData(){
//            //for key retrieve
//            DatabaseReference databaseReference1;
//            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//            databaseReference1 = FirebaseDatabase.getInstance().getReference("Tv_keys").child(androidId);
//            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    showKey = snapshot.child("EnteredKey").getValue(String.class);
//                    }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.e("error firebase",error.getMessage());
//                }
//            });
//            // end
//            teacherDataList = new ArrayList<>();
//            itemRecycler = findViewById(R.id.recyclerView);
//            uploadText = findViewById(R.id.uploadDataText);
//            recyclerAdapter = new RecyclerAdapter(this,teacherDataList);
//
//            itemRecycler.setAdapter(recyclerAdapter);
//
//            databaseReference = FirebaseDatabase.getInstance().getReference("Teacher_Data");
//            recyclerAdapter.showLoading();
//            // Add a ValueEventListener to listen for changes in the database
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    List<DataModel> newDataList = new ArrayList<>(); // Create a temporary list to hold the new data
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        String key = dataSnapshot.child("key").getValue(String.class);
//                        if (key != null && key.equals(showKey)){
//                            System.out.println("keyMatched");
//                            DataModel dataModel = dataSnapshot.getValue(DataModel.class);
//                            newDataList.add(dataModel);
//                        }
//                        else {
//                            System.out.println("notMatched");
//                        }
//                    }
//                    teacherDataList.clear();
//                    teacherDataList.addAll(newDataList);
//
//                    recyclerAdapter.notifyDataSetChanged();
//                    recyclerAdapter.hideLoading();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Handle any errors
//                    Log.e("Firebase", "Error: " + error.getMessage());
//                }
//            });
//
//            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
//            itemRecycler.setLayoutManager(layoutManager);
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
