package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calorietracker.food.FoodListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DashboardActivity.this, "You've been logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, FirstPageActivity.class));
            }
        });
    }

    public void onListOfFoodsClick(View view) {
        startActivity(new Intent(DashboardActivity.this, FoodListActivity.class));
    }
}