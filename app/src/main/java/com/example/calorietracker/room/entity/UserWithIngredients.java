package com.example.calorietracker.room.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithIngredients {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "foodId",
            associateBy = @Junction(UserIngredientCrossRef.class)
    )
    public List<Ingredient> ingredients;
}
