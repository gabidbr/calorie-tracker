package com.example.calorietracker.food;

import com.google.gson.annotations.SerializedName;

public class IngredientInfo {
    private String id;
    private String name;
    private String image;
    @SerializedName("nutrition")
    private Nutrition nutrition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
