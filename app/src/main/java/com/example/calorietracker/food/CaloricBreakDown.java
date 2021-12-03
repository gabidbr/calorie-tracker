package com.example.calorietracker.food;

import com.google.gson.annotations.SerializedName;

public class CaloricBreakDown {
    @SerializedName("percentProtein")
    private float protein;

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    @SerializedName("percentFat")
    private float fat;
    @SerializedName("percentCarbs")
    private float carbs;
}
