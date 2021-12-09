package com.example.calorietracker.food;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.R;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView foodCaloriesTextView, foodNameTextView;
    ImageView foodImageView;
    ConstraintLayout parentLayout;

    public TextView getFoodCaloriesTextView() {
        return foodCaloriesTextView;
    }

    public TextView getFoodNameTextView() {
        return foodNameTextView;
    }

    public ImageView getFoodImageView() {
        return foodImageView;
    }

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        parentLayout = itemView.findViewById(R.id.constraintLayoutForCardView);
        foodCaloriesTextView = itemView.findViewById(R.id.foodCaloriesTextView);
        foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
        foodImageView = itemView.findViewById(R.id.foodImageView);
    }
}
