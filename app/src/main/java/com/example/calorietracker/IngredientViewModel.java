package com.example.calorietracker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.calorietracker.room.IngredientRepository;
import com.example.calorietracker.room.entity.Ingredient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private final IngredientRepository mRepository;
    private final FirebaseAuth firebaseAuth;
    private final LiveData<List<Ingredient>> mAllIngredients;
    private final LiveData<Integer> mCaloriesFromAllIngredients;

    public IngredientViewModel(Application application) {
        super(application);
        mRepository = new IngredientRepository(application);
        firebaseAuth = FirebaseAuth.getInstance();
        mAllIngredients = mRepository.getAllIngredients(firebaseAuth.getCurrentUser().getUid());
        mCaloriesFromAllIngredients = mRepository.getCaloriesFromAllIngredients(firebaseAuth.getCurrentUser().getUid());
    }

    public LiveData<List<Ingredient>> getAllIngredients(String currentUserId) {
        return mAllIngredients;
    }

    public LiveData<Integer> getCaloriesFromAllIngredients(String currentUserId) {
        return mCaloriesFromAllIngredients;
    }

    public void insert(Ingredient ingredient) {
        mRepository.insert(ingredient);
    }

    public void delete(Ingredient ingredient) {
        mRepository.deleteById(ingredient);
    }
}
