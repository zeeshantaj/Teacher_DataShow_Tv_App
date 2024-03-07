package com.example.myapplication.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherDataViewModel extends ViewModel  {
    private MutableLiveData<List<DataModel>> teacherDataListLiveData;
    private DatabaseReference databaseReference;
    private String showKey;

    public LiveData<List<DataModel>> getTeacherDataList(String showKey) {
        if (teacherDataListLiveData == null) {
            teacherDataListLiveData = new MutableLiveData<>();
            this.showKey = showKey;
            loadTeacherData();
        }
        return teacherDataListLiveData;
    }

    private void loadTeacherData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher_Data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DataModel> newDataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.child("key").getValue(String.class);
                    if (key != null && key.equals(showKey)) {
                        DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                        newDataList.add(dataModel);
                    }
                }
                teacherDataListLiveData.setValue(newDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }
}
