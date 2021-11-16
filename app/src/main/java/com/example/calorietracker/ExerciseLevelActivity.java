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
        setFocusOnButton(button, true);

        Button button2 = (Button)findViewById(R.id.sedentary);
        setFocusOnButton(button2, false);

        Button button3 = (Button)findViewById(R.id.very_active);
        setFocusOnButton(button3, false);
    }

    private void setFocusOnButton(Button button, boolean b) {
        button.setFocusable(b);
        button.setFocusableInTouchMode(b);
        button.requestFocus();
    }

    public void sedentaryOnClick(View view) {
        Button button = (Button)findViewById(R.id.sedentary);
        setFocusOnButton(button, true);

        Button button2 = (Button)findViewById(R.id.very_active);
        setFocusOnButton(button2, false);

        Button button3 = (Button)findViewById(R.id.lightlyActive);
        setFocusOnButton(button3, false);
    }

    public void veryActiveOnClick(View view) {
        Button button = (Button)findViewById(R.id.very_active);
        setFocusOnButton(button, true);

        Button button2 = (Button)findViewById(R.id.sedentary);
        setFocusOnButton(button2, false);

        Button button3 = (Button)findViewById(R.id.lightlyActive);
        setFocusOnButton(button3, false);
    }
}