package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.calorietracker.fragment.BlankFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        insertFragment(new BlankFragment());
    }

    private void insertFragment(BlankFragment blankFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.thePlaceHolder, blankFragment);
        fragmentTransaction.commit();
    }

    public void goToPreviousActivityOnClick(View view) {
        finish();
    }
}