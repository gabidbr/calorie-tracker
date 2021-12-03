package com.example.calorietracker.food;

import com.google.gson.annotations.SerializedName;

public class Nutrition {
    @SerializedName("caloricBreakdown")
    private CaloricBreakDown caloricBreakdown;

    public CaloricBreakDown getCaloricBreakdown() {
        return caloricBreakdown;
    }

    public void setCaloricBreakdown(CaloricBreakDown caloricBreakdown) {
        this.caloricBreakdown = caloricBreakdown;
    }
}
