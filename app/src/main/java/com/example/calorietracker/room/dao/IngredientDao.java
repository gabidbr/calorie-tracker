package com.example.calorietracker.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.calorietracker.room.entity.DateConverter;
import com.example.calorietracker.room.entity.Ingredient;

import java.util.Date;
import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient_table WHERE userId = :currentUserId AND date >= :today")
    LiveData<List<Ingredient>> getAllIngredientsFromToday(String currentUserId, Long today);

    @Query("SELECT CAST(SUM(calories) AS INT) FROM ingredient_table WHERE userId = :currentUserId AND date >= :today")
    LiveData<Integer> getCaloriesSum(String currentUserId, Long today);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Delete
    void deleteUserById(Ingredient ingredient);
}
