package com.example.calorietracker.room;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.calorietracker.room.dao.IngredientDao;
import com.example.calorietracker.room.entity.DateConverter;
import com.example.calorietracker.room.entity.Ingredient;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IngredientRepository {
    private IngredientDao ingredientDao;
    private LiveData<List<Ingredient>> allIngredients;
    private LiveData<Integer> caloriesFromAllIngredients;
    private FirebaseAuth firebaseAuth;

    public IngredientRepository(Application application){
        IngredientRoomDatabase db = IngredientRoomDatabase.getDatabase(application);
        ingredientDao = db.ingredientDao();
        firebaseAuth = FirebaseAuth.getInstance();
        allIngredients = ingredientDao.getAllIngredientsFromToday(firebaseAuth.getCurrentUser().getUid(), DateConverter.fromDate(getStartDateOfDay(Calendar.getInstance().getTime())));
        caloriesFromAllIngredients = ingredientDao.getCaloriesSum(firebaseAuth.getCurrentUser().getUid(), DateConverter.fromDate(getStartDateOfDay(Calendar.getInstance().getTime())));
    }

    public LiveData<List<Ingredient>> getAllIngredients(String currentUserId){
        return allIngredients;
    }

    public LiveData<Integer> getCaloriesFromAllIngredients(String currentUserId){
        return caloriesFromAllIngredients;
    }

    public void insert(Ingredient ingredient){
        IngredientRoomDatabase.databaseWriteExecutor.execute(()->{
            ingredientDao.insert(ingredient);
        });
    }

    public void deleteById(Ingredient ingredient){
        IngredientRoomDatabase.databaseWriteExecutor.execute(()->{
            ingredientDao.deleteUserById(ingredient);
        });
    }

    public static Date getStartDateOfDay(Date day) {
        if (day == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.MILLISECOND, 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }
}
