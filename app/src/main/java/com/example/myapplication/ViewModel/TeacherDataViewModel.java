package com.example.myapplication.ViewModel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.AnnouncementModel;
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
    private  MutableLiveData<List<AnnouncementModel>> announcementData;
    private String showKey;
    public LiveData<List<DataModel>> getTeacherDataList(String showKey) {
        if (teacherDataListLiveData == null) {
            teacherDataListLiveData = new MutableLiveData<>();
            this.showKey = showKey;
            loadTeacherData();
        }
        return teacherDataListLiveData;
    }

    public LiveData<List<AnnouncementModel>> getAnnouncementData(String showKey){
        if (announcementData == null){
            announcementData = new MutableLiveData<>();
            this.showKey = showKey;
            loadAnnouncementData();
        }
        return announcementData;
    }
    private void loadTeacherData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teacher_Data");
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
    private void loadAnnouncementData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Announcement");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AnnouncementModel> modelList = new ArrayList<>();

                for (DataSnapshot uidSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot keySnapshot : uidSnapshot.getChildren()) {
                        String key = keySnapshot.child("key").getValue(String.class);
                        if (key != null && key.equals(showKey)) {
                            AnnouncementModel model = new AnnouncementModel(); // Create a new instance only if the key matches
                            if (keySnapshot.child("title").exists()) {
                                model.setTitle(keySnapshot.child("title").getValue(String.class));
                                model.setCurrent_date(keySnapshot.child("current_date").getValue(String.class));
                                model.setDue_date(keySnapshot.child("due_date").getValue(String.class));
                                model.setKey(keySnapshot.child("key").getValue(String.class));
                                model.setDescription(keySnapshot.child("description").getValue(String.class));
                                model.setId(keySnapshot.child("id").getValue(String.class));
                            }
                            if (keySnapshot.child("imageUrl").exists()) {
                                model.setImageUrl(keySnapshot.child("imageUrl").getValue(String.class));
                            }

                            modelList.add(model);
                        }
                    }
                }
                // Set the value of LiveData after the loop to contain all items
                announcementData.setValue(modelList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }
    private void removeAnnouncement(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Announcement");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot uidSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot keySnapshot : uidSnapshot.getChildren()) {
                        String key = keySnapshot.child("key").getValue(String.class);
                        if (key != null && key.equals(showKey)) {

                            if (keySnapshot.child("title").exists()) {
                                String due_date = keySnapshot.child("due_date").getValue(String.class);
                            }

                            if (keySnapshot.child("imageUrl").exists()) {
                                String due_date = keySnapshot.child("due_date").getValue(String.class);
                            }


                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

}
