package com.example.calorietracker.food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nutrition {
    @SerializedName("caloricBreakdown")
    private CaloricBreakDown caloricBreakdown;
    @SerializedName("nutrients")
    private List<Nutrient> nutrients;

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public CaloricBreakDown getCaloricBreakdown() {
        return caloricBreakdown;
    }

    public void setCaloricBreakdown(CaloricBreakDown caloricBreakdown) {
        this.caloricBreakdown = caloricBreakdown;
    }
}
