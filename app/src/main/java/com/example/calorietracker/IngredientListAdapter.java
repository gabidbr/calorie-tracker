package com.example.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.room.entity.Ingredient;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {
    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients;

    IngredientListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            Ingredient current = mIngredients.get(position);
            holder.ingredientItemView.setText(StringUtils.capitalize(current.foodName));
            holder.caloriesItemView.setText(String.format("%s calories", current.calories) );
        } else {
            holder.ingredientItemView.setText("No ingredient");
            holder.caloriesItemView.setText("No calories");
        }
    }

    void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }

    public Ingredient getIngredientAtPosition (int position) {
        return mIngredients.get(position);
    }

    public void remove(int position) {
        if (position < 0 || position >= mIngredients.size()) {
            return;
        }
        mIngredients.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientItemView, caloriesItemView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
            caloriesItemView = itemView.findViewById(R.id.caloriesOfIngredient);
        }
    }
}
