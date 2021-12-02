package com.example.calorietracker.food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientService {
    @GET("search?query=che&number=5&apiKey=7f44089524134491ae3b67763196e607")
    Call<List<IngredientResponse>> getIngredients();
}
