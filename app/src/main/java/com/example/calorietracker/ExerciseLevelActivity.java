package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExerciseLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_level);
    }

    public void lightlyActiveOnClick(View view) {
        Button button = (Button)findViewById(R.id.lightlyActive);
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);
        button.requestFocus();

        Button button2 = (Button)findViewById(R.id.sedentary);
        button2.setFocusable(false);
        button2.setFocusableInTouchMode(false);
        button2.requestFocus();

        Button button3 = (Button)findViewById(R.id.very_active);
        button3.setFocusable(false);
        button3.setFocusableInTouchMode(false);
        button3.requestFocus();
    }

    public void sedentaryOnClick(View view) {
        Button button = (Button)findViewById(R.id.sedentary);
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);
        button.requestFocus();

        Button button2 = (Button)findViewById(R.id.very_active);
        button2.setFocusable(false);
        button2.setFocusableInTouchMode(false);
        button2.requestFocus();

        Button button3 = (Button)findViewById(R.id.lightlyActive);
        button3.setFocusable(false);
        button3.setFocusableInTouchMode(false);
        button3.requestFocus();
    }

    public void veryActiveOnClick(View view) {
        Button button = (Button)findViewById(R.id.very_active);
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);
        button.requestFocus();

        Button button2 = (Button)findViewById(R.id.sedentary);
        button2.setFocusable(false);
        button2.setFocusableInTouchMode(false);
        button2.requestFocus();

        Button button3 = (Button)findViewById(R.id.lightlyActive);
        button3.setFocusable(false);
        button3.setFocusableInTouchMode(false);
        button3.requestFocus();
    }
}