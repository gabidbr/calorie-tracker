package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PersonalDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
    }

    public void currentWeightOnClick(View view) {
        Intent currentWeightActivity = new Intent(this, CurrentWeightActivity.class);
        startActivity(currentWeightActivity);
    }
}