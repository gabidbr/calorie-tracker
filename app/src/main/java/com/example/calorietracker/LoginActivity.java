package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.textView5);
        textView.setOnClickListener(text -> openPersonalDataActivity());
    }

    public void openPersonalDataActivity() {
        Intent personalDataActivity = new Intent(this, PersonalDataActivity.class);
        startActivity(personalDataActivity);
    }
}