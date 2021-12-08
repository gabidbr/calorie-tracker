package com.example.calorietracker.room.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.calorietracker.room.entity.Ingredient;
import com.example.calorietracker.room.entity.User;
import com.example.calorietracker.room.entity.UserIngredientCrossRef;

import java.util.List;

public class IngredientsWithUsers {
    @Embedded
    public Ingredient ingredient;

    @Relation(
            parentColumn = "foodId",
            entityColumn = "userId",
            associateBy = @Junction(UserIngredientCrossRef.class)
    )
    public List<User> users;
}
