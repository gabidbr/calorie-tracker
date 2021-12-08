package com.example.calorietracker.room.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.calorietracker.room.entity.Ingredient;
import com.example.calorietracker.room.entity.User;

import java.time.LocalDate;

@Entity(primaryKeys = {"userId", "foodId"},
        indices = {@Index(value = "foodId")},
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Ingredient.class,
                        parentColumns = "foodId",
                        childColumns = "foodId",
                        onDelete = CASCADE
                )
        })
public class UserIngredientCrossRef {
        @NonNull
        public String userId;
        @NonNull
        public String foodId;
        public float calories;
        public String date;

        public UserIngredientCrossRef(@NonNull String userId, @NonNull String foodId, float calories, String date) {
                this.userId = userId;
                this.foodId = foodId;
                this.calories = calories;
                this.date = date;
        }
}
