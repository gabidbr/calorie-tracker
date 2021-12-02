package com.example.calorietracker.food;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/food/ingredients/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static IngredientListService getIngredientService() {
        IngredientListService ingredientService = getRetrofit().create(IngredientListService.class);
        return ingredientService;
    }
}
