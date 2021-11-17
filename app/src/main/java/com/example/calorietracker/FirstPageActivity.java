package com.example.calorietracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload(new DashboardActivity());
        }
    }

    private void reload(Activity activity) {
        startActivity(new Intent(FirstPageActivity.this, activity.getClass()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        TextView textView = findViewById(R.id.textView5);
        textView.setOnClickListener(text -> openPersonalDataActivity());
    }

    public void openPersonalDataActivity() {
        startActivity(new Intent(FirstPageActivity.this, StartActivity.class));
    }
}