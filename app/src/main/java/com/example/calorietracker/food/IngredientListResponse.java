package com.example.calorietracker.food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListResponse {
    @SerializedName("results")
    private List<IngredientResponse> ingredientsList;


    public List<IngredientResponse> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<IngredientResponse> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public String toString() {
        return "IngredientListResponse{" +
                "ingredientsList=" + ingredientsList +
                '}';
    }


}
