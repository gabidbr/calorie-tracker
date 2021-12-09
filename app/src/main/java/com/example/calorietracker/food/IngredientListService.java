package com.example.calorietracker.food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IngredientListService {
    @GET("search")
    Call<IngredientListResponse> getIngredientsList(@Query("query") String ingredientName, @Query("number") String number, @Query("apiKey") String apiKey);
}
