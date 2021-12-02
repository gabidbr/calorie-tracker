package com.example.calorietracker.food;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientListService {
    @GET("search?query=cal&number=5&apiKey=7f44089524134491ae3b67763196e607")
    Call<IngredientListResponse> getIngredientsList();
}
