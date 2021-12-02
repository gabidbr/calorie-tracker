package com.example.calorietracker.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.R;

import java.io.IOException;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.Viewholder> {

    private final Context context;
    private final IngredientListResponse ingredientList;

    public IngredientsAdapter(Context context, IngredientListResponse ingredientList) throws IOException {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ingredient_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.Viewholder holder, int position) {
        IngredientResponse ingredientResponse = ingredientList.getIngredientsList().get(position);
        holder.foodCaloriesTextView.setText("123");
        holder.foodNameTextView.setText(ingredientResponse.getName());


    }

    @Override
    public int getItemCount() {
        return ingredientList.getIngredientsList().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView foodCaloriesTextView, foodNameTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foodCaloriesTextView = itemView.findViewById(R.id.foodCaloriesTextView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
        }
    }
}
