package com.example.calorietracker.food;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity {
    private final String NUMBER = "10";
    private final String API_KEY = "7f44089524134491ae3b67763196e607";
    RecyclerView recyclerView;
    EditText searchFoodEditText;
    ImageView searchFoodIcon;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        recyclerView = findViewById(R.id.recyclerView);
        searchFoodEditText = findViewById(R.id.searchFoodEditText);
        searchFoodIcon = findViewById(R.id.search_food_icon);
        searchFoodIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (searchFoodEditText.getText().toString().length() == 0) {
                    searchFoodEditText.requestFocus();
                    Toast.makeText(FoodListActivity.this, "Please insert the food name", Toast.LENGTH_SHORT).show();
                } else {
                    Call<IngredientListResponse> ingredientList = ApiClient.getIngredientService().getIngredientsList(searchFoodEditText.getText().toString(), NUMBER, API_KEY);

                    ingredientList.enqueue(new Callback<IngredientListResponse>() {
                        @Override
                        public void onResponse(Call<IngredientListResponse> call, Response<IngredientListResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(FoodListActivity.this, response.body());
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FoodListActivity.this);

                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(ingredientsAdapter);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.e("success", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<IngredientListResponse> call, Throwable t) {
                            Log.e("failure", t.getLocalizedMessage());
                        }
                    });
                }
            }
        });


    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void goToPreviousActivityOnClick(View view) {
        finish();
    }

}