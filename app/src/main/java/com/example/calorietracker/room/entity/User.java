package com.example.calorietracker.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @NonNull
    @PrimaryKey
    public String userId;
    public String firstName;
    public String caloriesDailyIntake;

    public User(@NonNull String userId, String firstName, String caloriesDailyIntake) {
        this.userId = userId;
        this.firstName = firstName;
        this.caloriesDailyIntake = caloriesDailyIntake;
    }
}
