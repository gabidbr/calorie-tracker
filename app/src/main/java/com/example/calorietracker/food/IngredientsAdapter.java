package com.example.calorietracker.food;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    private final IngredientListResponse ingredientList;
    private Context mContext;
    public IngredientsAdapter(Context context, IngredientListResponse ingredientList) throws IOException {
        this.ingredientList = ingredientList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ingredient_view, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        IngredientResponse ingredientResponse = ingredientList.getIngredientsList().get(position);
        String photoUrl = "https://spoonacular.com/cdn/ingredients_100x100/"+ ingredientResponse.getImage();
        Picasso.get().load(photoUrl).fit().into(holder.foodImageView);
        holder.foodNameTextView.setText(ingredientResponse.getName());
        holder.parentLayout.setOnClickListener((view)->{
            Intent intent = new Intent(mContext, IngredientMacros.class);
            intent.putExtra("foodId", ingredientResponse.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.getIngredientsList().size();
    }

}
