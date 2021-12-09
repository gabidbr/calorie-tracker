package com.example.calorietracker.food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IngredientInfoService {
    @GET("{id}/information")
    Call<IngredientInfo> getIngredientInformation(@Path("id") String id, @Query("amount") String amount, @Query("apiKey") String apiKey);
}
