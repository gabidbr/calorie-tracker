package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TargetWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_weight);
    }

    public void heightOnClick(View view) {
        Intent exerciseLevelActivity = new Intent(this, ExerciseLevelActivity.class);
        startActivity(exerciseLevelActivity);
    }
}