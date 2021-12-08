package com.example.calorietracker.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "ingredient_table")
@TypeConverters(DateConverter.class)
public class Ingredient {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public Long ingredientId;

    public String foodId;

    public String foodName;

    public Integer calories;

    public String userId;

    public Date date;

    public Ingredient(String foodId, String foodName, Integer calories, String userId, Date date) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.calories = calories;
        this.userId = userId;
        this.date = date;
    }

    public Ingredient getIngredient(){
        return this;
    }
}
