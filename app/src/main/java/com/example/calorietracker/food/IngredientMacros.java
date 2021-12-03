package com.example.calorietracker.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.calorietracker.R;

public class IngredientMacros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_macros);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String foodId = extras.getString("foodId");
            System.out.println(foodId);
        }
    }
}