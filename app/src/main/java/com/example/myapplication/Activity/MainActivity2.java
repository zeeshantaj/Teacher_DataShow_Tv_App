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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.TeacherDataRecyclerAdapter;
import com.example.myapplication.BottomMenu.BottomMenuFragment;
import com.example.myapplication.Fragments.Bottom_Sheet_Fragment;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TeacherDataViewModel;
import com.example.myapplication.databinding.ActivityMain2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // Handle left button press
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // Handle right button press
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                // Handle up button press
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // Handle down button press
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.keyIcon) {
            Toast.makeText(this, "CLicked", Toast.LENGTH_SHORT).show();
            // Get the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  Bottom_Sheet_Fragment fragment = new Bottom_Sheet_Fragment();
            BottomMenuFragment fragment = new BottomMenuFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null); // Add the fragment to the back stack
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);

    }
}
